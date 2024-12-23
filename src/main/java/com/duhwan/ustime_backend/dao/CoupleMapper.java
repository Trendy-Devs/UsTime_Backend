package com.duhwan.ustime_backend.dao;

import com.duhwan.ustime_backend.dto.CoupleResponseDto;
import com.duhwan.ustime_backend.dto.CoupleDto;
import com.duhwan.ustime_backend.dto.CoupleRequestDto;
import com.duhwan.ustime_backend.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Mapper
public interface CoupleMapper {

    void createCoupleRequest(CoupleRequestDto coupleRequestDTO);

    Long getCoupleRequests(Long userId);

    void approveCoupleRequest(Long requestId);

    void declineCoupleRequest(Long requestId);

    void updateCoupleId(Map<String, Long> params);

    void createCouple(CoupleDto coupleDTO);

    CoupleRequestDto getRequestById(Long requestId);

    CoupleResponseDto getRequestInfo(Long requestId);

    List<UserDto> searchUsers(String name);

    void deleteCouple(Long coupleId);

    CoupleDto getCoupleById(Long coupleId);

    void updateAnniversary(Long coupleId, LocalDate anniversary);

}
