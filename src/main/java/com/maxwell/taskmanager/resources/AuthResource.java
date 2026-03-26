package com.maxwell.taskmanager.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maxwell.taskmanager.dtos.LoginRequestDTO;
import com.maxwell.taskmanager.services.AuthService;

/**
 * Controller responsible for authentication endpoints.
 */
@RestController
@RequestMapping("/auth")
public class AuthResource {

    @Autowired
    private AuthService service;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO dto) {
        String token = service.login(dto);
        return ResponseEntity.ok(token);
    }
}
