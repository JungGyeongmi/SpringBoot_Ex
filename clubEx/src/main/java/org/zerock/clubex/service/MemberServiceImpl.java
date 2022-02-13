package org.zerock.clubex.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.zerock.clubex.dto.ClubMemberDTO;
import org.zerock.clubex.entity.ClubMember;
import org.zerock.clubex.repository.ClubMemberRepository;
import org.zerock.clubex.security.dto.ClubAuthMemberDTO;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class MemberServiceImpl implements MemberService{
    private final ClubMemberRepository clubMemberRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public boolean loginCheck(String username, String password) {
        
        log.info("loginCheck.....");
        boolean passResult = false;
        String enPw = passwordEncoder.encode("1");
        passResult = passwordEncoder.matches(password, enPw);
        boolean usernameResult = username.equals("user1")?true:false;
        return passResult && usernameResult;
    }

    @Override
    public void modify(ClubMemberDTO clubMemberDTO) {
        Optional<ClubMember> result = clubMemberRepository.findById(clubMemberDTO.getEmail());

        ClubMember clubMember = result.get();
        log.info("modify member service / search clubmember " + clubMember);
        clubMember.changePassword(clubMemberDTO.getPassword());
        log.info("change password in serviceimpl : "+clubMemberDTO.getPassword());
        clubMemberRepository.save(clubMember);
    }

    // @Override
    // public void modify(ClubMemberDTO clubAMemberDTO) {
    //     Optional<ClubMember> result = clubMemberRepository.findById(clubAMemberDTO.getEmail());
    //     ClubMember clubMember = result.get();
    //     clubMember.changePassword(clubAMemberDTO.getPassword());
        
    //     clubMemberRepository.save(clubMember);
    // }

    @Override
    public void updateClubMemberDTO(ClubMemberDTO clubMemberDTO) {
      log.info("updateClubMemberDTO..clubMemberDTO:"+clubMemberDTO);
//       repository.save
    }

}
