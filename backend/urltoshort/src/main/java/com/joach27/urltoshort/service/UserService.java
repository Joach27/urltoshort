package com.joach27.urltoshort.service;

import org.springframework.stereotype.Service;

import com.joach27.urltoshort.dto.CreateUserRequest;
import com.joach27.urltoshort.dto.UserResponse;
import com.joach27.urltoshort.entity.User;
import com.joach27.urltoshort.repository.UserRepository;

@Service 
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public UserResponse createUser(CreateUserRequest request){

        User user = new User();

        user.setEmail(request.email());
        user.setFirstname(request.firstname());
        user.setLastname(request.lastname());
        user.setUsername(request.username());

        // Pseudo Hash
        String pseudoHash = "HASHED_" + request.password();
        user.setPasswordStringHash(pseudoHash);

        userRepository.save(user);

        return new UserResponse(
            user.getFirstname(), 
            user.getLastname(), 
            user.getUsername(),
            user.getEmail()
        );

        
    }
}