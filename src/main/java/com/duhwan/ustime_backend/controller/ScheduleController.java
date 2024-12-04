package com.duhwan.ustime_backend.controller;

import com.duhwan.ustime_backend.dto.ScheduleDto;
import com.duhwan.ustime_backend.service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calendar")
@Tag(name ="스케줄 API", description ="캘린더 관련 기능을 제공하는 API")
public class ScheduleController {

    private final ScheduleService service;

    @Operation(summary = "전체 일정 조회")
    @GetMapping("/all")
    public ResponseEntity<List<ScheduleDto>> getAllSchedulesForCalendar() {
        List<ScheduleDto> result = service.getAllSchedulesForCalendar();
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "특정 날짜 일정 조회")
    @GetMapping("/{date}")
    public ResponseEntity<List<ScheduleDto>> getSchedulesByDate(@RequestParam Long coupleId,@PathVariable("date") String date) {
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
        List<ScheduleDto> result = service.getSchedulesByDate(coupleId,localDate);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "일정 생성")
    @PostMapping("/create")
    public ResponseEntity<String> createSchedule(@RequestBody ScheduleDto dto) {
        service.createSchedule(dto);
        return ResponseEntity.ok("일정이 생성되었습니다.");
    }





}
