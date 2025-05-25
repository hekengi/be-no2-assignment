package org.example.beno2assignment.repository.jdbc;

import lombok.RequiredArgsConstructor;
import org.example.beno2assignment.entity.Author;
import org.example.beno2assignment.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


@Repository
@RequiredArgsConstructor
public class ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    // DB에 저장
    public void save(Schedule schedule) {
        String sql = "INSERT INTO schedule (title, name, password, createdate, updatedate, author_id) VALUES (?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder(); // JdbcTemplate.update(...) 대신 KeyHolder를 사용해서 자동 생성된 ID 값을 가져와야 함

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, schedule.getTitle());
            ps.setString(2, schedule.getName());
            ps.setString(3, schedule.getPassword());
            ps.setString(4, schedule.getCreateDate());
            ps.setString(5, schedule.getUpdateDate());
            ps.setLong(6, schedule.getAuthor().getId()); // Author ID를 설정
            return ps;
        }, keyHolder);

        // 생성된 ID를 엔티티에 반영
        if (keyHolder.getKey() != null) {
            schedule.setId(keyHolder.getKey().longValue());
        }
    }

    // ID로 스케줄 수정
    public void update(Schedule schedule) {
        String sql = "UPDATE schedule SET title = ?, updatedate = ? ,author_id = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                schedule.getTitle(),
                schedule.getUpdateDate(),
                schedule.getAuthor().getId(), // Author ID를 설정
                schedule.getId());

    }

    // 전체 일정 조회
    public List<Schedule> findAll(Long author_id, String name, String updateDate) {
        StringBuilder sql = new StringBuilder("SELECT * FROM schedule");
        List<Object> params = new ArrayList<>();

        boolean hasAuthorId = author_id != null;
        boolean hasName = name != null && !name.isEmpty();
        boolean hasDate = updateDate != null && !updateDate.isEmpty();

        if (hasAuthorId || hasName || hasDate) {
            sql.append(" WHERE");
            List<String> conditions = new ArrayList<>();
            if (hasAuthorId) {
                conditions.add(" author_id = ?");
                params.add(author_id);
            }
            if (hasName) {
                conditions.add(" name = ?");
                params.add(name);
            }
            if (hasDate) {
                conditions.add(" DATE(updatedate) = ?");
                params.add(updateDate);
            }
            sql.append(String.join(" AND", conditions));
        }


        sql.append(" ORDER BY updatedate DESC");

        return jdbcTemplate.query(sql.toString(), (rs, rowNum) -> {
            Schedule schedule = new Schedule();
            schedule.setId(rs.getLong("id"));
            schedule.setName(rs.getString("name"));
            schedule.setTitle(rs.getString("title"));
            schedule.setPassword(rs.getString("password"));
            schedule.setCreateDate(rs.getString("createdate"));
            schedule.setUpdateDate(rs.getString("updatedate"));
            Author author = new Author();
            author.setId(rs.getLong("author_id"));
            schedule.setAuthor(author);  // [Lv4 수정] Schedule에 author 객체 설정
            return schedule;
        }, params.toArray());
    }



    // Id를 통해 스케줄을 조회
    public Schedule findById(Long id) {
        String sql = "SELECT s.*, a.id AS author_id, a.name AS author_name, a.email AS author_email " +
                "FROM schedule s " +
                "JOIN author a ON s.author_id = a.id " +
                "WHERE s.id = ?";

        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
            Schedule schedule = new Schedule();
            schedule.setId(rs.getLong("id"));
            schedule.setTitle(rs.getString("title"));
            schedule.setPassword(rs.getString("password"));
            schedule.setCreateDate(rs.getString("createdate"));
            schedule.setUpdateDate(rs.getString("updatedate"));
            schedule.setName(rs.getString("name")); // name 컬럼도 저장

            // 작성자 정보 설정
            Author author = new Author();
            author.setId(rs.getLong("author_id"));
            author.setName(rs.getString("author_name"));
            author.setEmail(rs.getString("author_email"));
            schedule.setAuthor(author);

            return schedule;
        });
    }



    // Lv2 선택한 일정 삭제
    public void delete(long id) {
        String sql = "DELETE FROM schedule WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }



}
