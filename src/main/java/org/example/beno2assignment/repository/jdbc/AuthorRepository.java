package org.example.beno2assignment.repository.jdbc;

import lombok.RequiredArgsConstructor;
import org.example.beno2assignment.entity.Author;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AuthorRepository {

    private final JdbcTemplate jdbcTemplate;

    // Lv1
    public Author findByName(String name) {
        String sql = "SELECT * FROM author WHERE name = ?";
        List<Author> result = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Author author = new Author();
            author.setId(rs.getLong("id"));
            author.setName(rs.getString("name"));
            author.setEmail(rs.getString("email"));
            author.setCreateDate(rs.getString("createdate"));
            author.setUpdateDate(rs.getString("updatedate"));
            return author;
        }, name);

        return result.isEmpty() ? null : result.get(0);
    }

    // Lv3
    public void save(Author author) {
        String sql = "INSERT INTO author (name, email, createdate, updatedate) VALUES (?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder(); //KeyHolder를 사용해서 자동 생성된 ID 값을 가져와야 함
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, author.getName());
            ps.setString(2, author.getEmail());
            ps.setString(3, author.getCreateDate());
            ps.setString(4, author.getUpdateDate());
            return ps;
        }, keyHolder);

        // 생성된 ID를 엔티티에 반영
        if (keyHolder.getKey() != null) {
            author.setId(keyHolder.getKey().longValue());
        }
    }

    // Lv3
    public Author findById(Long id) {
        String sql = "SELECT * FROM author WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            Author author = new Author();
            author.setId(rs.getLong("id"));
            author.setName(rs.getString("name"));
            author.setEmail(rs.getString("email"));
            author.setCreateDate(rs.getString("createdate"));
            author.setUpdateDate(rs.getString("updatedate"));
            return author;
        }, id);
    }
}
