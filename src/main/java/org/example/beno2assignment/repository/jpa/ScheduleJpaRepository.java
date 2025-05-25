package org.example.beno2assignment.repository.jpa;

import org.example.beno2assignment.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

// Lv4 페이징 기능을 위한 JPA 레포지토리
public interface ScheduleJpaRepository extends JpaRepository<Schedule, Long> {

    @Query("SELECT s FROM Schedule s JOIN FETCH s.author")
    Page<Schedule> findAllWithAuthor(Pageable pageable);
}
