package org.zerock.clubex.security.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.zerock.clubex.entity.ClubMember;
import org.zerock.clubex.repository.ClubMemberRepository;
import org.zerock.clubex.security.dto.ClubAuthMemberDTO;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class ClubUserDetailsService implements UserDetailsService {
    // UserDetailsService : JPA와 Spring Security를 연결하는 객체

    private final ClubMemberRepository clubMemberRepository;
    /*
     loadUserByUsername:
     매개변수 username을 가져와서 JPA로 가서 username이 있는지 여부를 확인하는 메서드
     리턴타입은 UserDetails (회원정보)
    */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("ClubUserDetailsService loadUserByUsername:"+username);
        Optional<ClubMember> result =
                clubMemberRepository.findByEmail(username);
        if(!result.isPresent()) {
            throw new UsernameNotFoundException("Check Email or Social");
        }
        ClubMember clubMember = result.get();
        log.info("-----------------------------------------------------------");
        log.info("clubMember: "+clubMember);
        ClubAuthMemberDTO clubAuthMemberDTO = new ClubAuthMemberDTO(
                clubMember.getEmail(), clubMember.getPassword(),
                clubMember.isFromSocial(),
                clubMember.getRoleSet().stream().map(
                                role -> new SimpleGrantedAuthority("ROLE_"+role.name()))
                        .collect(Collectors.toSet())
        );
        clubAuthMemberDTO.setName(clubMember.getName());
        clubAuthMemberDTO.setFromSocial(clubMember.isFromSocial());
        return clubAuthMemberDTO;
    }

}
