package com.frontdesk.test.human_in_the_loop.repository;

import com.frontdesk.test.human_in_the_loop.model.HelpRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HelpRequestRepository extends JpaRepository<HelpRequest, Long> {
}
