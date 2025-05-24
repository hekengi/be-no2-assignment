package org.example.beno2assignment.repository;

import lombok.RequiredArgsConstructor;
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
        String sql = "INSERT INTO schedule (title, password, createdate, updatedate, authorId) VALUES (?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder(); // JdbcTemplate.update(...) 대신 KeyHolder를 사용해서 자동 생성된 ID 값을 가져와야 함

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, schedule.getTitle());
            ps.setString(2, schedule.getPassword());
            ps.setString(3, schedule.getCreateDate());
            ps.setString(4, schedule.getUpdateDate());
            ps.setLong(5, schedule.getAuthorId());
            return ps;
        }, keyHolder);

        // 생성된 ID를 엔티티에 반영
        if (keyHolder.getKey() != null) {
            schedule.setId(keyHolder.getKey().longValue());
        }
    }

    // ID로 스케줄 수정
    public void update(Schedule schedule) {
        String sql = "UPDATE schedule SET title = ?, updatedate = ? ,authorId = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                schedule.getTitle(),
                schedule.getUpdateDate(),
                schedule.getAuthorId(),
                schedule.getId());

    }

    // 전체 일정 조회
    public List<Schedule> findAll(Long authorId, String updateDate) {
        StringBuilder sql = new StringBuilder("SELECT * FROM schedule");
        List<Object> params = new ArrayList<>();

        boolean hasAuthorId = authorId != null;
        boolean hasDate = updateDate != null && !updateDate.isEmpty();

        if (hasAuthorId || hasDate) {
            sql.append(" WHERE");
            if (hasAuthorId) {
                sql.append(" authorId = ?");
                params.add(authorId);
            }
            if (hasDate) {
                if (hasAuthorId) sql.append(" AND");
                sql.append(" DATE(updatedate) = ?");
                params.add(updateDate);
            }
        }

        sql.append(" ORDER BY updatedate DESC");

        return jdbcTemplate.query(sql.toString(), (rs, rowNum) -> {
            Schedule schedule = new Schedule();
            schedule.setId(rs.getLong("id"));
            schedule.setTitle(rs.getString("title"));
            schedule.setPassword(rs.getString("password"));
            schedule.setCreateDate(rs.getString("createdate"));
            schedule.setUpdateDate(rs.getString("updatedate"));
            schedule.setAuthorId(rs.getLong("authorId"));
            return schedule;
        }, params.toArray());
    }



    // Id를 통해 스케줄을 조회
    public Schedule findById(long id) {
        String sql = "SELECT * FROM schedule WHERE id = ?";

        // 쿼리 실행 후 결과를 Schedule 객체로 매핑
        // 단일 레코드 조회이므로 queryForObject 사용
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            Schedule schedule = new Schedule();
            schedule.setId(rs.getLong("id"));
            schedule.setTitle(rs.getString("title"));
            schedule.setPassword(rs.getString("password"));
            schedule.setCreateDate(rs.getString("createdate"));
            schedule.setUpdateDate(rs.getString("updatedate"));
            return schedule;
        }, id);
    }

    // Lv2 선택한 일정 삭제
    public void delete(long id) {
        String sql = "DELETE FROM schedule WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

}
