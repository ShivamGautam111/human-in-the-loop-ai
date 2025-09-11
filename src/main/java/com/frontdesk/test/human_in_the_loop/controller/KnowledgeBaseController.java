package com.frontdesk.test.human_in_the_loop.controller;

import com.frontdesk.test.human_in_the_loop.repository.KnowledgeBaseRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class KnowledgeBaseController {

    private final KnowledgeBaseRepository kbRepo;

    public KnowledgeBaseController(KnowledgeBaseRepository kbRepo) {
        this.kbRepo = kbRepo;
    }

    @GetMapping({"/knowledge", "/kb"})
    public String viewKnowledgeBase(Model model) {
        model.addAttribute("entries", kbRepo.findAll());
        return "knowledge";   // MUST match knowledge.html exactly
    }
}
