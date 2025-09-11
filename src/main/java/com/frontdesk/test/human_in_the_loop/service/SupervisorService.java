package com.frontdesk.test.human_in_the_loop.service;

import org.springframework.scheduling.annotation.Scheduled;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import com.frontdesk.test.human_in_the_loop.model.HelpRequest;
import com.frontdesk.test.human_in_the_loop.model.KnowledgeBase;
import com.frontdesk.test.human_in_the_loop.repository.HelpRequestRepository;
import com.frontdesk.test.human_in_the_loop.repository.KnowledgeBaseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SupervisorService {

    private final HelpRequestRepository helpRequestRepo;
    private final KnowledgeBaseRepository knowledgeBaseRepo;

    public SupervisorService(HelpRequestRepository helpRequestRepo, KnowledgeBaseRepository knowledgeBaseRepo) {
        this.helpRequestRepo = helpRequestRepo;
        this.knowledgeBaseRepo = knowledgeBaseRepo;
    }

    public void resolveRequest(Long id, String answer) {
        HelpRequest req = helpRequestRepo.findById(id).orElseThrow();
        req.setStatus("RESOLVED");
        req.setSupervisorAnswer(answer);
        req.setResolvedAt(LocalDateTime.now());
        helpRequestRepo.save(req);

        // Save to Knowledge Base
        KnowledgeBase kb = new KnowledgeBase(req.getCustomerQuestion(), answer);
        knowledgeBaseRepo.save(kb);

        System.out.println("AI: Hey customer, I got the answer from my supervisor → " + answer);
    }
}
