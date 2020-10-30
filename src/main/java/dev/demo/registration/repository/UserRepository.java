package dev.demo.registration.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dev.demo.registration.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>, UserRepositoryCustom {
	
	@Query(value = "SELECT U.* FROM USER U WHERE U.TELEPHONE_NUMBER = :telephoneNumber", nativeQuery = true)
    User findByTelephoneNumber(@Param("telephoneNumber") String telephoneNumber);
	
	@Query(value = "SELECT U.* FROM USER U WHERE U.EMAIL = :email", nativeQuery = true)
    User findByEmail(@Param("email") String email);

	@Query(value = "SELECT U.* FROM USER U WHERE U.USERNAME = :username", nativeQuery = true)
    User findByUsername(@Param("username") String username);
	
}
