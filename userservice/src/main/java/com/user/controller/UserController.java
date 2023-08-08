package com.user.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.user.dto.AuthRequest;
import com.user.dto.AuthResponse;
import com.user.entity.User;
import com.user.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/save")
	public ResponseEntity<User> createUser(@RequestBody User user) {

		User createdUser = userService.createUser(user);

		return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
	}
	
//	 @PostMapping("/save")
//	    public User addNewUser(@RequestBody User user) {
//	        return userService.createUser(user);
//	    }

	@PostMapping("/token")
	public ResponseEntity<AuthResponse> getToken(@RequestBody AuthRequest authRequest) {
		Authentication authenticate = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		if (authenticate.isAuthenticated()) {
			String token= userService.generateToken(authRequest.getUsername());
			User user = userService.getUserByUsername(authRequest.getUsername());
			AuthResponse authResponse = new AuthResponse(user.getUserId(),user.getUsername(),user.getEmail(),token);
			return ResponseEntity.ok(authResponse);
			 
		} else {
			throw new RuntimeException("invalid access");
		}
	}

	@GetMapping("/validate")
	public String validateToken(@RequestParam("token") String token) {
		userService.validateToken(token);
		return "Token is valid";
	}

	@GetMapping("/get/{userId}")
	public ResponseEntity<User> getSingleUser(@PathVariable int userId) {
		User user = userService.getUser(userId);
		return ResponseEntity.ok(user);
	}

	@GetMapping("get/all")
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> allUsers = userService.getAllUser();
		return ResponseEntity.ok(allUsers);
	}

}
