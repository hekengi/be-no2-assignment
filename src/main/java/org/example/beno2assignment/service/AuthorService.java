package org.example.beno2assignment.service;

import lombok.RequiredArgsConstructor;
import org.example.beno2assignment.dto.AuthorRequestDto;
import org.example.beno2assignment.entity.Author;
import org.example.beno2assignment.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;

    public Author createAuthor(AuthorRequestDto requestDto) {
        Author author = new Author();
        author.setName(requestDto.getName());
        author.setEmail(requestDto.getEmail());

        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        author.setCreateDate(now);
        author.setUpdateDate(now);

        authorRepository.save(author);
        return author;
    }
}
