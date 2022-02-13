package org.zerock.clubex.controller;


import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.clubex.security.dto.ClubAuthMemberDTO;


@Controller
@Log4j2
@RequestMapping("/sample")
public class SampleController {

    @GetMapping("/all")
    public void exAll() {
        log.info("exAll...........");
    }

    @GetMapping("/member")
    public void member(@AuthenticationPrincipal ClubAuthMemberDTO authDTO) {
        log.info("member.........");
        log.info("ClubAuthMemberDTO: "+authDTO);
    }

    @GetMapping("/admin")
    public void exAdmin() {
        log.info("exAdmin...........");
    }
}
