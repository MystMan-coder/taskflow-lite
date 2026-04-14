package com.dhananjaykr.taskflow.taskflowlite.user.controller;

import com.dhananjaykr.taskflow.taskflowlite.user.dto.LoginRequestDto;
import com.dhananjaykr.taskflow.taskflowlite.user.dto.RegisterRequestDto;
import com.dhananjaykr.taskflow.taskflowlite.user.dto.UserResponseDto;
import com.dhananjaykr.taskflow.taskflowlite.user.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registerUser(@RequestBody @Valid RegisterRequestDto registerRequestDto) {
        return new ResponseEntity<>(userService.registerUser(registerRequestDto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(userService.loginUser(loginRequestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable long id) {
        return ResponseEntity.ok(userService.getUserProfile(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable long id, @RequestBody @Valid RegisterRequestDto registerRequestDto) {
        return ResponseEntity.ok(userService.updateUser(id, registerRequestDto));
    }
}
