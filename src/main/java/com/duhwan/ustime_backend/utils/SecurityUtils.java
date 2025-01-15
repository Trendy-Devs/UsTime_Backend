package com.duhwan.ustime_backend.utils;

import com.duhwan.ustime_backend.config.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

// 인증 객체를 이용하여 사용할 정보 가져오는 유틸 클래스
public class SecurityUtils {

    // 사용자 이름
    public static String getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            return userDetails.getUsername(); // 사용자 이름 반환
        }
        throw new IllegalStateException("사용자 정보가 존재하지 않습니다.");
    }

    // 사용자 ID
    public static Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            return userDetails.getUserId(); // 사용자 ID 반환
        }
        throw new IllegalStateException("사용자 정보가 존재하지 않습니다.");
    }
}
