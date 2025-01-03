package com.duhwan.ustime_backend.service;

import com.duhwan.ustime_backend.dao.CoupleMapper;
import com.duhwan.ustime_backend.dao.ScheduleMapper;
import com.duhwan.ustime_backend.dto.ScheduleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleMapper scheduleMapper;
    private final CoupleMapper coupleMapper;
    private final NotificationService notificationService;

    // 일정 생성
    @Transactional
    public void createSchedule(ScheduleDto dto) {
        // 일정 생성
        scheduleMapper.insertSchedule(dto);
        // 알림 생성: 일정 생성 알림
        String message = "새로운 일정이 생성되었습니다.";
        Long scheduleId = dto.getScheduleId();
        Long userId = dto.getCreatedBy();  // 일정 생성자의 ID
        Long partnerId = coupleMapper.getPartnerId(userId);
        Long coupleId = dto.getCoupleId();


        notificationService.createNotification(userId,"일정 생성", scheduleId, message, coupleId);
        notificationService.createNotification(partnerId,"일정 생성", scheduleId, message, coupleId);
    }

    @Transactional
    public void updateSchedule(ScheduleDto dto) {
        scheduleMapper.updateSchedule(dto);
        String message = "일정이 수정되었습니다.";
        Long scheduleId = dto.getScheduleId();
        Long userId = dto.getCreatedBy();
        Long partnerId = coupleMapper.getPartnerId(userId);
        Long coupleId = dto.getCoupleId();

        notificationService.createNotification(userId,"일정 수정", scheduleId, message, coupleId);
        notificationService.createNotification(partnerId,"일정 수정", scheduleId, message, coupleId);
    }

    // 캘린더에 표시될 스코프 별 모든 일정 가져오기
    public List<ScheduleDto>  getAllSchedulesForCalendar(Long userId, Long coupleId, String scope) {
        if(coupleId == null) {
            return scheduleMapper.getPersonalSchedules(userId);
        } else {
            return scheduleMapper.getSchedulesByScope(userId,coupleId,scope);
        }

    }

    // 일정 삭제
    @Transactional
    public void deleteSchedule(Long scheduleId) {
        scheduleMapper.deleteSchedule(scheduleId);
        notificationService.deleteScheduleNoti(scheduleId);
    }



}
