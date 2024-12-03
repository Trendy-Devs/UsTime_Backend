package com.duhwan.ustime_backend.service;

import com.duhwan.ustime_backend.dao.NotificationMapper;
import com.duhwan.ustime_backend.dto.NotificationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationMapper notificationMapper;

    // 알림 신청
    public void createNotification(Long userId, String type, String message) {
        // NotificationDto 객체 생성
        NotificationDto notification = new NotificationDto();
        notification.setUserId(userId);               // 알림을 받을 사용자 ID
        notification.setType(type);                    // 알림 유형 (예: 커플 신청, 일정 생성 등)
        notification.setMessage(message);              // 알림 메시지
        notification.setStatus("읽지 않음");          // 기본 상태는 '읽지 않음'
        notification.setCreatedAt(LocalDateTime.now()); // 생성 일시

        // 알림 생성: 알림 유형에 관계없이 동일하게 처리
        notificationMapper.createNotification(notification);
    }

    // 알림 조회
    public List<NotificationDto> getNotifications(Long userId) {
        List<NotificationDto> notifications = notificationMapper.getNotifications(userId);
        return notifications.stream()
                .map(notification -> NotificationDto.builder()
                        .notificationId(notification.getNotificationId())
                        .userId(notification.getUserId())
                        .type(notification.getType())
                        .message(notification.getMessage())
                        .status(notification.getStatus())
                        .createdAt(notification.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    // 알림 읽음 처리
    public void markAsRead(Long notificationId) {
        notificationMapper.updateNotificationStatus(notificationId, "읽음");
    }

    // 알림 삭제
    public void deleteNotification(Long notificationId) {
        notificationMapper.deleteNotification(notificationId);
    }
}
