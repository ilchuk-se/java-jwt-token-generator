package com.epam.auth.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class TokenRepository {
	private final List<String> tokens = new ArrayList<>();

	public void addToken(String token){
		tokens.add(token);
	}

	public List<String> getAll(){
		return tokens;
	}

	public void delete(String token){
		tokens.remove(token);
	}
}
