package com.duhwan.ustime_backend.dto.Couple;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Alias("CoupleRequestDto")
public class CoupleRequestDto {

    private Long requestId;             // 요청 고유번호
    private Long fromUserId;            // 요청자 ID
    private Long toUserId;              // 대상자 ID
    private String status;              // 상태 (대기, 승인, 거절)
    private LocalDateTime requestedAt;  // 신청 일시
    private LocalDateTime respondedAt;  // 응답 일시
}
