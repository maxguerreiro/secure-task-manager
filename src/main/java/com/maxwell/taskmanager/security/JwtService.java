package com.maxwell.taskmanager.security;

import java.security.Key;
import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.io.Decoder;
import io.jsonwebtoken.io.Decoders;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.maxwell.taskmanager.domain.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
	private final Key secretKey;

	public JwtService(@Value("${jwt.secret}") String secret) {
	    byte[] keyBytes = Decoders.BASE64.decode(secret);
	    this.secretKey = Keys.hmacShaKeyFor(keyBytes);
	}

    public String generateToken(User user) {

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("role", user.getRoles().name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1h
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }
    
    public String extractUsername(String token) {
    	return Jwts.parserBuilder()
    			.setSigningKey(secretKey)
    			.build()
    			.parseClaimsJws(token)
    			.getBody()
    			.getSubject();
    }
    
    public boolean isTokenValid(String token) {
    	try {
			Jwts.parserBuilder()
			.setSigningKey(secretKey)
			.build()
			.parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
    }
    
    public Claims getClaims(String token) {
    	return Jwts.parserBuilder()
    			.setSigningKey(secretKey)
    			.build()
    			.parseClaimsJws(token)
    			.getBody();
    }
}
