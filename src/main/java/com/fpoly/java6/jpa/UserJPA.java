package com.fpoly.java6.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpoly.java6.entities.UserEntity;

public interface UserJPA extends JpaRepository<UserEntity, Integer> {
    boolean existsByUsername(String username);

    Optional<UserEntity> findByUsername(String username);
}
