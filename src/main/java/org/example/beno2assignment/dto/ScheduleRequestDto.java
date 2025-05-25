package org.example.beno2assignment.dto;

import jakarta.persistence.Column;
import lombok.Getter;

@Getter
public class ScheduleRequestDto {
    private String title;
    private String password;
    private String name;
    private Long authorId;
}
