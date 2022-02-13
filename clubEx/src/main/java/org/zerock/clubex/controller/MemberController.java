package org.zerock.clubex.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.clubex.dto.ClubMemberDTO;
import org.zerock.clubex.security.dto.ClubAuthMemberDTO;
import org.zerock.clubex.service.MemberService;

import java.util.ArrayList;
import java.util.List;

@Controller
@Log4j2
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
   

    @GetMapping("/login")
    public void login(){
        
        log.info("login get....");
    }

    @PostMapping("/login")
    public String loginForm(String username, String password){
        String resultUrl = "";
        log.info("login get....");
        boolean logincheck = memberService.loginCheck(username, password);
        log.info("logincheck: " + logincheck);
        resultUrl = logincheck?"redirect:/":"redirect:/member/login?error=fail";
        log.info("result URL  >> "+resultUrl);
        return resultUrl;
    }

    /*@GetMapping("/modify")
    public void modify(@AuthenticationPrincipal ClubAuthMemberDTO authDTO, Model model){ 
         log.info("modify get....");
        log.info("minji usernmae " + authDTO.getEmail());
        log.info("password " + authDTO.getPassword());
        model.addAttribute("authDTO", authDTO);
    }

    @PostMapping("/modify")
    public String modifyPass(RedirectAttributes rd, ClubMemberDTO clubMemberDTO, @AuthenticationPrincipal ClubAuthMemberDTO authDTO){
        log.info("modify post.............");
        log.info("post mapping test :"+authDTO.getEmail());
        log.info("post mapping test :"+authDTO.getPassword());

        memberService.modify(authDTO);

        return "redirect:/member/modify";
    }*/

    @GetMapping("modify")
    public void modify(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMemberDTO,
                       ClubMemberDTO clubMemberDTO,
                       Model model) {
      model.addAttribute("auth", clubAuthMemberDTO);
      List<String> roleNames = new ArrayList<>();
      clubAuthMemberDTO.getAuthorities().forEach(authority ->{
        roleNames.add(authority.getAuthority());
      });
      model.addAttribute("member", clubMemberDTO);
      model.addAttribute("roleNames", roleNames);

      log.info("get modify controller - clubMemberDTO : "+clubMemberDTO);
    }
  
    @PostMapping("modify")
    public String modifyForm(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMemberDTO,
                             ClubMemberDTO clubMemberDTO,
                             Model model){
      String result="redirect:/member/modify";
      memberService.modify(clubMemberDTO);

      clubAuthMemberDTO.changePassword(clubMemberDTO.getPassword());

      log.info("ClubAuthMemberDTO:"+clubAuthMemberDTO.getPassword());
      log.info("ClubMemberDTO:"+clubMemberDTO.getPassword());
      return result;
    }
}
