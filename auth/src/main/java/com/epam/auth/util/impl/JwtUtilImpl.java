package com.epam.auth.util.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.epam.auth.util.JwtUtil;
import java.util.Date;

public final class JwtUtilImpl implements JwtUtil {

	private String secret;
	private String expirationTime;
	private Algorithm algorithm;

	public JwtUtilImpl(String secret, String expirationTime, Algorithm algorithm) {
		this.secret = secret;
		this.expirationTime = expirationTime;
		this.algorithm = algorithm;
	}

	@Override
	public String generateToken(String userId, String userAgent) {
		return JWT.create()
			.withSubject(userId)
			.withExpiresAt(getExpirationDate())
			.withIssuer(userAgent)
			.sign(algorithm);
	}

	@Override
	public String geUserAgent(String authToken) {
		return decodeJwtToken(authToken).getIssuer();
	}

	@Override
	public String geUserId(String authToken) {
		return decodeJwtToken(authToken).getSubject();
	}

	@Override
	public boolean validateToken(String authToken) {
		return decodeJwtToken(authToken).
			getExpiresAt()
			.after(getCurrentTime());
	}

	private Date getCurrentTime(){
		return new Date(System.currentTimeMillis());
	}

	private Date getExpirationDate(){
		return new Date(getCurrentTime().getTime() + Long.parseLong(expirationTime) * 100);
	}

	private DecodedJWT decodeJwtToken(String JwtToken){
		JWTVerifier verifier = JWT.require(algorithm).build();
		return verifier.verify(JwtToken);
	}
}
