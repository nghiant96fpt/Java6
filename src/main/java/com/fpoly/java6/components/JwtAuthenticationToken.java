package com.fpoly.java6.components;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final UserDetails principal;
    private final String role;

    public JwtAuthenticationToken(UserDetails principal, String role,
	    Collection<? extends GrantedAuthority> authorities) {
	super(authorities);
	this.principal = principal;
	this.role = role;
	super.setAuthenticated(true); // Must call this if you use a custom token
    }

    public JwtAuthenticationToken(UserDetails principal, String role) {
	super(null);
	this.principal = principal;
	this.role = role;
	setAuthenticated(false); // Mark as not authenticated initially
    }

    @Override
    public Object getCredentials() {
	return null; // No credentials required for JWT
    }

    @Override
    public Object getPrincipal() {
	return principal;
    }

    public String getRole() {
	return role;
    }
}
