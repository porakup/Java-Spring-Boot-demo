package dev.demo.registration.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
	
	private Long id;
	
	@NotBlank(message = "First Name is required")
	private String firstName;
	
	@NotBlank(message = "Last Name is required")
	private String lastName;
	
	@NotBlank(message = "Username is required.")
	private String username;
	
	@NotBlank(message = "Passwordd is required.")
	@Size(min = 8, max = 50, message = "password length should be greater or equal than 8.")
	private String password;
	
	@NotBlank(message = "Email is required.")
	@Email(message = "Email is invalid.")
	private String email;
	
	@NotBlank(message = "Address is required.")
	private String address;
	
	@NotBlank(message = "Telephone Number is required.")
	@Size(min = 9, max = 10, message = "Telephone Number length should be greater than 9 and less then 10.")
	private String telephoneNumber;
	
	@NotBlank(message = "Salary is required.")
	private String salary;
	
	private String referenceCode;
	
	private String memberType;
	
}
