package com.duhwan.ustime_backend.config.security;

import com.duhwan.ustime_backend.dao.UserMapper;
import com.duhwan.ustime_backend.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserMapper userMapper;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // email로 사용자 조회
        UserDto userDto = userMapper.findByEmail(email);
        if (userDto == null) {
            throw new UsernameNotFoundException("이메일에 해당하는 유저가 없습니다.: " + email);
        }
        return CustomUserDetails.create(userDto);
    }
}
