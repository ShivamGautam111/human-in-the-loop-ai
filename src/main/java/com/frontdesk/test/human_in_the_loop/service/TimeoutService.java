package com.frontdesk.test.human_in_the_loop.service;

import com.frontdesk.test.human_in_the_loop.model.HelpRequest;
import com.frontdesk.test.human_in_the_loop.repository.HelpRequestRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TimeoutService {

    private final HelpRequestRepository repo;

    // demo timeout window (e.g., 2 minutes)
    private static final int TIMEOUT_MINUTES = 2;

    public TimeoutService(HelpRequestRepository repo) {
        this.repo = repo;
    }

    // run every 30 seconds
    @Scheduled(fixedDelay = 30_000)
    public void markStalePendingAsUnresolved() {
        LocalDateTime cutoff = LocalDateTime.now().minusMinutes(TIMEOUT_MINUTES);
        List<HelpRequest> all = repo.findAll();
        all.stream()
                .filter(r -> "PENDING".equals(r.getStatus()))
                .filter(r -> r.getCreatedAt() != null && r.getCreatedAt().isBefore(cutoff))
                .forEach(r -> {
                    r.setStatus("UNRESOLVED");
                    r.setUpdatedAt(LocalDateTime.now());
                    repo.save(r);
                    System.out.println("TIMEOUT → Request #" + r.getId() + " marked UNRESOLVED");
                });
    }
}
