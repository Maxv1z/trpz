// UserController.java
package com.example.metorologyapp.Controllers;

import com.example.metorologyapp.DTO.UserRequest;
import com.example.metorologyapp.DTO.UserResponse;
import com.example.metorologyapp.Services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Create User
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequestDto) {
        UserResponse createdUser = userService.createUser(userRequestDto);
        return ResponseEntity.ok(createdUser);
    }

    // Get User by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        Optional<UserResponse> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update User
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody UserRequest userRequestDto) {
        try {
            UserResponse updatedUser = userService.updateUser(id, userRequestDto);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete User
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
