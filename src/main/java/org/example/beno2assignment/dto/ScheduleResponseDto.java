package org.example.beno2assignment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.beno2assignment.entity.Schedule;

@Getter
@NoArgsConstructor
public class ScheduleResponseDto {
    private Long id;
    private String title;
    private String createDate;
    private String updateDate;
    private Long authorId;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.createDate = schedule.getCreateDate();
        this.updateDate = schedule.getUpdateDate();
        this.authorId = schedule.getAuthorId();
    }

    public ScheduleResponseDto(Long id, String title, String createDate, String updateDate, Long authorId) {
        this.id = id;
        this.title = title;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.authorId = authorId;
    }
}
