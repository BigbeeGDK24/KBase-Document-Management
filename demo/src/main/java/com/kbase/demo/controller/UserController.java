package com.kbase.demo.controller;

import com.kbase.demo.dto.UserMapper;
import com.kbase.demo.dto.UserResponse;
import com.kbase.demo.entity.User;
import com.kbase.demo.service.UserService;

import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(
            UserService service
    ) {

        this.service = service;

    }

    // Lấy thông tin user đang đăng nhập
    @GetMapping("/me")
    public UserResponse profile(
            Authentication authentication
    ) {

        String username
                = authentication.getName();

        System.out.println(
                "PROFILE USER = "
                + username
        );

        User user
                = service.findByUsername(username);

        return UserMapper.toResponse(user);

    }

    // Lấy danh sách tất cả user - chỉ ADMIN
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<UserResponse> getAllUsers() {

        return service.getAllUsers()
                .stream()
                .map(UserMapper::toResponse)
                .toList();

    }

    // Xóa user - chỉ ADMIN
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public String deleteUser(
            @PathVariable Long id
    ) {

        User user
                = service.findById(id);

        if (user == null) {

            throw new RuntimeException(
                    "User not found"
            );

        }

        service.deleteUser(id);

        return "User deleted successfully";

    }

}
