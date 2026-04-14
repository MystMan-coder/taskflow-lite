package com.dhananjaykr.taskflow.taskflowlite.user.service;

import com.dhananjaykr.taskflow.taskflowlite.user.dto.LoginRequestDto;
import com.dhananjaykr.taskflow.taskflowlite.user.dto.RegisterRequestDto;
import com.dhananjaykr.taskflow.taskflowlite.user.dto.UserResponseDto;

public interface UserService {

    UserResponseDto registerUser(RegisterRequestDto request);

    UserResponseDto loginUser(LoginRequestDto request);

    UserResponseDto getUserProfile(Long userId);

    UserResponseDto updateUser(Long userId, RegisterRequestDto request);
}
