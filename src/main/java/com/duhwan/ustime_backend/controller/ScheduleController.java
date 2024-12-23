package com.duhwan.ustime_backend.controller;

import com.duhwan.ustime_backend.dto.ScheduleDto;
import com.duhwan.ustime_backend.service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calendar")
@Tag(name ="스케줄 API", description ="캘린더 관련 기능을 제공하는 API")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Operation(summary = "스코프 별 전체 일정 조회")
    @GetMapping("/all")
    public ResponseEntity<List<ScheduleDto>> getAllSchedulesForCalendar(@RequestParam Long userId,
                                                                        @RequestParam(required = false) Long coupleId,
                                                                        @RequestParam(required = false) String scope) {
        List<ScheduleDto> result = scheduleService.getAllSchedulesForCalendar(userId,coupleId,scope);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "일정 생성")
    @PostMapping("/create")
    public ResponseEntity<String> createSchedule(@Valid @RequestBody ScheduleDto dto) {
        scheduleService.createSchedule(dto);
        return ResponseEntity.ok("일정이 생성되었습니다.");
    }

    @PutMapping("/update")
    @Operation(summary = "일정 수정")
    public ResponseEntity<String> updateSchedule(@RequestBody ScheduleDto dto) {
        scheduleService.updateSchedule(dto);
        return ResponseEntity.ok("일정이 수정되었습니다.");
    }

    @DeleteMapping("/delete")
    @Operation(summary = "일정 삭제")
    public ResponseEntity<String> deleteSchedule(@RequestParam Long scheduleId) {
        scheduleService.deleteSchedule(scheduleId);
        return ResponseEntity.ok("일정이 삭제되었습니다.");
    }





}
