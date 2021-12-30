package com.anil.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anil.demo.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

	User findByEmail(String email);
}
