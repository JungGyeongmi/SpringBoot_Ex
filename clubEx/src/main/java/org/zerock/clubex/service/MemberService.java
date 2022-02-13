package org.zerock.clubex.service;

import org.zerock.clubex.dto.ClubMemberDTO;
import org.zerock.clubex.entity.ClubMember;
import org.zerock.clubex.security.dto.ClubAuthMemberDTO;

public interface MemberService {
    // clubAuthMemberDTO는 session, bean이다
//    public void modify(ClubAuthMemberDTO clubAuthMemberDTO);

    public void modify(ClubMemberDTO clubAMemberDTO);

    boolean loginCheck(String username, String password);

    void updateClubMemberDTO(ClubMemberDTO clubMemberDTO);

    default ClubMember dtoToEntity(ClubMemberDTO clubMemberDTO) {
        ClubMember clubMember = ClubMember.builder()
                .email(clubMemberDTO.getEmail())
                .name(clubMemberDTO.getName())
                .fromSocial(clubMemberDTO.isFromSocial())
                // .roleSet(clubMemberDTO.getRoleSet())
                .build();
        return clubMember;
    }

    default ClubMemberDTO entitiesToDTO(ClubMember clubMember) {
        ClubMemberDTO clubMemberDTO = ClubMemberDTO.builder()
                .email(clubMember.getEmail())
                .name(clubMember.getName())
                .fromSocial(clubMember.isFromSocial())
                // .roleSet(clubMember.getRoleSet())
                .regDate(clubMember.getRegDate())
                .modDate(clubMember.getModDate())
                .build();
        return clubMemberDTO;
    }
}
