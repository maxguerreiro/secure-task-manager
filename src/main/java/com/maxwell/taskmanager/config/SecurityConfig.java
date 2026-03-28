package com.maxwell.taskmanager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.maxwell.taskmanager.security.JwtFilter;

@Configuration
public class SecurityConfig {

	
	@Autowired
	private JwtFilter jwtFilter;
	
	
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
		.authorizeHttpRequests(auth -> auth.requestMatchers("/auth/**").permitAll()
				.requestMatchers(HttpMethod.POST, "/users").permitAll()
				.requestMatchers("/users").hasRole("ADMIN")
				
				.anyRequest().authenticated()).addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
		
	}

    @Bean
    PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}

}
