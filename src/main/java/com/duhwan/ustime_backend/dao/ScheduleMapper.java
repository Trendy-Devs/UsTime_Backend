package com.duhwan.ustime_backend.dao;

import com.duhwan.ustime_backend.dto.ScheduleDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ScheduleMapper {

    void insertSchedule(ScheduleDto dto);
    List<ScheduleDto> getPersonalSchedules(Long userId);
    List<ScheduleDto> getSchedulesByScope(Long userId, Long coupleId, String scope);
    void updateSchedule(ScheduleDto dto);
    void deleteSchedule(Long scheduleId);
    ScheduleDto getScheduleById(Long typeId);
}
