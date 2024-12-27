package com.example.metorologyapp.Services;


import com.example.metorologyapp.DTO.UserRequest;
import com.example.metorologyapp.DTO.UserResponse;
import com.example.metorologyapp.Entities.User;
import com.example.metorologyapp.Repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse createUser(UserRequest userRequestDto) {
        User user = new User();
        user.setFirstName(userRequestDto.getFirstName());
        user.setLastName(userRequestDto.getLastName());
        user.setRole(userRequestDto.getRole());
        User savedUser = userRepository.save(user);
        return mapToResponseDto(savedUser);
    }

    public Optional<UserResponse> getUserById(Long id) {
        return userRepository.findById(id).map(this::mapToResponseDto);
    }

    public UserResponse updateUser(Long id, UserRequest userRequestDto) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setFirstName(userRequestDto.getFirstName());
                    user.setLastName(userRequestDto.getLastName());
                    user.setRole(userRequestDto.getRole());
                    User updatedUser = userRepository.save(user);
                    return mapToResponseDto(updatedUser);
                })
                .orElseThrow(() -> new RuntimeException("User with ID " + id + " not found."));
    }

    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("User with ID " + id + " not found.");
        }
    }

    private UserResponse mapToResponseDto(User user) {
        UserResponse responseDto = new UserResponse();
        responseDto.setId(user.getId());
        responseDto.setFirstName(user.getFirstName());
        responseDto.setLastName(user.getLastName());
        responseDto.setRole(user.getRole());
        return responseDto;
    }
}
