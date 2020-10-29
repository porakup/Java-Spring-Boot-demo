package dev.demo.registration.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dev.demo.registration.dto.ResponseDto;
import dev.demo.registration.dto.UserDto;
import dev.demo.registration.exception.BusinessException;
import dev.demo.registration.exception.TokenException;
import dev.demo.registration.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/demo")
@RequiredArgsConstructor
public class UserController {
	
	private static final Logger logger = LogManager.getLogger(UserController.class);
	
	private final UserService userService;

	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody UserDto req) throws BusinessException {
		ResponseDto response = new ResponseDto();
		
		try {		
			response = userService.register(req);
		} catch(BusinessException e) {
			logger.error(String.format("Error: %s", e.getMessage()));
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
		} catch(Exception e) {
			logger.error(String.format("Error: %s", e.getMessage()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		} 
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<?> getUser(@PathVariable("userId") Long userId, @RequestHeader("Authorization") String accessToken) {
		UserDto response = new UserDto();
		
		try {		
			response = userService.getUser(userId,accessToken);
		} catch(TokenException e) {
			logger.error(String.format("Error: %s", e.getMessage()));
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		} catch(BusinessException e) {
			logger.error(String.format("Error: %s", e.getMessage()));
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
		} catch(Exception e) {
			logger.error(String.format("Error: %s", e.getMessage()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(
	  MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();
	    ex.getBindingResult().getAllErrors().forEach((error) -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        errors.put(fieldName, errorMessage);
	    });
	    return errors;
	}
	
}
