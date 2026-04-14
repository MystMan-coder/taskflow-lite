package com.dhananjaykr.taskflow.taskflowlite.user.mapper;

import com.dhananjaykr.taskflow.taskflowlite.user.dto.RegisterRequestDto;
import com.dhananjaykr.taskflow.taskflowlite.user.dto.UserResponseDto;
import com.dhananjaykr.taskflow.taskflowlite.user.entity.User;

public class UserMapper {

    //Entity -> Response Dto
    public static UserResponseDto mapToDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    //Request Dto -> Entity
    public static User mapToEntity(RegisterRequestDto registerRequestDto) {
        return User.builder()
                .name(registerRequestDto.getName())
                .email(registerRequestDto.getEmail())
                .password(registerRequestDto.getPassword())
                .build();
    }
}
