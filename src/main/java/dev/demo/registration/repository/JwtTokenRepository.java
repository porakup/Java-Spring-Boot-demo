package dev.demo.registration.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dev.demo.registration.entity.JwtToken;

@Repository
public interface JwtTokenRepository extends CrudRepository<JwtToken, Long> {
	
	@Query(value = "SELECT T.* FROM TOKEN T WHERE T.ACCESS_TOKEN = :accessToken", nativeQuery = true)
    JwtToken findByAccessToken(@Param("accessToken") String accessToken);

}
