package com.duhwan.ustime_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationDto {

    private Long notificationId;  // 알림 고유 ID
    private Long userId;          // 알림을 받을 사용자 ID
    private String type;          // 알림 유형
    private String message;       // 알림 내용
    private String status;        // 알림 상태
    private LocalDateTime createdAt; // 생성 일시
}
