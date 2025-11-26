package com.example.kuit_9week_mission.domain.club.repository;

import com.example.kuit_9week_mission.domain.club.model.Club;
import com.example.kuit_9week_mission.domain.club.model.ClubStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;

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
    public List<Club> findByPageWithStatus(Long page, int size,String status){
        Long baseId = (page - 1) * size;
        if (status == null) {
            String sql = """
                SELECT club_id, name, description, status
                FROM Clubs
                ORDER BY club_id ASC
                LIMIT ? OFFSET ?
                """;
            return jdbc.query(sql, MAPPER, size, baseId);

        }
        String sql = """
            SELECT club_id, name, description, status
            FROM Clubs
            WHERE status = ?
            ORDER BY club_id ASC
            LIMIT ? OFFSET ?
            """;
        return jdbc.query(sql, MAPPER, status, size, baseId);

    }

    public Long findLastId(String status){
        if (status == null) {

            String sql = """
            SELECT MAX(club_id) AS last_id
            FROM Clubs
            """;
            return jdbc.queryForObject(sql, (rs, rowNum) -> rs.getLong("last_id"));

        }
        String sql = """
            SELECT MAX(club_id) AS last_id
            FROM Clubs
            WHERE status = ?
            """;
        return jdbc.queryForObject(sql, (rs, rowNum) -> rs.getLong("last_id"), status);
    }

    public Club findById(Long clubId) {
        String sql = """
            SELECT club_id, name, description, status
            FROM Clubs
            WHERE club_id = ?
            """;
        return jdbc.queryForObject(sql, MAPPER, clubId);
    }

    public Club update(Club updatedClub) {
        String sql = """
            UPDATE Clubs
            SET name = ?, description = ?, status = ?
            WHERE club_id = ?
            """;
        jdbc.update(sql,
                updatedClub.name(),
                updatedClub.description(),
                updatedClub.status().name(),
                updatedClub.clubId()
        );
        return updatedClub;
    }

    public void deleteById(Long clubId) {
        String sql = """
            DELETE FROM Clubs
            WHERE club_id = ?
            """;
        jdbc.update(sql, clubId);
    }
}
