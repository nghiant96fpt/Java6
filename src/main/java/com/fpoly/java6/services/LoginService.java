package com.fpoly.java6.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fpoly.java6.components.JwtUtil;
import com.fpoly.java6.entities.UserEntity;
import com.fpoly.java6.jpa.UserJPA;

@Service
public class LoginService {

    private final UserJPA userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public LoginService(UserJPA userRepository, JwtUtil jwtUtil) {
	this.userRepository = userRepository;
	this.passwordEncoder = new BCryptPasswordEncoder();
	this.jwtUtil = jwtUtil;
    }

    public String login(String username, String password) {
	UserEntity user = userRepository.findByUsername(username)
		.orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

	if (!passwordEncoder.matches(password, user.getPassword())) {
	    throw new IllegalArgumentException("Invalid username or password");
	}

	return jwtUtil.generateToken(username, user.getRole());
    }
}
