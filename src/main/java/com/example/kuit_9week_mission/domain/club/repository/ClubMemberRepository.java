package com.example.kuit_9week_mission.domain.club.repository;

import com.example.kuit_9week_mission.domain.club.model.Club;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.time.LocalTime.now;

@Repository
@RequiredArgsConstructor
public class ClubMemberRepository {

    private final JdbcTemplate jdbc;


    public Boolean existByClubIdAndStudentId(Long clubId, Long studentId) {
        String sql = "SELECT 1 FROM Club_Members WHERE club_id = ? AND student_id = ?";
        return jdbc.query(sql, rs -> {
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        }, clubId, studentId);
    }

    public void addMemberToClub(Long clubId, Long studentId) {
        String sql = "INSERT INTO Club_Members (join_date, club_id, student_id) VALUES (?, ?, ?)";
        jdbc.update(sql, LocalDate.now(), clubId, studentId);
    }

    public List<String> findClubsOfStudent(Long studentId) {
        String sql = """
            SELECT c.name
            FROM Club_Members cm
            JOIN Clubs c ON c.club_id = cm.club_id
            WHERE cm.student_id = ?
            """;
        List<String> clubs = jdbc.query(
                sql,
                (rs, rowNum) -> rs.getString("name"),
                studentId
        );
        return clubs;
    }
}
