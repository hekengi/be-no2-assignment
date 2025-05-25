package org.example.beno2assignment.service;

import lombok.RequiredArgsConstructor;
import org.example.beno2assignment.dto.ScheduleResponseDto;
import org.example.beno2assignment.entity.Author;
import org.example.beno2assignment.entity.Schedule;
import org.example.beno2assignment.repository.jdbc.AuthorRepository;
import org.example.beno2assignment.repository.jpa.ScheduleJpaRepository;
import org.example.beno2assignment.repository.jdbc.ScheduleRepository;
import org.example.beno2assignment.dto.ScheduleRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private  final ScheduleJpaRepository scheduleJpaRepository; // Lv4 페이징 기능
    private final AuthorRepository authorRepository;

    //Lv1 일정 생성 API
    public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto) {
        // 1. 이름으로 Author 검색
        Author author = authorRepository.findByName(requestDto.getName());

        // 2. 없으면 새로 생성
        if (author == null) {
            author = new Author();
            author.setName(requestDto.getName());
            author.setEmail("default@email.com"); // 또는 requestDto에 email 필드 추가
            String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            author.setCreateDate(now);
            author.setUpdateDate(now);
            authorRepository.save(author);  // DB 저장 및 id 생성
        }

        // 3. Schedule 생성
        Schedule schedule = new Schedule();
        schedule.setTitle(requestDto.getTitle());
        schedule.setPassword(requestDto.getPassword());
        schedule.setName(requestDto.getName()); // name 컬럼에도 넣기 위해
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        schedule.setCreateDate(now);
        schedule.setUpdateDate(now);
        schedule.setAuthor(author);  // JPA 연관 설정

        scheduleRepository.save(schedule);  // JDBC 저장

        return new ScheduleResponseDto(
                schedule.getId(), schedule.getName(),schedule.getTitle(),
                schedule.getCreateDate(), schedule.getUpdateDate(),
                schedule.getAuthor().getId()
        );
    }

    //Lv1 전체 일정 조회
    public List<ScheduleResponseDto> getAllSchedules(Long authorId, String name, String updateDate) {
        List<Schedule> schedules = scheduleRepository.findAll(authorId, name , updateDate);

        return schedules.stream()
                .map(schedule -> new ScheduleResponseDto(
                        schedule.getId(),
                        schedule.getName(),
                        schedule.getTitle(),
                        schedule.getCreateDate(),
                        schedule.getUpdateDate(),
                        schedule.getAuthor().getId()) // JPA 연관 객체로 Author ID 포함
                ).collect(Collectors.toList());
    }

    // Lv1 선택한 일정 조회
    public ScheduleResponseDto getScheduleById(Long id) {
        Schedule schedule = scheduleRepository.findById(id);

        return new ScheduleResponseDto(
                schedule.getId(),
                schedule.getName(),
                schedule.getTitle(),
                schedule.getCreateDate(),
                schedule.getUpdateDate(),
                schedule.getAuthor().getId()
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

        Author author = authorRepository.findByName(requestDto.getName());

        schedule.setTitle(requestDto.getTitle());
        schedule.setName(requestDto.getName());
        schedule.setUpdateDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        schedule.setAuthor(author);  // author_id에 들어갈 값 설정

        scheduleRepository.update(schedule);

        return new ScheduleResponseDto(schedule);
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

    // Lv4 페이징 기능
    public Page<ScheduleResponseDto> getAllSchedulesPaginated(Pageable pageable) {
        return scheduleJpaRepository.findAllWithAuthor(pageable)
                .map(ScheduleResponseDto::new);
    }
}
