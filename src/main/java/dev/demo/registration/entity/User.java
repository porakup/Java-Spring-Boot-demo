package dev.demo.registration.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;


@Entity
@Data
@Table(name = "user")
public class User {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
	
	@Column(name = "first_name")
    private String firstName;
	
	@Column(name = "last_name")
    private String lastName;
	
	@Column(name = "username")
    private String username;
	
	@Column(name = "password")
    private String password;
	
	@Column(name = "email")
    private String email;
	
	@Column(name = "address")
    private String address;
	
	@Column(name = "telephone_number")
    private String telephoneNumber;
	
	@Column(name = "salary")
    private String salary;
	
	@Column(name = "reference_code")
    private String referenceCode;
	
	@Column(name = "member_type")
    private String memberType;

}
