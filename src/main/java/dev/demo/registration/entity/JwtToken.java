package dev.demo.registration.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "token")
public class JwtToken {
	
	@Id
    @Column(name = "user_id", nullable = false)
    private Long userId;
	
	@Column(name = "access_token", nullable = false)
    private String accessToken;
	
	@Column(name = "expiration_in_ms", nullable = false)
    private Date expirationInMs;
	

}
