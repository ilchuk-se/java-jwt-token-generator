package com.epam.auth.util;

public interface JwtUtil {
	String generateToken(String userId, String userAgent);
	String geUserAgent(String authToken);
	String geUserId(String authToken);
	boolean validateToken(String authToken);
}
