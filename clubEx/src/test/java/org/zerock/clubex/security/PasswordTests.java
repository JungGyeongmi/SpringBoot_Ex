package org.zerock.clubex.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.zerock.clubex.entity.ClubMember;
import org.zerock.clubex.repository.ClubMemberRepository;

import java.util.Optional;

@SpringBootTest
public class PasswordTests {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ClubMemberRepository clubMemberRepository;

    @Test
    public void testEncode() {
        String password = "1111";
        String enPw = passwordEncoder.encode(password);
        System.out.println("enPw: " + enPw);
        boolean matchResult = passwordEncoder.matches(password, enPw);
        System.out.println("matchResult: " + matchResult);

    }

    @Test
    public void testRead() {
        Optional<ClubMember> result = clubMemberRepository.findByEmail("user95@zerock.org");
        ClubMember clubMember = result.get();

        System.out.println(clubMember);
    }
}
