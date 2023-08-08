package com.user.service;

import java.util.List;
import java.util.Optional;

import com.user.entity.User;

public interface UserService {
	
	User createUser(User user);
	
	List<User> getAllUser();
	
	User getUser(int userId);
	
//	User getUserByUsername(String username,String password);
	
	String generateToken(String username);	
	
	void validateToken(String token);

	User getUserByUsername(String username);

	

}
