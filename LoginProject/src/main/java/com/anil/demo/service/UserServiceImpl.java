package com.anil.demo.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.anil.demo.model.Role;
import com.anil.demo.model.User;
import com.anil.demo.repo.UserRepo;
import com.anil.demo.web.dto.UserRegistrationDto;

@Service
public class UserServiceImpl implements UserService{

	//@Autowired is field base injection but here we use constructor base injection
	private UserRepo userRepo;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
    
	public UserServiceImpl(UserRepo userRepo) {
		super();
		this.userRepo = userRepo; 
    }
    
	@Override // we create here save method which will save data to the database.
	public User save(UserRegistrationDto registrationDto) {
		User user = new User(registrationDto.getFirstName(), registrationDto.getLastName(), registrationDto.getEmail(),
				   passwordEncoder.encode(registrationDto.getPassword()), Arrays.asList(new Role("ROLE_USER")));
		
		       return userRepo.save(user);
	}
    
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByEmail(username);
		if(user== null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesAuthorities(user.getRoles()));
	}

	private Collection<? extends GrantedAuthority> mapRolesAuthorities(Collection<Role> roles) {
		 return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}
}
