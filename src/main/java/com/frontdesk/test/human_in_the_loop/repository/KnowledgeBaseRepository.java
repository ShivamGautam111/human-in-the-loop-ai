package com.frontdesk.test.human_in_the_loop.repository;

import com.frontdesk.test.human_in_the_loop.model.KnowledgeBase;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface KnowledgeBaseRepository extends JpaRepository<KnowledgeBase, Long> {

    // Custom query method to find by question ignoring case
    Optional<KnowledgeBase> findByQuestionIgnoreCase(String question);
}
