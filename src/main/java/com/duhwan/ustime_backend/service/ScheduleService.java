package com.duhwan.ustime_backend.service;

import com.duhwan.ustime_backend.dao.CoupleMapper;
import com.duhwan.ustime_backend.dao.ScheduleMapper;
import com.duhwan.ustime_backend.dto.ScheduleDto;
import com.duhwan.ustime_backend.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleMapper scheduleMapper;
    private final CoupleMapper coupleMapper;
    private final NotificationService notificationService;
    private final UserService userService;

    // 일정 생성
    @Transactional
    public void createSchedule(ScheduleDto dto) {
        // 일정 생성
        scheduleMapper.insertSchedule(dto);

        String createUserName = userService.getUserInfo(dto.getCreatedBy()).getName();
        String message = createUserName + "님이 새로운 일정이 생성하였습니다.";
        Long scheduleId = dto.getScheduleId();
        Long userId = dto.getCreatedBy();  // 일정 생성자의 ID
        Long partnerId = coupleMapper.getPartnerId(userId);
        Long coupleId = dto.getCoupleId();
        String summary = dto.getTitle();

        // 알림 생성: 일정 생성 알림
        notificationService.createNotification(userId,"일정 생성", scheduleId, message, summary, coupleId);
        notificationService.createNotification(partnerId,"일정 생성", scheduleId, message, summary, coupleId);
    }

    @Transactional
    public void updateSchedule(ScheduleDto dto) {
        // 인증된 사용자 이름 가져오기
        String currentUserName = SecurityUtils.getCurrentUserName();
        
        // 이전 스케줄
        ScheduleDto prev = scheduleMapper.getScheduleById(dto.getScheduleId());

        scheduleMapper.updateSchedule(dto);

        // 변경된 필드 확인
        StringBuilder changeSummary = new StringBuilder();
        boolean firstChange = true;

        if (!Objects.equals(prev.getTitle(), dto.getTitle())) {
            if (!firstChange) changeSummary.append(",");
            changeSummary.append("제목");
            firstChange = false;
        }
        if (!Objects.equals(prev.getDescription(), dto.getDescription())) {
            if (!firstChange) changeSummary.append(",");
            changeSummary.append("설명");
            firstChange = false;
        }
        if (!Objects.equals(prev.getStartDate(), dto.getStartDate()) ||
                !Objects.equals(prev.getEndDate(), dto.getEndDate())) {
            if (!firstChange) changeSummary.append(",");
            changeSummary.append("날짜");
            firstChange = false;
        }
        if (!Objects.equals(prev.getLabel(), dto.getLabel())) {
            if (!firstChange) changeSummary.append(",");
            changeSummary.append("라벨색");
            firstChange = false;
        }
        if (!Objects.equals(prev.getLocation(), dto.getLocation())) {
            if (!firstChange) changeSummary.append(",");
            changeSummary.append("장소");
        }

        String summary = changeSummary.length() > 0
                ? "수정된 항목: " + changeSummary.toString()
                : "변경 사항 없음";

        String message = currentUserName + "님이 일정이 수정했습니다.";
        Long scheduleId = dto.getScheduleId();
        Long userId = dto.getCreatedBy();
        Long partnerId = coupleMapper.getPartnerId(userId);
        Long coupleId = dto.getCoupleId();

        notificationService.createNotification(userId,"일정 수정", scheduleId, message, summary, coupleId);
        notificationService.createNotification(partnerId,"일정 수정", scheduleId, message, summary, coupleId);
    }

    // 캘린더에 표시될 스코프 별 모든 일정 가져오기
    public List<ScheduleDto>  getAllSchedulesForCalendar(Long userId, Long coupleId, String scope) {
        if(coupleId == null) {
            return scheduleMapper.getPersonalSchedules(userId);
        } else {
            return scheduleMapper.getSchedulesByScope(userId,coupleId,scope);
        }
    }

    // 이번주 일정 가져오기
    public List<ScheduleDto> getWeekSchedule(Long userId, Long coupleId, LocalDate date) {
        // 주간 범위 계산
        LocalDate startOfWeek = date.with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = date.with(DayOfWeek.SUNDAY);

        return scheduleMapper.getWeekSchedule(userId,coupleId,startOfWeek,endOfWeek);
    }

    // 일정 삭제
    @Transactional
    public void deleteSchedule(Long scheduleId) {
        scheduleMapper.deleteSchedule(scheduleId);
        notificationService.deleteScheduleNoti(scheduleId);
    }



}
