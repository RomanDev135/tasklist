package com.exampl.tasklist.web.controller;

import com.exampl.tasklist.domain.user.User;
import com.exampl.tasklist.service.AuthService;
import com.exampl.tasklist.service.UserService;
import com.exampl.tasklist.web.dto.auth.JwtRequest;
import com.exampl.tasklist.web.dto.auth.JwtResponse;
import com.exampl.tasklist.web.dto.user.UserDto;
import com.exampl.tasklist.web.dto.validation.OnCreate;
import com.exampl.tasklist.web.mappers.UserMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor //Выполнить Autowired
@Validated
@Tag(name = "Auth controller", description = "Auth API")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/login")
    public JwtResponse login(@Validated @RequestBody JwtRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/register")
    public UserDto register(@Validated(OnCreate.class) @RequestBody UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        User userCreated = userService.create(user);
        return userMapper.toDto(userCreated);
    }

    @PostMapping("/refresh")
    public JwtResponse refresh(@RequestBody String refreshToken) {
        return authService.refresh(refreshToken);
    }


}
