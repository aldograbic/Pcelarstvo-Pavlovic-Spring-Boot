package com.honeywebsite.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutUsController {

    @GetMapping("/o-nama")
    public String showAboutUs() {
        return "about-us";
    }
}