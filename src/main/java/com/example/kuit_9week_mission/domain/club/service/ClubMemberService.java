package com.example.kuit_9week_mission.domain.club.service;

import com.example.kuit_9week_mission.domain.club.model.ClubStatus;
import com.example.kuit_9week_mission.domain.club.model.ClubsOfStudentDto;
import com.example.kuit_9week_mission.domain.club.repository.ClubMemberRepository;
import com.example.kuit_9week_mission.domain.club.repository.ClubRepository;
import com.example.kuit_9week_mission.domain.student.model.Student;
import com.example.kuit_9week_mission.domain.student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClubMemberService {

    private final StudentRepository studentRepository;
    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;


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
    public ClubsOfStudentDto getClubsOfStudentV1(Long studentId) {
        List<String> clubsName = clubMemberRepository.findClubsOfStudent(studentId);
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new IllegalArgumentException("해당 학생이 존재하지 않습니다."));
        return new ClubsOfStudentDto(student.name(), clubsName);
    }


}
