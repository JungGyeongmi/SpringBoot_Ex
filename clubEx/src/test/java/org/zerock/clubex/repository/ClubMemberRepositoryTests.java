package org.zerock.clubex.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.zerock.clubex.entity.ClubMember;
import org.zerock.clubex.entity.ClubMemberRole;

import java.util.stream.IntStream;


@SpringBootTest
class ClubMemberRepositoryTests {

    @Autowired
    ClubMemberRepository clubMemberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void insertDummies() {

        IntStream.rangeClosed(1, 100).forEach(i->{

            ClubMember member = ClubMember.builder()
                    .email("user"+i+"@zerock.org")
                    .name("user"+i)
                    .password(passwordEncoder.encode("1111"))
                    .fromSocial(false)
                    .build();

            //default role
            member.addMemberRole(ClubMemberRole.USER);
            if(i > 80){
                member.addMemberRole(ClubMemberRole.MANAER);
            }
            if(i > 90){
                member.addMemberRole(ClubMemberRole.ADMIN);
            }
            clubMemberRepository.save(member);

        });
    }
}