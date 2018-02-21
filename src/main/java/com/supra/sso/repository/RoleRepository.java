package com.supra.sso.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.supra.sso.model.Role;


public interface RoleRepository extends JpaRepository<Role, Long>{

	
}