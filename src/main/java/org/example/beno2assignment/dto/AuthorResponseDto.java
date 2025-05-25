package org.example.beno2assignment.dto;

import org.example.beno2assignment.entity.Author;

public class AuthorResponseDto {
    private Long id;
    private String name;
    private String email;

    public AuthorResponseDto(Author author) {
        this.id = author.getId();
        this.name = author.getName();
        this.email = author.getEmail();
    }
}

