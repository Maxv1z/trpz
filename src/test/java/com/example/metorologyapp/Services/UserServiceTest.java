package com.example.metorologyapp.Services;

import com.example.metorologyapp.DTO.UserRequest;
import com.example.metorologyapp.DTO.UserResponse;
import com.example.metorologyapp.Entities.User;
import com.example.metorologyapp.Repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    void testCreateUser() {
        UserRequest requestDto = new UserRequest();
        requestDto.setFirstName("John");
        requestDto.setLastName("Doe");
        requestDto.setRole("Admin");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setFirstName(requestDto.getFirstName());
        savedUser.setLastName(requestDto.getLastName());
        savedUser.setRole(requestDto.getRole());

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserResponse response = userService.createUser(requestDto);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("John", response.getFirstName());
        assertEquals("Doe", response.getLastName());
        assertEquals("Admin", response.getRole());

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testGetUserById() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setRole("User");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<UserResponse> response = userService.getUserById(1L);

        assertTrue(response.isPresent());
        assertEquals(1L, response.get().getId());
        assertEquals("Jane", response.get().getFirstName());
        assertEquals("Doe", response.get().getLastName());
        assertEquals("User", response.get().getRole());

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateUser() {
        UserRequest requestDto = new UserRequest();
        requestDto.setFirstName("Updated John");
        requestDto.setLastName("Updated Doe");
        requestDto.setRole("SuperAdmin");

        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setFirstName("John");
        existingUser.setLastName("Doe");
        existingUser.setRole("Admin");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UserResponse response = userService.updateUser(1L, requestDto);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Updated John", response.getFirstName());
        assertEquals("Updated Doe", response.getLastName());
        assertEquals("SuperAdmin", response.getRole());

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    void testDeleteUser() {
        when(userRepository.existsById(1L)).thenReturn(true);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).existsById(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteUserNotFound() {
        when(userRepository.existsById(1L)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.deleteUser(1L));

        assertEquals("User with ID 1 not found.", exception.getMessage());
        verify(userRepository, times(1)).existsById(1L);
        verify(userRepository, never()).deleteById(anyLong());
    }
}
