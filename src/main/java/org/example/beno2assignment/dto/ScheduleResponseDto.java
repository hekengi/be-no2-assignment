package org.example.beno2assignment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.beno2assignment.entity.Schedule;

@Getter
@NoArgsConstructor
public class ScheduleResponseDto {
    private Long id;
    private String title;
    private String name;
    private String createDate;
    private String updateDate;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.name = schedule.getName();
        this.createDate = schedule.getCreateDate();
        this.updateDate = schedule.getUpdateDate();
    }

    public ScheduleResponseDto(Long id, String title, String name, String createDate, String updateDate) {
        this.id = id;
        this.title = title;
        this.name = name;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }
}
