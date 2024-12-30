package com.duhwan.ustime_backend.config;

import com.duhwan.ustime_backend.config.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import javax.naming.AuthenticationException;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final JwtUtil jwtUtil;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 메시지 브로커 설정
        config.enableSimpleBroker("/ustime/notifications"); // 클라이언트가 구독할 prefix
        config.setApplicationDestinationPrefixes("/app"); // 서버로 보낼 prefix
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // WebSocket 연결 엔드포인트 설정
        registry.addEndpoint("/ws")
                .setAllowedOrigins("http://localhost:3000", "https://www.ustime.store") // 허용 도메인 설정
                .withSockJS(); // SockJS 지원
    }

//    @Override
//    public void configureClientInboundChannel(ChannelRegistration registration) {
//        // 인터셉터 등록
//        registration.interceptors(new ChannelInterceptor() {
//            @Override
//            public Message<?> preSend(Message<?> message, MessageChannel channel) {
//                // 메시지에서 Authorization 헤더를 추출하여 JWT 토큰을 검증
//                String token = (String) message.getHeaders().get("Authorization");
//
//                if (token != null && token.startsWith("Bearer ")) {
//                    token = token.substring(7).trim(); // "Bearer " 부분 제거
//
//                    // JWT 토큰 검증
//                    if (!jwtUtil.validateToken(token)) {
//                        try {
//                            throw new AuthenticationException("인증되지 않은 토큰입니다.(웹소켓)") {};  // 예외 처리
//                        } catch (AuthenticationException e) {
//                            throw new RuntimeException(e);
//                        }
//                    }
//                } else {
//                    try {
//                        throw new AuthenticationException("헤더가 비었습니다 (웹소켓)") {}; // 예외 처리
//                    } catch (AuthenticationException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//
//                return message; // 메시지 반환
//            }
//        });
//    }

}
