package org.zerock.clubex.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.zerock.clubex.entity.ClubMember;
import org.zerock.clubex.entity.ClubMemberRole;
import org.zerock.clubex.repository.ClubMemberRepository;
import org.zerock.clubex.security.dto.ClubAuthMemberDTO;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class ClubOAuth2UserDetailsService extends DefaultOAuth2UserService {
    
    private final ClubMemberRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("ClubOAuth2UserDetailsService.....userRequest: "+userRequest);
        String clientName = userRequest.getClientRegistration().getClientName();
        log.info("clientName"+clientName);
        log.info(userRequest.getAdditionalParameters());

        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("===============================");

        oAuth2User.getAttributes().forEach((k, v)-> {
            log.info(k+":"+v);
        });
        
        String email = null;
        if(clientName.equals("Google"))
            email = oAuth2User.getAttribute("email");
            log.info("email : "+email);
            ClubMember member = saveSocialMember(email);

             return oAuth2User;

//            ClubAuthMemberDTO clubAuthMemberDTO =
//                new ClubAuthMemberDTO(member.getEmail(), member.getPassword(),true,
//                        member.getRoleSet().stream().map(role ->
//                            new SimpleGrantedAuthority("ROLE_"+role.name()))
//                .collect(Collectors.toList()), oAuth2User.getAttributes());
//
//                return clubAuthMemberDTO;
    }

    private ClubMember saveSocialMember(String email){
        Optional<ClubMember> result = repository.findByEmail(email);

        //기존에 동일한 이메일로 가입한 회원이 있는 경우에는 그대로 조회만
        if(result.isPresent()) {

            log.info("result.isPresent() "+result.isPresent());
            return result.get();

        }

        //없다면 회원 추가 패스워드는 1111 이름은 그냥 이메일 주소로
        ClubMember clubMember = ClubMember.builder()
            .email(email)
            .name(email)
            .password(passwordEncoder.encode("1111"))
            .fromSocial(true)
            .build();

        clubMember.addMemberRole(ClubMemberRole.USER);
        repository.save(clubMember);
        log.info("clubMember "+clubMember);
        return clubMember;
    }

}
