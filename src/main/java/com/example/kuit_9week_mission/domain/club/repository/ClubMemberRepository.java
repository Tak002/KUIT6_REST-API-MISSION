package com.example.kuit_9week_mission.domain.club.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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

    // TODO: TODO 6을 구현하기 위해선 Club_Members 와 Clubs 테이블간의 JOIN 을 적절히 활용해야한다!


}
