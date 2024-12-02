package com.duhwan.ustime_backend.service;

import com.duhwan.ustime_backend.config.security.CustomUserDetails;
import com.duhwan.ustime_backend.dao.ScheduleMapper;
import com.duhwan.ustime_backend.dto.ScheduleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleMapper mapper;

    public void createSchedule(ScheduleDto dto) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long coupleId = userDetails.getCoupleId();
        dto.setCoupleId(coupleId);
        mapper.insertSchedule(dto);
    }

    public List<ScheduleDto> getAllSchedulesForCalendar() {
        return mapper.getAllSchedulesForCalendar();
    }

    public List<ScheduleDto> getSchedulesByDate(String date) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long coupleId = userDetails.getCoupleId();
        return mapper.getSchedulesByDate(coupleId, date);
    }



}
