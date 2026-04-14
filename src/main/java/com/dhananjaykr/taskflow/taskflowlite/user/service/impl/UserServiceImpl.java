package com.dhananjaykr.taskflow.taskflowlite.user.service.impl;

import com.dhananjaykr.taskflow.taskflowlite.shared.exception.custom.ConflictException;
import com.dhananjaykr.taskflow.taskflowlite.shared.exception.custom.ResourceNotFoundException;
import com.dhananjaykr.taskflow.taskflowlite.shared.exception.custom.UnauthorizedException;
import com.dhananjaykr.taskflow.taskflowlite.user.dto.LoginRequestDto;
import com.dhananjaykr.taskflow.taskflowlite.user.dto.RegisterRequestDto;
import com.dhananjaykr.taskflow.taskflowlite.user.dto.UserResponseDto;
import com.dhananjaykr.taskflow.taskflowlite.user.entity.User;
import com.dhananjaykr.taskflow.taskflowlite.user.mapper.UserMapper;
import com.dhananjaykr.taskflow.taskflowlite.user.repository.UserRepository;
import com.dhananjaykr.taskflow.taskflowlite.user.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public UserResponseDto registerUser(RegisterRequestDto req){
        log.info("Registering user with email: {}", req.getEmail());
        // 1. Check if email already exists
        if(userRepository.existsByEmail(req.getEmail())){
            log.info("Registration failed - email already exists: {}", req.getEmail());
            throw new ConflictException("Email already exists");
        }
        // 2. Map DTO → Entity
        User user = UserMapper.mapToEntity(req);

        // 3. Encode password (business logic here, NOT in mapper)
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // 4. Save user
        User savedUser = userRepository.save(user);

        log.info("User registered successfully: {}", savedUser.getEmail());
        // 5. Return DTO
        return UserMapper.mapToDto(savedUser);
    }

    @Override
    public UserResponseDto loginUser(LoginRequestDto loginRequestDto) {

        log.info("Login attempt for email: {}", loginRequestDto.getEmail());

        // 1. Find user by email
        User user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> {
                    log.warn("Login failed - user not found: {}", loginRequestDto.getEmail());
                    return new UnauthorizedException("Invalid email or password");
                });

        // 2. Check password
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            log.warn("Login failed - incorrect password for email: {}", loginRequestDto.getEmail());
            throw new UnauthorizedException("Invalid email or password");
        }

        log.info("Login successful for email: {}", loginRequestDto.getEmail());
        // 3. Return response DTO
        return UserMapper.mapToDto(user);
    }

    @Override
    public UserResponseDto getUserProfile(Long userId) {
        log.info("Fetching user profile for ID: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return UserMapper.mapToDto(user);
    }

    @Override
    public UserResponseDto updateUser(Long userId,RegisterRequestDto registerRequestDto) {
        log.info("Updating user with ID: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setName(registerRequestDto.getName());

        //prevent duplicate email
        if (!user.getEmail().equals(registerRequestDto.getEmail())) {

            if (userRepository.existsByEmail(registerRequestDto.getEmail())) {
                throw new ConflictException("Email already exists");
            }

            user.setEmail(registerRequestDto.getEmail());
        }

        if (registerRequestDto.getPassword() != null && !registerRequestDto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
        }

        log.info("User updated successfully: {}", userId);
        return UserMapper.mapToDto(userRepository.save(user));
    }

}
