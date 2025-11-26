package com.example.kuit_9week_mission.domain.club.model;

import java.util.List;

public record ClubsOfStudentDto(
        String studentName,
        List<String> clubNames
) {
}
