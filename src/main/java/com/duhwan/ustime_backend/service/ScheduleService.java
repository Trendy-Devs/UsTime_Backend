package com.duhwan.ustime_backend.service;

import com.duhwan.ustime_backend.config.security.CustomUserDetails;
import com.duhwan.ustime_backend.dao.CoupleMapper;
import com.duhwan.ustime_backend.dao.ScheduleMapper;
import com.duhwan.ustime_backend.dto.CoupleDto;
import com.duhwan.ustime_backend.dto.ScheduleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleMapper mapper;
    private final NotificationService notificationService;

    // 일정 생성
    public void createSchedule(ScheduleDto dto) {
        // coupleId가 유효한지 확인 (Couple 테이블에 존재하는지 확인)
        if (dto.getCoupleId() == null) {
            throw new IllegalArgumentException("유효한 커플이 존재하지 않습니다.");
        }

        // 일정 생성
        mapper.insertSchedule(dto);

        // 알림 생성: 일정 생성 알림
        String message = "새로운 일정이 생성되었습니다.";
        Long userId = dto.getCreatedBy();  // 일정 생성자의 ID

        // 알림 생성: "일정 생성" 유형으로 알림 전송
        notificationService.createNotification(
                userId,
                "일정 생성",  // 알림 유형
                message      // 알림 메시지
        );
    }

    // 캘린더에 표시될 모든 일정 가져오기
    public List<ScheduleDto> getAllSchedulesForCalendar() {
        return mapper.getAllSchedulesForCalendar();
    }

    // 특정 커플의 특정 날짜에 해당하는 일정 가져오기
    public List<ScheduleDto> getSchedulesByDate(Long coupleId, LocalDate date) {
        return mapper.getSchedulesByDate(coupleId, date);
    }



}
