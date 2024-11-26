package com.duhwan.ustime_backend.dao;

import com.duhwan.ustime_backend.dto.ScheduleDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ScheduleMapper {

    void insertSchedule(ScheduleDto dto);



}
