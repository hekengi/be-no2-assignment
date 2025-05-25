package org.example.beno2assignment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;


@Entity
@Table(name = "author")
@Getter @Setter @NoArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID 필드가 자동으로 증가하는 기본 키
    private Long id;
    private String name;
    private String email;

    @Column(name = "createdate")
    private String createDate;
    @Column(name = "updatedate")
    private String updateDate;


}
