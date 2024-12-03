package com.fpoly.java6.services;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fpoly.java6.entities.UserEntity;
import com.fpoly.java6.jpa.UserJPA;

@Service
public class UserService {

    private final UserJPA userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserJPA userRepository) {
	this.userRepository = userRepository;
	this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public UserEntity registerUser(String username, String rawPassword, String role) {
	if (userRepository.existsByUsername(username)) {
	    throw new IllegalArgumentException("Username already exists");
	}

	if (rawPassword.length() < 6) {
	    throw new IllegalArgumentException("Password error!");
	}

	String hashedPassword = passwordEncoder.encode(rawPassword);

	UserEntity user = new UserEntity();
	user.setUsername(username);
	user.setPassword(hashedPassword);
	user.setRole(role);

	return userRepository.save(user);
    }

    public List<UserEntity> getAll() {
	return userRepository.findAll();
    }

    public UserEntity findByUsername(String username) {
	Optional<UserEntity> userOptional = userRepository.findByUsername(username);
	return userOptional.isPresent() ? userOptional.get() : null;
    }
}
