package com.maxwell.taskmanager.security;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.maxwell.taskmanager.domain.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private final Key SECRET_KEY = Keys.hmacShaKeyFor(
            "my-super-secret-key-that-is-very-long-and-secure-123456".getBytes());

    public String generateToken(User user) {

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("role", user.getRoles().name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1h
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
    
    public String extractUsername(String token) {
    	return Jwts.parserBuilder()
    			.setSigningKey(SECRET_KEY)
    			.build()
    			.parseClaimsJws(token)
    			.getBody()
    			.getSubject();
    }
    
    public boolean isTokenValid(String token) {
    	try {
			Jwts.parserBuilder()
			.setSigningKey(SECRET_KEY)
			.build()
			.parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
    }
}
