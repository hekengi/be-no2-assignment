package org.example.beno2assignment.controller;

import lombok.RequiredArgsConstructor;
import org.example.beno2assignment.dto.AuthorRequestDto;
import org.example.beno2assignment.dto.AuthorResponseDto;
import org.example.beno2assignment.entity.Author;
import org.example.beno2assignment.service.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/author")
public class AuthorController {
    private final AuthorService authorService;

    // Lv3
    @PostMapping("")
    public ResponseEntity<AuthorResponseDto> createAuthor(@RequestBody AuthorRequestDto requestDto) {
        Author author = authorService.createAuthor(requestDto);
        return ResponseEntity.ok(new AuthorResponseDto(author));
    }

}
