package com.anil.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.anil.demo.model.User;
import com.anil.demo.web.dto.UserRegistrationDto;

public interface UserService extends UserDetailsService {

	//this method will save user register data
	User save(UserRegistrationDto registrationDto);
}
