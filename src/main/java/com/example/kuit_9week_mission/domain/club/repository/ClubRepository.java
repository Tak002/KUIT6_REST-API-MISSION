package com.example.kuit_9week_mission.domain.club.repository;

import com.example.kuit_9week_mission.domain.club.model.Club;
import com.example.kuit_9week_mission.domain.club.model.ClubStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;

import static org.springframework.data.relational.core.query.Query.query;

@Repository
@RequiredArgsConstructor
public class ClubRepository {

    private final JdbcTemplate jdbc;

    private static final RowMapper<Club> MAPPER = (ResultSet rs, int rowNum) -> new Club(
            rs.getLong("club_id"),
            rs.getString("name"),
            rs.getString("description"),
            ClubStatus.valueOf(rs.getString("status"))
    );
    public List<Club> findByPage(Long page, int size){
        Long baseId = (page - 1) * size;
        String sql = """
            SELECT club_id, name, description, status
            FROM Clubs
            WHERE club_id > ?
            ORDER BY club_id ASC
            LIMIT ?
            """;
        return jdbc.query(sql, MAPPER, baseId, size);
    }

    public Long findLastId(){
        String sql = """
            SELECT MAX(club_id) AS last_id
            FROM Clubs
            """;
        return jdbc.queryForObject(sql, (rs, rowNum) -> rs.getLong("last_id"));
    }
}
