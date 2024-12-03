package com.fpoly.java6.components;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "IUhuQQpG1l3gA5aFf9SjfjRau2WiXYDIORDGWkggqNBIv4aGb5";
    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 2;

    private Key getSigningKey() {
	return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateToken(String username, String role) {
	Map<String, Object> claims = new HashMap<>();
	claims.put("role", role);
	claims.put("username", username);
	return Jwts.builder().setSubject(username).setClaims(claims).setIssuedAt(new Date())
		.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
		.signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    public String extractUsername(String token) {
	return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody().getSubject();
    }

    public String extractRole(String token) {
	return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody().get("role",
		String.class);
    }

    public boolean validateToken(String token, String username) {
	String extractedUsername = extractUsername(token);
	return extractedUsername.equals(username) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
	Date expiration = Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody()
		.getExpiration();
	return expiration.before(new Date());
    }
}
