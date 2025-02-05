package com.duhwan.ustime_backend.dao;

import com.duhwan.ustime_backend.dto.NotificationDto;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface NotificationMapper {

    // 알림 생성
    void createNotification(NotificationDto notification);

    // 알림 조회
    List<NotificationDto> getNotifications(Long userId);

    // 알림 상태 변경
    void updateNotificationStatus(Long notificationId, String status);

    // 알림 삭제
    void deleteNotification(Long notificationId);

    // 일정 삭제 후 알림 삭제
    void deleteScheduleNoti(Long scheduleId);

    // 커플 매칭 후 커플 신청알림 삭제
    void deleteCoupleRequestNoti(Long reqeustId);

    // 읽은지 3일 혹은 30일 경과된 알림 자동삭제
    int deleteOldNoti(LocalDateTime readAtLimit, LocalDateTime createAtLimit);
}
