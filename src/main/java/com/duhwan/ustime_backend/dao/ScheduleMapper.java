package com.duhwan.ustime_backend.dao;

import com.duhwan.ustime_backend.dto.ScheduleDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ScheduleMapper {

    void insertSchedule(ScheduleDto dto);
    List<ScheduleDto> getAllSchedulesForCalendar();
    List<ScheduleDto> getSchedulesByDate(Long coupleId,String date);

}
