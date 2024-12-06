package com.fpoly.java6.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth -> auth
//			.requestMatchers("/login", "/register", "/reset-password", "/", "/product-detail")
//				.permitAll().requestMatchers("/admin/**").hasRole("admin")
		.anyRequest().permitAll());
	return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
	return new BCryptPasswordEncoder();
    }
}
