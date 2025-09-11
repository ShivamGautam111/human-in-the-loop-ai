package com.frontdesk.test.human_in_the_loop.service;

import com.frontdesk.test.human_in_the_loop.model.HelpRequest;
import com.frontdesk.test.human_in_the_loop.model.KnowledgeBase;
import com.frontdesk.test.human_in_the_loop.repository.HelpRequestRepository;
import com.frontdesk.test.human_in_the_loop.repository.KnowledgeBaseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AgentService {

    private final HelpRequestRepository helpRequestRepo;
    private final KnowledgeBaseRepository kbRepo;

    public AgentService(HelpRequestRepository helpRequestRepo,
                        KnowledgeBaseRepository kbRepo) {
        this.helpRequestRepo = helpRequestRepo;
        this.kbRepo = kbRepo;
    }

    /**
     * Handle a customer question:
     * 1. Check knowledge base
     * 2. If found → answer immediately
     * 3. If not found → create HelpRequest and mark as PENDING
     */
    public String handleCustomerQuestion(String question) {
        if (question == null || question.trim().isEmpty()) {
            return "AI: Please provide a question.";
        }

        String q = question.trim();

        // 1) Look up in Knowledge Base
        Optional<KnowledgeBase> kbEntry = kbRepo.findByQuestionIgnoreCase(q);
        if (kbEntry.isPresent()) {
            return "AI: " + kbEntry.get().getAnswer();
        }

        // 2) Not found → escalate to supervisor
        HelpRequest req = new HelpRequest();
        req.setCustomerId("caller-web-guest"); // Simulated caller id
        req.setCustomerQuestion(q);
        req.setStatus("PENDING");
        req.setCreatedAt(LocalDateTime.now());
        req.setUpdatedAt(LocalDateTime.now());
        helpRequestRepo.save(req);

        System.out.println("SUPERVISOR NOTIFY → New help request #" + req.getId()
                + " | Question: \"" + q + "\"");

        // 3) Return escalation message
        return "AI: Let me check with my supervisor and get back to you.";
    }
}
