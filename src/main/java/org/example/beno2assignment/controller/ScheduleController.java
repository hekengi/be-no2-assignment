package org.example.beno2assignment.controller;

import lombok.RequiredArgsConstructor;
import org.example.beno2assignment.service.ScheduleService;
import org.example.beno2assignment.dto.ScheduleRequestDto;
import org.example.beno2assignment.dto.ScheduleResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;

    // Lv1 일정 생성
    @PostMapping("")
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto requestDto) {
        ScheduleResponseDto scheduleResponseDto = scheduleService.createSchedule(requestDto);
        return ResponseEntity.ok(scheduleResponseDto);
    }

    // LV1 전체 일정 조회
    @GetMapping("")
    public ResponseEntity<List<ScheduleResponseDto>> getAllSchedules(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String updateDate
    ) {
        List<ScheduleResponseDto> schedules = scheduleService.getAllSchedules(name, updateDate);
        return ResponseEntity.ok(schedules);
    }

    // Lv1 선택한 일정 조회
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> getScheduleById(@PathVariable Long id) {
        ScheduleResponseDto scheduleResponseDto = scheduleService.getScheduleById(id);
        return ResponseEntity.ok(scheduleResponseDto);
    }

    // Lv2 선택한 일정 수정
    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @PathVariable Long id,
            @RequestBody ScheduleRequestDto requestDto) {
        ScheduleResponseDto scheduleResponseDto = scheduleService.updateSchedule(id, requestDto);
        return ResponseEntity.ok(scheduleResponseDto);
    }

    // Lv2 선택한 일정 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSchedule(
            @PathVariable Long id,
            @RequestBody ScheduleRequestDto requestDto) {
        scheduleService.deleteSchedule(id, requestDto);
        return ResponseEntity.ok("일정이 삭제되었습니다.");
    }



}
