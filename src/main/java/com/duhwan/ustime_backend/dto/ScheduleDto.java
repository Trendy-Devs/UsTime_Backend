package com.duhwan.ustime_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "시작 날짜는 필수입니다.")
    private LocalDate startDate;
    @NotNull(message = "종료 날짜는 필수입니다.")
    private LocalDate endDate;
    @NotBlank(message = "라벨은 공백이 불가합니다.")
    private String label;
    private String location;
    @NotNull(message = "일정을 생성한 사용자 ID는 필수입니다.")
    private Long createdBy;
    private String scope;
    private LocalDate createdAt;
}
