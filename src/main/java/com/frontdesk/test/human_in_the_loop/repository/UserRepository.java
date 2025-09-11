package com.frontdesk.test.human_in_the_loop.repository;

import com.frontdesk.test.human_in_the_loop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
