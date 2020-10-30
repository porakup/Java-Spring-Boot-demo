package dev.demo.registration.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.demo.registration.dto.ResponseDto;
import dev.demo.registration.dto.UserDto;
import dev.demo.registration.entity.JwtToken;
import dev.demo.registration.entity.User;
import dev.demo.registration.exception.BusinessException;
import dev.demo.registration.repository.JwtTokenRepository;
import dev.demo.registration.repository.UserRepository;
import dev.demo.registration.util.AES;
import dev.demo.registration.util.JwtTokenProvider;

@Service
public class UserServiceImpl implements UserService {
	
	private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private JwtTokenRepository jwtTokenRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AES aes;
	

	@Override
	public ResponseDto register(UserDto req) throws Exception {
		logger.info("register : Start");
		
		Double salary;
		try {
			salary = Double.parseDouble(req.getSalary());
		}catch(NumberFormatException nfe){
			throw new BusinessException("Salary must be numeric.");
		}
		
		if(userRepository.checkDuplicateEmail(aes.encrypt(req.getEmail()))) {
			throw new BusinessException("This email is not available.");
		}
		
		if(userRepository.checkDuplicateTelephoneNumber(aes.encrypt(req.getTelephoneNumber()))) {
			throw new BusinessException("This telephone number is not available.");
		}
		
		User user = new User();
		
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd",new Locale("th", "TH"));
		String date = dateFormat.format(new Date());
		String referenceCode = date.concat(req.getTelephoneNumber().substring(req.getTelephoneNumber().length() - 4 , req.getTelephoneNumber().length()));
		String memberType = null;
		
		if(salary < 15000) {
			throw new BusinessException("Register is reject because salary not match qualify.");
		}else {
			if(salary < 30000) {
				memberType = "Silver";
			}else if(salary <= 50000) {
				memberType = "Gold";
			}else if(salary > 50000){
				memberType = "Platinum";
			}
		}

		String encryptFirstName = aes.encrypt(req.getFirstName());
		String encryptLastName = aes.encrypt(req.getLastName());
		String encryptUsername = aes.encrypt(req.getUsername());
		String encryptPassword = aes.encrypt(req.getPassword());
		String encryptEmail = aes.encrypt(req.getEmail());
		String encryptAddress = aes.encrypt(req.getAddress());
		String encryptTelephoneNumber = aes.encrypt(req.getTelephoneNumber());
		String encryptSalary = aes.encrypt(req.getSalary());
		String encryptReferenceCode = aes.encrypt(referenceCode);
		String encryptMemberType = aes.encrypt(memberType);
		
		user.setFirstName(encryptFirstName);
		user.setLastName(encryptLastName);
		user.setUsername(encryptUsername);
		user.setPassword(encryptPassword);
		user.setEmail(encryptEmail);
		user.setAddress(encryptAddress);
		user.setTelephoneNumber(encryptTelephoneNumber);
		user.setSalary(encryptSalary);
		user.setReferenceCode(encryptReferenceCode);
		user.setMemberType(encryptMemberType);

		user = userRepository.save(user);
		
		if(Objects.nonNull(user)) {
			req.setId(user.getId());
			req.setPassword(null);
			req.setReferenceCode(referenceCode);
			req.setMemberType(memberType);
		}else {
			throw new Exception("Can not register.");
		}
			
		JwtToken jwtToken = jwtTokenProvider.generateToken(req.getUsername(),encryptUsername);
		jwtTokenRepository.save(jwtToken);
		ResponseDto response = new ResponseDto();
		response.setData(req);		
		response.setAccessToken(jwtToken.getAccessToken());
		logger.info("register : End");
		return response;
	}

	@Override
	public UserDto getUser(Long userId, String accessToken) throws Exception {
		logger.info("getUser : Start");
		
		jwtTokenProvider.validateToken(accessToken);
		
		Optional<User> user = Optional.ofNullable(new User());
		if(userRepository.findById(userId).isPresent()) {
			user = userRepository.findById(userId);
		}else {
			throw new BusinessException("User not found.");
		}
		UserDto userDto = new UserDto();
		
		if(!user.get().getFirstName().isEmpty()) {
			String decryptFirstName = aes.decrypt(user.get().getFirstName()) ;
			userDto.setFirstName(decryptFirstName);
		}
		if(!user.get().getLastName().isEmpty()) {
			String decryptLastName = aes.decrypt(user.get().getLastName());
			userDto.setLastName(decryptLastName);
		}
		if(!user.get().getUsername().isEmpty()) {
			String decryptUsername = aes.decrypt(user.get().getUsername());
			userDto.setUsername(decryptUsername);
		}
		if(!user.get().getEmail().isEmpty()) {
			String decryptEmail = aes.decrypt(user.get().getEmail());
			userDto.setEmail(decryptEmail);
		}
		if(!user.get().getAddress().isEmpty()) {
			String decryptAddress = aes.decrypt(user.get().getAddress());
			userDto.setAddress(decryptAddress);
		}
		if(!user.get().getTelephoneNumber().isEmpty()) {
			String decryptTelephoneNumber = aes.decrypt(user.get().getTelephoneNumber());
			userDto.setTelephoneNumber(decryptTelephoneNumber);
		}
		if(!user.get().getSalary().isEmpty()) {
			String decryptSalary = aes.decrypt(user.get().getSalary());
			userDto.setSalary(decryptSalary);
		}
		if(!user.get().getReferenceCode().isEmpty()) {
			String decryptReferenceCode = aes.decrypt(user.get().getReferenceCode());
			userDto.setReferenceCode(decryptReferenceCode);
		}
		if(!user.get().getMemberType().isEmpty()) {
			String decryptMemberType= aes.decrypt(user.get().getMemberType());
			userDto.setMemberType(decryptMemberType);
		}
		logger.info("getUser : End");
		return userDto;
	}

}
