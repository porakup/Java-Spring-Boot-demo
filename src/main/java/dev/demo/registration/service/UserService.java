package dev.demo.registration.service;

import dev.demo.registration.dto.ResponseDto;
import dev.demo.registration.dto.UserDto;

public interface UserService {
	
	ResponseDto register(UserDto req) throws Exception;;
	
	UserDto getUser(Long userId, String accessToken) throws Exception;

}
