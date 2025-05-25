package org.example.beno2assignment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.beno2assignment.dto.ScheduleRequestDto;
import org.springframework.data.relational.core.mapping.Table;

@Entity
@Table(name = "schedule")
@Getter @Setter @NoArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID 필드가 자동으로 증가하는 기본 키
    private Long id;
    private String name;
    private String title;
    private String password;
    @Column(name = "createdate")
    private String createDate;
    @Column(name = "updatedate")
    private String updateDate;

    @ManyToOne(fetch = FetchType.LAZY) // Schedule이 Author와 다대일 관계
    @JoinColumn(name = "author_id") // JoinColumn 은 외래 키를 나타냄
    private Author author;

    public Schedule(ScheduleRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.password = requestDto.getPassword();
        this.name = requestDto.getName();

        Author author = new Author(); // 새 author 객체 만들어서 ID만 설정
        author.setId(requestDto.getAuthorId());
        this.author = author; // JPA가 인식할 수 있도록 설정
    }
}
