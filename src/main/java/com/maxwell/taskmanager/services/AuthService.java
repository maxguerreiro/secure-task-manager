package com.maxwell.taskmanager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.maxwell.taskmanager.domain.User;
import com.maxwell.taskmanager.dtos.LoginRequestDTO;
import com.maxwell.taskmanager.repositories.UserRepository;
import com.maxwell.taskmanager.security.JwtService;

@Service
public class AuthService {
	
	@Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    /**
     * Authenticates a user and returns a JWT token.
     */
    public String login(LoginRequestDTO dto) {

        User user = repository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return jwtService.generateToken(user);
    }

}
