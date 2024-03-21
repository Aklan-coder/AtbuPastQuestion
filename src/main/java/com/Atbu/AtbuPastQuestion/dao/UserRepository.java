package com.Atbu.AtbuPastQuestion.dao;

import com.Atbu.AtbuPastQuestion.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
