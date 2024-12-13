package com.duhwan.ustime_backend.service;

import com.duhwan.ustime_backend.config.jwt.JwtUtil;
import com.duhwan.ustime_backend.config.security.CustomUserDetails;
import com.duhwan.ustime_backend.dao.UserMapper;
import com.duhwan.ustime_backend.dto.ChangePasswordDto;
import com.duhwan.ustime_backend.dto.LoginDto;
import com.duhwan.ustime_backend.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;  //인증 매니저
    private final JwtUtil jwtUtil;

    // 회원가입
    public void signUp(UserDto dto) {
    String encodedPassword = passwordEncoder.encode(dto.getPassword());
        dto.setPassword(encodedPassword);      // 암호화 된 비밀번호로 재설정
        userMapper.signUp(dto);
    }

    // 로그인
    public Map<String, Object> login(LoginDto loginDto) {
        // 사용자 인증
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );
        // 인증 성공 시 사용자 정의 UserDetails로 캐스팅
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String token = jwtUtil.generateToken(customUserDetails);
        Long userId = customUserDetails.getUserId();
        // coupleId가 null인 경우 Map에 포함시키지 않음
        Long coupleId = userMapper.getCoupleId(userId);
        String name = customUserDetails.getUsername();
        String email = customUserDetails.getEmail();

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("userId", userId);
        response.put("name", name);
        response.put("email", email);

        if (coupleId != null) {
            response.put("coupleId", coupleId);  // coupleId가 존재하면 포함
        }
        return response;
    }

    //내정보 보기
    public UserDto getUserInfo(Long userId) {
        return userMapper.selectUser(userId);
    }

    //내정보 수정
    public void updateUserInfo(UserDto updatedUserDto) {
        // 1. DB 업데이트
        userMapper.updateUser(updatedUserDto);

        // 2. SecurityContext에서 Authentication 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            // 3. CustomUserDetails 가져오기
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

            // 4. CustomUserDetails의 name과 email 업데이트
            if (updatedUserDto.getName() != null) {
                customUserDetails.updateName(updatedUserDto.getName());
            }
            if (updatedUserDto.getEmail() != null) {
                customUserDetails.updateEmail(updatedUserDto.getEmail());
            }

            // 5. Authentication 객체 재생성 및 SecurityContext 갱신
            Authentication newAuth = new UsernamePasswordAuthenticationToken(
                    customUserDetails,
                    authentication.getCredentials(),
                    customUserDetails.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(newAuth);
        }


    }

    // 비밀번호 재설정
    public void changePassword(ChangePasswordDto changePasswordDto) {
        UserDto user = userMapper.selectUser(changePasswordDto.getUserId());
        if (user == null || !passwordEncoder.matches(changePasswordDto.getCurrentPassword(), user.getPassword())) {
            throw new RuntimeException("현재 비밀번호가 일치하지 않습니다.");
        }
        String encodedNewPassword = passwordEncoder.encode(changePasswordDto.getNewPassword());

        changePasswordDto.setNewPassword(encodedNewPassword);
        userMapper.updatePassword(changePasswordDto);
    }



}
