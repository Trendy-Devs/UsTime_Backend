package com.duhwan.ustime_backend.service;

import com.duhwan.ustime_backend.config.jwt.JwtUtil;
import com.duhwan.ustime_backend.config.security.CustomUserDetails;
import com.duhwan.ustime_backend.dao.UserMapper;
import com.duhwan.ustime_backend.dto.LoginDto;
import com.duhwan.ustime_backend.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper dao;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;  //인증 매니저
    private final JwtUtil jwtUtil;

    // 회원가입
    public void signUp(UserDto dto) {
    String encodedPassword = passwordEncoder.encode(dto.getPassword());
        dto.setPassword(encodedPassword);      // 암호화 된 비밀번호로 재설정
        dao.signUp(dto);
    }

    //내정보 보기
    public UserDto getUserInfo(Long userId) {
        return dao.selectUser(userId);
    }

    // 로그인
    public String login(LoginDto loginDto) {
        // 사용자 인증
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );

        // 인증 성공 시 사용자 정의 UserDetails로 캐스팅
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        // JWT 생성
        return jwtUtil.generateToken(customUserDetails);
    }


}
