package com.ducle.user_service.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ducle.user_service.model.entity.User;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<User> findByAuthId(Long authId);

    Page<User> findAll(Pageable pageable); // Add this method to support pagination
    
}
