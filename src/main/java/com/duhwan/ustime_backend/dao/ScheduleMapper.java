package com.duhwan.ustime_backend.dao;

import com.duhwan.ustime_backend.dto.ScheduleDto;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface ScheduleMapper {

    void insertSchedule(ScheduleDto dto);
    List<ScheduleDto> getAllSchedulesForCalendar();
    List<ScheduleDto> getSchedulesByDate(Long coupleId, LocalDate date);
    void updateSchedule(ScheduleDto dto);
    void deleteSchedule(Long scheduleId);
    ScheduleDto getScheduleById(Long typeId);
}
