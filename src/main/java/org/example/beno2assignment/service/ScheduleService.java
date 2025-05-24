package org.example.beno2assignment.service;

import lombok.RequiredArgsConstructor;
import org.example.beno2assignment.dto.ScheduleResponseDto;
import org.example.beno2assignment.entity.Schedule;
import org.example.beno2assignment.repository.ScheduleRepository;
import org.example.beno2assignment.dto.ScheduleRequestDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    //Lv1 일정 생성 API
    public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto) {
        Schedule schedule = new Schedule(requestDto);

        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        schedule.setCreateDate(now);
        schedule.setUpdateDate(now);

        scheduleRepository.save(schedule);

        return new ScheduleResponseDto(
                schedule.getId(), schedule.getTitle(), schedule.getName(),
                schedule.getCreateDate(), schedule.getUpdateDate()
        );
    }

    //Lv1 전체 일정 조회
    public List<ScheduleResponseDto> getAllSchedules(String name, String updateDate) {
        List<Schedule> schedules = scheduleRepository.findAll(name, updateDate);

        return schedules.stream()
                .map(schedule -> new ScheduleResponseDto(
                        schedule.getId(),
                        schedule.getTitle(),
                        schedule.getName(),
                        schedule.getCreateDate(),
                        schedule.getUpdateDate())
                ).collect(Collectors.toList());
    }

    // Lv1 선택한 일정 조회
    public ScheduleResponseDto getScheduleById(Long id) {
        Schedule schedule = scheduleRepository.findById(id);

        return new ScheduleResponseDto(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getName(),
                schedule.getCreateDate(),
                schedule.getUpdateDate()
        );
    }


    //Lv2 선택한 일정 수정
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto requestDto) {
        // 먼저 ID로 스케줄을 조회
        Schedule schedule = scheduleRepository.findById(id);

        // 비밀번호 비교
        if (!schedule.getPassword().equals(requestDto.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // title, name 업데이트
        schedule.setTitle(requestDto.getTitle());
        schedule.setName(requestDto.getName());

        // 수정일자 업데이트
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        schedule.setUpdateDate(now);

        // DB에 저장
        scheduleRepository.update(schedule);

        // 응답 DTO 생성
        return new ScheduleResponseDto(
                schedule.getId(), schedule.getTitle(), schedule.getName(),
                schedule.getCreateDate(), schedule.getUpdateDate()
        );
    }

    // Lv2 선택한 일정 삭제
    public void deleteSchedule(Long id, ScheduleRequestDto requestDto) {
        //ID로  스케줄 조회
        Schedule schedule = scheduleRepository.findById(id);

        // 비밀번호 비교
        if (!schedule.getPassword().equals(requestDto.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 스케줄 삭제
        scheduleRepository.delete(id);
    }
}
