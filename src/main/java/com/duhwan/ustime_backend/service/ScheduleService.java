package com.duhwan.ustime_backend.service;

import com.duhwan.ustime_backend.dao.ScheduleMapper;
import com.duhwan.ustime_backend.dto.ScheduleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleMapper mapper;

    public void createSchedule(ScheduleDto dto) {
        mapper.insertSchedule(dto);
    }

}
