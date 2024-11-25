package com.duhwan.ustime_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoupleConfirmDto {

    private String fromUserName;
    private String toUserName;
    private String status;
    private LocalDateTime requestedAt;

}
