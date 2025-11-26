package com.example.kuit_9week_mission.domain.club.controller;

import com.example.kuit_9week_mission.domain.club.model.Club;
import com.example.kuit_9week_mission.domain.club.service.ClubMemberService;
import com.example.kuit_9week_mission.domain.club.service.ClubService;
import com.example.kuit_9week_mission.global.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clubs")
@RequiredArgsConstructor
public class ClubController {

    private final ClubService clubService;
    private final ClubMemberService clubMemberService;

    @GetMapping("/page/{page}")
    public ApiResponse<?> getAllClubs(@PathVariable Long page) {
        return ApiResponse.ok(clubService.getPagedClubs(page));
    }
    @PutMapping("/{clubId}")
    public ApiResponse<?> updateClub(@PathVariable Long clubId, @RequestBody Club club) {
        Club updatedClub = clubService.updateClub(clubId, club.name(), club.description(), club.status());
        return ApiResponse.ok(updatedClub);
    }
}
