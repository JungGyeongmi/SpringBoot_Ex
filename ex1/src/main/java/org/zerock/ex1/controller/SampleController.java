package org.zerock.ex1.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class SampleController {

    @GetMapping("/hello")
    public String[] hello() {
        return new String[]{"hello", "world"};
    }
}