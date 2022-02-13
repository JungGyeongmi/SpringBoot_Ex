package org.zerock.clubex.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log4j2
public class CommonController {

    @GetMapping({"/", "index", ""})
    public String index() {
        log.info("index.......");
        return "index";
    }

    @GetMapping("/accessError")
    public void accessError(Authentication auth, Model model){
        log.info("access denied : " +auth);
        model.addAttribute("msg","Access Denied");
    }

}
