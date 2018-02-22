package com.supra.sso.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.supra.sso.model.UserToken;

public interface UserTokenRepository extends JpaRepository<UserToken, Long> {

}
