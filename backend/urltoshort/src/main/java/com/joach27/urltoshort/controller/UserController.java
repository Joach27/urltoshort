package com.joach27.urltoshort.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joach27.urltoshort.dto.CreateUserRequest;
import com.joach27.urltoshort.dto.UserResponse;
import com.joach27.urltoshort.service.UserService;

@RestController 
@RequestMapping("/api/v1")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService){
		this.userService = userService;
	}

	// Create user 
	@PostMapping("/users")
	public ResponseEntity<UserResponse> createUser(@RequestBody CreateUserRequest request){
        UserResponse response = userService.createUser(request);
        
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
	}
}