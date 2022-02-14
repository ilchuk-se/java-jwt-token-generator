package com.epam.auth.config;

import com.auth0.jwt.algorithms.Algorithm;
import com.epam.auth.util.JwtUtil;
import com.epam.auth.util.impl.JwtUtilImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtUtilConfig {
	@Value("${jwt.secret}")
	private String secret;
	@Value("${jwt.expiration}")
	private String expirationTime;

	@Bean
	JwtUtil jwtUtil(){
		return new JwtUtilImpl(secret, expirationTime, Algorithm.HMAC256(secret.getBytes()));
	}
}
