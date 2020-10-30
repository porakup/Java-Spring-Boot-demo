package dev.demo.registration.repository;

public interface UserRepositoryCustom {
	
	boolean checkDuplicateEmail(String email);
	
	boolean checkDuplicateTelephoneNumber(String telephoneNumber);

}
