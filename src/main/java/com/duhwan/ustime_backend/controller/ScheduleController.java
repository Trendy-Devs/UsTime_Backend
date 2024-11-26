package com.duhwan.ustime_backend.controller;

import com.duhwan.ustime_backend.dto.ScheduleDto;
import com.duhwan.ustime_backend.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calendar")
public class ScheduleController {

    private final ScheduleService service;

//    // 일정 조회 (전체)
//    @GetMapping("selectall")
//    public ResponseEntity<List<ScheduleDto>> getAllSchedules() {
//        return ResponseEntity.ok(service.getAllSchedules());
//    }
//
//    // 특정 일정 조회
//    @GetMapping("/select/{id}")
//    public ResponseEntity<ScheduleDto> getScheduleById(@PathVariable Long id) {
//        return ResponseEntity.ok(service.getScheduleById(id));
//    }

    // 일정 생성
    @PostMapping("/create")
    public ResponseEntity<Void> createSchedule(@RequestBody ScheduleDto dto) {
        service.createSchedule(dto);
        return ResponseEntity.ok().build();
    }

//    // 일정 수정
//    @PutMapping("/update/{id}")
//    public ResponseEntity<ScheduleDto> updateSchedule(@PathVariable Long id, @RequestBody ScheduleDto scheduleDto) {
//        ScheduleDto updatedSchedule = service.updateSchedule(id, scheduleDto);
//        return ResponseEntity.ok(updatedSchedule);
//    }
//
//    // 일정 삭제
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
//        service.deleteSchedule(id);
//        return ResponseEntity.noContent().build();
//    }




}
