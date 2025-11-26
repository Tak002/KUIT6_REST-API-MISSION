package com.example.kuit_9week_mission.domain.club.service;

import com.example.kuit_9week_mission.domain.club.model.ClubStatus;
import com.example.kuit_9week_mission.domain.club.repository.ClubMemberRepository;
import com.example.kuit_9week_mission.domain.club.repository.ClubRepository;
import com.example.kuit_9week_mission.domain.student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClubMemberService {

    private final StudentRepository studentRepository;
    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;


    // TODO 5: 현재 로그인한 학생의 동아리 가입 기능 구현(토큰 필요) - POST

    public void addMemberToClub(Long clubId, Long studentId) {
        // 동아리의 status 검증
        if (clubRepository.findById(clubId).status() != ClubStatus.ACTIVE) {
            throw new IllegalArgumentException("비활성화된 동아리에는 가입할 수 없습니다.");
        }
        // 이미 가입했는지 여부 검증
        if(clubMemberRepository.existByClubIdAndStudentId(clubId, studentId)) {
            throw new IllegalArgumentException("이미 가입된 동아리입니다.");
        }
        // 동아리 가입 처리
        clubMemberRepository.addMemberToClub(clubId, studentId);
    }
    // TODO 6: 현재 로그인한 학생이 속해있는 동아리 목록 조회(토큰 필요) - (학생의 이름 & 동아리 이름 모두 반환 => JOIN 잘 활용하기) - GET
    /*
     * 응답 DTO 구조는 반드시 아래 형태를 따를 것
     * {
     *   "isSuccess": true,
     *   "statusCode": 20000,
     *   "message": "요청에 성공했습니다",
     *   "data": {
     *       "studentName": "멤버1",
     *       "clubNames": ["동아리 3", "동아리 7", "동아리 10"]
     *   },
     *   "timestamp": "2025-10-24T00:37:07.469931"
     * }
     */



}
