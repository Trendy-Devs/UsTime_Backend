package com.duhwan.ustime_backend.dao;

import com.duhwan.ustime_backend.dto.CoupleResponseDto;
import com.duhwan.ustime_backend.dto.CoupleDto;
import com.duhwan.ustime_backend.dto.CoupleRequestDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CoupleMapper {

    void createCoupleRequest(CoupleRequestDto coupleRequestDTO);

    List<CoupleResponseDto> getCoupleRequests(Long userId);

    void approveCoupleRequest(Long requestId);

    void declineCoupleRequest(Long requestId);

    void updateCoupleId(Map<String,Long> params);

    void createCouple(CoupleDto coupleDTO);

    CoupleRequestDto getRequestById(Long requestId);
}
