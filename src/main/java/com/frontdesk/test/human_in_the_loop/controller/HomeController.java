package com.frontdesk.test.human_in_the_loop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("message", "Welcome to Frontdesk AI 👋");
        return "index"; // renders index.html
    }
}
