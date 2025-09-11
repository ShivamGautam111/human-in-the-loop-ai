package com.frontdesk.test.human_in_the_loop;

import com.frontdesk.test.human_in_the_loop.model.KnowledgeBase;
import com.frontdesk.test.human_in_the_loop.repository.KnowledgeBaseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // enable scheduled tasks like TimeoutService
public class HumanInTheLoopApplication {

    public static void main(String[] args) {
        SpringApplication.run(HumanInTheLoopApplication.class, args);
    }

    /**
     * Seed a few Knowledge Base entries on startup (idempotent).
     */
    @Bean
    CommandLineRunner seed(KnowledgeBaseRepository kbRepo) {
        return args -> {
            seedIfMissing(kbRepo,
                    "What are your hours?",
                    "We’re open Mon–Fri, 9am–5pm.");

            seedIfMissing(kbRepo,
                    "Do you do hair coloring?",
                    "Yes, full + partial highlights available.");

            seedIfMissing(kbRepo,
                    "Do you accept walk-ins?",
                    "Walk-ins welcome, but appointments preferred.");
        };
    }

    /**
     * Helper method: only insert if not already present.
     */
    private void seedIfMissing(KnowledgeBaseRepository kbRepo, String q, String a) {
        if (kbRepo.findByQuestionIgnoreCase(q).isEmpty()) {
            kbRepo.save(new KnowledgeBase(q, a));
            System.out.println("KB SEED → \"" + q + "\"");
        }
    }
}
