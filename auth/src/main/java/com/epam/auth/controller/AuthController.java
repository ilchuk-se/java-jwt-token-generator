package com.epam.auth.controller;

import com.epam.auth.repository.TokenRepository;
import com.epam.auth.util.JwtUtil;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

	private final HttpServletRequest request;
	private final TokenRepository tokenRepository;
	private final JwtUtil jwtUtil;

	@Autowired
	public AuthController(HttpServletRequest request, TokenRepository tokenRepository, JwtUtil jwtUtil) {
		this.request = request;
		this.tokenRepository = tokenRepository;
		this.jwtUtil = jwtUtil;
	}

	@GetMapping("/login/{id}")
	public String authorize(@PathVariable(name = "id") String userId){
		String refresh_token = jwtUtil.generateToken(userId, getUserAgent());
		tokenRepository.addToken(refresh_token);
		return refresh_token;
	}

	@GetMapping("/refresh")
	public String refresh(@PathVariable(name = "token") String tokenToRefresh){
		if(!jwtUtil.validateToken(tokenToRefresh)){
			return null;
		}
		tokenRepository.delete(tokenToRefresh);
		String newRefreshToken = jwtUtil.generateToken(
			jwtUtil.geUserId(tokenToRefresh),
			jwtUtil.geUserAgent(tokenToRefresh)
		);
		tokenRepository.addToken(newRefreshToken);
		return newRefreshToken;
	}

	@GetMapping("/tokens")
	public List<String> getAll(){
		return tokenRepository.getAll();
	}

	private String getUserAgent() {
		return request.getHeader("user-agent");
	}
}
