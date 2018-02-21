package com.supra.sso.service.impl;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.supra.sso.model.User;
import com.supra.sso.repository.ModuleRepository;
import com.supra.sso.repository.RoleRepository;
import com.supra.sso.repository.UserRepository;
import com.supra.sso.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    
	@Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Autowired
    private ModuleRepository moduleRepository;

    
    
    
    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>(roleRepository.findAll()));
        user.setModules(new HashSet<>(moduleRepository.findAll()));
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}