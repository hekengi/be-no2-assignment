package org.example.beno2assignment.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.beno2assignment.dto.ScheduleRequestDto;

@Getter
@Setter
@NoArgsConstructor
public class Schedule {
    private Long id;
    private String title;
    private String password;
    private String createDate;
    private String updateDate;
    private Long authorId;

    public Schedule(ScheduleRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.password = requestDto.getPassword();
        this.authorId = requestDto.getAuthorId();
    }
}
