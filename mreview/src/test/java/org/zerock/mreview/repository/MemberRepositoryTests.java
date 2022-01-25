package org.zerock.mreview.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.mreview.entity.Member;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTests {

    @Autowired
    private  MemberRepository memberRepository;


    @Test
    public void insertReviewers() {
        IntStream.rangeClosed(1, 100).forEach(i-> {

            Member member = Member.builder()
                    .id("user"+i)
                    .pw("0426")
                    .email("rr"+i+"@org.zero.ck")
                    .nickname("reviewer"+i)
                    .build();

            memberRepository.save(member);
        });
    }
}