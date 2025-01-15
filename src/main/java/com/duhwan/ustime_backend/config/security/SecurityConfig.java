package com.duhwan.ustime_backend.config.security;

import com.duhwan.ustime_backend.config.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration      // 빈에 등록하는 어노테이션
@EnableWebSecurity  // 시큐리티 활성시 해당파일에서 설정을 하겠다.
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomUserDetailsService userDetailsService; // CustomUserDetailsService를 주입받습니다.

    // CORS 세부 설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // CORS 설정
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/couple/request/**", "/couple/approve/**", "/couple/decline/**","/couple/search/**","/couple/update/**","/couple/getInfo/**").authenticated()  // 커플 인증 필요
                        .requestMatchers("/user/userinfo/**", "/user/update/**", "/user/changePassword/**").authenticated()  // 유저 인증 필요
                        .requestMatchers("/calendar/all/**", "/calendar/week/**","/calendar/create/**", "/calendar/update/**", "/calendar/delete/**").authenticated()  // 일정 인증 필요
                        .requestMatchers("/notifications/getNotify/**", "/notifications/getDetail/**","/notifications/markAsRead/**", "/notifications/delete/**").authenticated()  // 알림 인증 필요
                        .requestMatchers("/check/update/**", "/check/add/**","/check/**", "/check/delete/**").authenticated()  // 알림 인증 필요
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()  // OPTIONS 요청 허용
                        .anyRequest().permitAll()  // 그 외 모든 요청 인증
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);  // JWT 필터 설정

        return http.build();
    }

    // AuthenticationManager 빈 설정
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(userDetailsService);
        return authenticationManagerBuilder.build();
    }

    // CORS 설정을 위한 CorsConfigurationSource bean
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000", "https://www.ustime.store"));
        configuration.setAllowedMethods(List.of("*"));  // 허용할 HTTP 메서드
        configuration.setAllowedHeaders(List.of("*"));  // 허용할 헤더
        configuration.setAllowCredentials(true);  // 자격 증명 허용

        // 경로에 대해 CORS 설정 적용
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        source.registerCorsConfiguration("/ws/**", configuration);
        return source;
    }
}
