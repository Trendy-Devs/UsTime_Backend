package com.duhwan.ustime_backend.service;

import com.duhwan.ustime_backend.dao.CoupleMapper;
import com.duhwan.ustime_backend.dao.NotificationMapper;
import com.duhwan.ustime_backend.dao.ScheduleMapper;
import com.duhwan.ustime_backend.dto.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationMapper notificationMapper;
    private final CoupleMapper coupleMapper;
    private final ScheduleMapper scheduleMapper;
    private final SimpMessagingTemplate messagingTemplate;
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    // 알림 신청
    public void createNotification(Long userId, String type, Long typeId,String message, String summary, Long coupleId) {
        // NotificationDto 객체 생성
        NotificationDto notification = new NotificationDto();
        notification.setUserId(userId);               // 알림을 받을 사용자 ID
        notification.setType(type);                    // 알림 유형 (예: 커플 신청, 일정 생성 등)
        notification.setTypeId(typeId);                // 타입 ID (예: requestId, scheduleId 등)
        notification.setMessage(message);              // 알림 메시지
        notification.setSummary(summary);              // 알림 요약
        notification.setStatus("읽지 않음");          // 기본 상태는 '읽지 않음'
        notification.setCreatedAt(LocalDateTime.now()); // 생성 일시
        notification.setCoupleId(coupleId);         // 커플 아이디

        // 알림 생성: 알림 유형에 관계없이 동일하게 처리
        notificationMapper.createNotification(notification);

        // 실시간 알림 전송: coupleId가 있으면 포함하여 전송
            messagingTemplate.convertAndSend(
                    "/ustime/notifications/" + userId,
                    notification
            );
    }

    // 액션에 맞는 메시지 생성 메서드
    public String createMessage(String action, String userName, String title, LocalDateTime startDate) {
        switch (action) {
            case "일정 생성":
                return userName + "님이 새로운 일정을 생성하였습니다.";
            case "사진 생성":
                return userName + "님이 새로운 사진을 업로드하였습니다.";
            case "커플 매칭":
                return userName + "님이 커플을 매칭하였습니다.";
            default:
                return userName + "님이 활동을 진행하였습니다."; // 기본 메시지
        }
    }

    // 액션에 맞는 요약 생성 메서드
    public String createSummary(String action, String title, LocalDateTime startDate) {
        switch (action) {
            case "일정 생성":
                return "일정: " + title + "\n일자: " + startDate;
            case "사진 생성":
                return "새로운 사진이 업로드되었습니다: " + title;
            case "커플 매칭":
                return "커플 매칭이 완료되었습니다.";
            default:
                return "새로운 활동이 있었습니다.";
        }
    }

    // 알림 조회
    public List<NotificationDto> getNotifications(Long userId) {
        List<NotificationDto> notifications = notificationMapper.getNotifications(userId);
        return notifications.stream()
                .map(notification -> NotificationDto.builder()
                        .notificationId(notification.getNotificationId())
                        .userId(notification.getUserId())
                        .type(notification.getType())
                        .typeId(notification.getTypeId())
                        .message(notification.getMessage())
                        .summary(notification.getSummary())
                        .status(notification.getStatus())
                        .createdAt(notification.getCreatedAt())
                        .coupleId(notification.getCoupleId())
                        .build())
                .collect(Collectors.toList());
    }

    // 특정 알림 조회
    @SuppressWarnings("unchecked")
    public <T> T getDetail(String type, Long typeId) {
        switch (type) {
            case "커플":
                return (T) coupleMapper.getRequestInfo(typeId);
            case "일정":
                ScheduleDto result = scheduleMapper.getScheduleById(typeId);
                return (T) result;
            default:
                throw new IllegalArgumentException("유효하지 않은 type: " + type);
        }
    }

    // 알림 읽음 처리
    public void markAsRead(Long notificationId) {
        notificationMapper.updateNotificationStatus(notificationId, "읽음");
    }

    // 알림 삭제
    public void deleteNotification(Long notificationId) {
        notificationMapper.deleteNotification(notificationId);
    }

    // 일정 삭제 시 관련 알림 삭제
    public void deleteScheduleNoti(Long scheduleId) {
        notificationMapper.deleteScheduleNoti(scheduleId);
    }

    // 읽은지 3일 혹은 30일이 경과된 알림 자동 삭제
    @Scheduled(cron = "0 0 0 * * ?") //매일 자정에 실행
    public int deleteOldNoti() {
        LocalDateTime threeDaysAgo = LocalDateTime.now().minusDays(3);
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
        int deletedCount = notificationMapper.deleteOldNoti(threeDaysAgo, thirtyDaysAgo);

        // 삭제된 알림 수를 로그로 출력
        logger.info("자동 삭제된 알림 수: {}", deletedCount);
        return deletedCount;
    }


}
