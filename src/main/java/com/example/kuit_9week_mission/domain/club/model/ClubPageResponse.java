package com.example.kuit_9week_mission.domain.club.model;

import java.util.List;

public record ClubPageResponse(
        List<Club> data,
        Long lastId,
        boolean hasNext
) {
}
