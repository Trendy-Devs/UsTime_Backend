package com.duhwan.ustime_backend.dao;

import com.duhwan.ustime_backend.dto.CoupleDto;
import com.duhwan.ustime_backend.dto.CoupleRequestDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CoupleMapper {

    void createCoupleRequest(CoupleRequestDto coupleRequestDTO);

    List<CoupleRequestDto> getCoupleRequests(Long userId, String status);

    void approveCoupleRequest(Long requestId);

    void declineCoupleRequest(Long requestId);

    void updateCoupleId(Long userId, Long coupleId);

    void createCouple(CoupleDto coupleDTO);
}
