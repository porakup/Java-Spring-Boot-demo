package dev.demo.registration.util;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import dev.demo.registration.entity.JwtToken;
import dev.demo.registration.entity.User;
import dev.demo.registration.exception.TokenException;
import dev.demo.registration.repository.JwtTokenRepository;
import dev.demo.registration.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {
	
	@Value("${jwt.secret}")
	private String jwtSecret;


	@Value("${jwt.token.prefix}")
	private String jwtTokenPrefix;

	@Value("${jwt.expiration-in-ms}")
	private Long jwtExpirationInMs;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtTokenRepository jwtTokenRepository;

	public JwtToken generateToken(String username, String encryptUsername){

	String accessToken = Jwts.builder().setSubject(username)
	                     .setExpiration(new Date(System.currentTimeMillis()+ jwtExpirationInMs))
	                     .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	
	
	JwtToken jwtToken = new JwtToken();
	User user = userRepository.findByUsername(encryptUsername);
	jwtToken.setUserId(user.getId());
	jwtToken.setAccessToken(accessToken);
	jwtToken.setExpirationInMs(new Date(System.currentTimeMillis()+ jwtExpirationInMs));
	
	return jwtToken;
	
	}
	
	
	public boolean validateToken(String accessToken) throws Exception{
		
		String token = resolveToken(accessToken);
		
		if(token == null){
		   throw new TokenException("Permission denied.");
		}
				
		JwtToken jwtToken = jwtTokenRepository.findByAccessToken(token);
		
		if(jwtToken == null) {
			
			throw new TokenException("Access token is invalid.");
		}
		
		Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
		
		if(claims.getExpiration().before(new Date())){
			throw new TokenException("Access token expired.");
		}
		return true;
	}


	private String resolveToken(String accessToken){
	String bearerToken = accessToken;
		if(bearerToken != null && bearerToken.startsWith(jwtTokenPrefix)){
		 return bearerToken.substring(7, bearerToken.length());  
		}
		return null;
	}

}
