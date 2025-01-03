package com.duhwan.ustime_backend.dao;

import com.duhwan.ustime_backend.dto.NotificationDto;
import org.apache.ibatis.annotations.Mapper;

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

    Long getUserId(Long notificationId);
}
