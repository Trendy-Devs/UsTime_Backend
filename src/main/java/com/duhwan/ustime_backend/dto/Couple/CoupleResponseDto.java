package com.duhwan.ustime_backend.dto.Couple;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Alias("CoupleResponseDto")
public class CoupleResponseDto {

    private Long requestId;             // 요청 고유번호
    private String fromUserName;        // 요청자 이름
    private String toUserName;          // 대상자 이름
    private String status;              // 상태
    private LocalDateTime requestedAt;  // 보낸일시
}
