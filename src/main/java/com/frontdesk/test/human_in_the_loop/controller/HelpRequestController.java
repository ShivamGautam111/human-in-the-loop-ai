    package com.frontdesk.test.human_in_the_loop.controller;

    import com.frontdesk.test.human_in_the_loop.model.HelpRequest;
    import com.frontdesk.test.human_in_the_loop.repository.HelpRequestRepository;
    import com.frontdesk.test.human_in_the_loop.service.AgentService;
    import com.frontdesk.test.human_in_the_loop.service.SupervisorService;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.servlet.mvc.support.RedirectAttributes;

    import java.util.List;

    @Controller
    public class HelpRequestController {

        private final AgentService agentService;
        private final SupervisorService supervisorService;
        private final HelpRequestRepository helpRequestRepo;

        public HelpRequestController(AgentService agentService,
                                     SupervisorService supervisorService,
                                     HelpRequestRepository helpRequestRepo) {
            this.agentService = agentService;
            this.supervisorService = supervisorService;
            this.helpRequestRepo = helpRequestRepo;
        }

        /**
         * Show all help requests
         * URL: /requests
         */
        @GetMapping("/requests")
        public String showRequests(Model model) {
            List<HelpRequest> requests = helpRequestRepo.findAll();
            model.addAttribute("requests", requests);
            return "requests"; // renders requests.html
        }

        /**
         * Handle customer asking a question
         * URL: /ask
         */
        @PostMapping("/ask")
        public String askQuestion(@RequestParam String question,
                                  RedirectAttributes redirectAttributes) {
            String reply = agentService.handleCustomerQuestion(question);
            redirectAttributes.addFlashAttribute("askResponse", reply);
            return "redirect:/"; // redirect back to index.html
        }

        /**
         * Supervisor resolves request
         * URL: /resolve
         */
        @PostMapping("/resolve")
        public String resolveRequest(@RequestParam Long id,
                                     @RequestParam String answer,
                                     RedirectAttributes redirectAttributes) {
            try {
                supervisorService.resolveRequest(id, answer);
                redirectAttributes.addFlashAttribute("success",
                        "✅ Request " + id + " resolved successfully!");
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("error",
                        "❌ Failed to resolve request " + id + ".");
            }
            return "redirect:/requests";
        }
    }
