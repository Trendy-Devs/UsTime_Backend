package com.duhwan.ustime_backend.config.security;

import com.duhwan.ustime_backend.dto.UserDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final UserDto user;

    public CustomUserDetails(UserDto user) {
        this.user = user;
    }

    public static CustomUserDetails create(UserDto user) {
        return new CustomUserDetails(user);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 권한 리스트를 반환합니다. 이후에 role이 필요할경우 수정예정
        // return List.of(() -> "ROLE_USER");
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getName();
    }

    public String getEmail() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 만료된 계정 여부
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 잠금된 계정 여부
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 자격 증명 만료 여부
    }

    @Override
    public boolean isEnabled() {
        return true; // 계정 활성 여부
    }

    public Long getUserId() {
        return user.getUserId();
    }

}
