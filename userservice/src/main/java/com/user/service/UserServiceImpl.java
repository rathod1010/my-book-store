package com.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.user.entity.User;
import com.user.exception.ResourceNotFoundException;
import com.user.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public User createUser(User user) {
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUser() {

		return userRepository.findAll();
	}

	@Override
	public User getUser(int userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id" + userId));
	}

	@Override
	public String generateToken(String username) {
	
		return jwtService.generateToken(username);
	}

	@Override
	public void validateToken(String token) {
		
		jwtService.validateToken(token);
		
		
	}
	
	public User getUserByUsername(String username) {
	    Optional<User> userOptional = userRepository.findByUsername(username);

	    // Check if the user exists in the database
	    if (userOptional.isPresent()) {
	        return userOptional.get();
	    } else {
	        // Handle the case when the user is not found
	        throw new RuntimeException("User not found for the given username: " + username);
	    }
	}


}