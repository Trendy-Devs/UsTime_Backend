package com.duhwan.ustime_backend.service;

import com.duhwan.ustime_backend.dao.CoupleMapper;
import com.duhwan.ustime_backend.dto.CoupleResponseDto;
import com.duhwan.ustime_backend.dto.CoupleDto;
import com.duhwan.ustime_backend.dto.CoupleRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CoupleService {

    private final CoupleMapper mapper;

    // 커플 신청
    public void createCoupleRequest(CoupleRequestDto dto) {
        List<CoupleResponseDto> existRequests = mapper.getCoupleRequests(dto.getToUserId());
        if(!existRequests.isEmpty()) {
            throw new IllegalArgumentException("이미 대기 중인 신청이 있습니다.");
        }
        mapper.createCoupleRequest(dto);
    }

    // 커플 신청 조회
    public List<CoupleResponseDto> getCoupleRequest(Long userId) {
        return mapper.getCoupleRequests(userId);
    }

    // 커플 신청 승인
    @Transactional
    public void approveCoupleRequest(Long requestId) {
        CoupleRequestDto request = mapper.getRequestById(requestId);
        if(request == null || !"대기".equals(request.getStatus())) {
            throw new IllegalArgumentException("유효하지 않은 요청입니다.");
        }

        CoupleDto coupleDto = new CoupleDto();
        coupleDto.setUser1Id(request.getFromUserId());
        coupleDto.setUser2Id(request.getToUserId());
        mapper.createCouple(coupleDto);

        mapper.approveCoupleRequest(requestId);

        mapper.updateCoupleId(Map.of(
                "userId",request.getFromUserId(),
                "coupleId", coupleDto.getCoupleId()
        ));
        mapper.updateCoupleId(Map.of(
                "userId",request.getToUserId(),
                "coupleId", coupleDto.getCoupleId()
        ));
    }

    // 커플 신청 거절
    public void declineCoupleRequest(Long requestId) {
        mapper.declineCoupleRequest(requestId);
    }

}
