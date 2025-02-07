package com.duhwan.ustime_backend.dto.Couple;

import lombok.*;
import org.apache.ibatis.type.Alias;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Alias("CoupleDto")
public class CoupleDto {

    private Long coupleId; // 커플 ID
    private Long user1Id; // 사용자 1 (남자친구)
    private Long user2Id; // 사용자 2 (여자친구)
    private LocalDate startDate; // 시작일 (커플 시작일)
    private LocalDate anniversary; // 기념일 (옵션)
}
