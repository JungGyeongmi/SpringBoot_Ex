package org.zerock.mreview.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.mreview.dto.MovieDTO;
import org.zerock.mreview.dto.PageRequestDTO;
import org.zerock.mreview.service.MovieService;
import org.zerock.mreview.service.MovieServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/movie")
@Log4j2
@RequiredArgsConstructor
public class MovieContoller {

    private final MovieService movieService; // final

    @GetMapping("/register")
    public void register() {
    }

    @PostMapping("register")
    public String register(MovieDTO movieDTO, RedirectAttributes ra) {
        log.info("movieDTO : " + movieDTO);
        Long mno = movieService.register(movieDTO);
        ra.addFlashAttribute("msg", mno+" 등록");
        return "redirect:/movie/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        log.info("pageRequestDTO: " + pageRequestDTO);
        model.addAttribute("result", movieService.getList(pageRequestDTO));
    }

    @GetMapping({"/read","/modify"})
    public void read(Long mno, PageRequestDTO pageRequestDTO, Model model) {
        log.info("pageRequestDTO: " + pageRequestDTO);     
        MovieDTO movieDTO = movieService.getMovie(mno);
        model.addAttribute("pageRequestDTO", pageRequestDTO);
        model.addAttribute("dto", movieDTO);
    }

}
