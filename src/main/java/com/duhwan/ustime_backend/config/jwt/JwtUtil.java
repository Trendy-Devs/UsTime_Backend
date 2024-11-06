package com.duhwan.ustime_backend.config.jwt;

import com.duhwan.ustime_backend.config.security.CustomUserDetails;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JwtUtil {
    
    private final String SECRET_KEY = "mySuperSecretKeyWhichIsAtLeast512BitsLongMySuperSecretKeyWhichIsAtLeast512BitsLongdfsdfsdfsdfsdf";                //보안을 위해 추후 환경변수로 변경예정
    private final long EXPIRATION_TIME = 1000L * 60 * 60;   //1시간

    // JWT 토큰 생성
    public String generateToken(CustomUserDetails userDetails) { // UserDto 대신 CustomUserDetails 사용
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userDetails.getUserId()); // CustomUserDetails에서 userId 추출
        claims.put("email", userDetails.getEmail()); // CustomUserDetails의 getUsername() 메서드를 사용
        claims.put("name", userDetails.getUsername()); // CustomUserDetails에서 name 추출
        claims.put("role", "ROLE_USER"); // 또는 사용자 역할에 맞게 설정

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername()) // 사용자의 이메일로 설정
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.warn("잘못된 JWT 토큰입니다.", e);
        } catch (ExpiredJwtException e) {
            log.warn("만료된 JWT 토큰입니다.", e);
        } catch (UnsupportedJwtException e) {
            log.warn("지원되지 않는 JWT 토큰입니다.", e);
        } catch (IllegalArgumentException e) {
            log.warn("JWT 클레임이 비어 있습니다.", e);
        }
        return false;
    }

    //헤더에서 토큰 추출
    public String extractTokenFromHeader(String authorization) {
        if (!StringUtils.hasText(authorization) || !authorization.startsWith("Bearer")) {
            return null;
        }

        return authorization.substring(7).trim();
    }

    // Jwt에서 Claims를 추출
    public Claims parseclaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    //토큰에서 userId 추출
    public String getUserId(String token) {
        return parseclaims(token).get("userId", String.class);
    }
    //이후 필요한게 있을시 추가 작성




}
