package com.kbase.demo.controller;

import com.kbase.demo.dto.LoginRequest;
import com.kbase.demo.dto.LoginResponse;
import com.kbase.demo.entity.User;
import com.kbase.demo.security.JwtUtil;
import com.kbase.demo.service.UserService;
import com.kbase.demo.dto.RegisterRequest;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(
            UserService userService,
            JwtUtil jwtUtil) {

        this.userService = userService;
        this.jwtUtil = jwtUtil;

    }

    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody LoginRequest request) {

        User user = userService.login(
                request.getUsername(),
                request.getPassword()
        );

        String token = jwtUtil.generateToken(
                user.getUsername(),
                user.getRole()
        );

        return new LoginResponse(token);

    }

    @PostMapping("/register")
    public String register(
            @RequestBody RegisterRequest request
    ) {

        userService.register(
                request.getUsername(),
                request.getPassword()
        );

        return "Register success";

    }

}
