package org.example.beno2assignment.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Author {
    private Long id;
    private String name;
    private String email;
    private String createDate;
    private String updateDate;


}
