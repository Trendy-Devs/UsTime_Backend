package com.duhwan.ustime_backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDto {

    private Long scheduleId;
    private Long coupleId;
    @NotBlank(message = "타이틀은 공백이 불가합니다.")
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String label;
    private String location;
    private Long createdBy;
    private String scope;
    private LocalDate createdAt;
}
