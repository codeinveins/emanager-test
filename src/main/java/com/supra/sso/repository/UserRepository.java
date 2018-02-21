package com.supra.sso.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.supra.sso.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}