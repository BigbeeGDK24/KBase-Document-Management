package com.kbase.demo.service;

import com.kbase.demo.entity.User;

import com.kbase.demo.repository.UserRepository;
import com.kbase.demo.repository.KnowledgeArticleRepository;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {

    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    private final KnowledgeArticleRepository articleRepository;

    public UserService(
            UserRepository repository,
            PasswordEncoder passwordEncoder,
            KnowledgeArticleRepository articleRepository
    ) {

        this.repository = repository;

        this.passwordEncoder = passwordEncoder;

        this.articleRepository = articleRepository;

    }

    // Lấy tất cả user
    public List<User> getAllUsers() {

        return repository.findAll();

    }

    // Tạo user mới
    public User saveUser(
            User user
    ) {

        return repository.save(user);

    }

    // Tìm user theo username
    public User findByUsername(
            String username
    ) {

        return repository.findByUsername(username)
                .orElse(null);

    }

    // Tìm user theo id
    public User findById(
            Long id
    ) {

        return repository.findById(id)
                .orElse(null);

    }

    // Xóa user + xóa bài viết của user
    @Transactional
    public void deleteUser(
            Long id
    ) {

        User user
                = repository.findById(id)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "User not found"
                                )
                        );

        // Xóa tất cả bài viết của user trước
        articleRepository.deleteByAuthorId(id);

        // Sau đó xóa user
        repository.delete(user);

    }

    // Login
    public User login(
            String username,
            String password
    ) {

        User user
                = repository.findByUsername(username)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "User not found"
                                )
                        );

        if (!passwordEncoder.matches(
                password,
                user.getPasswordHash()
        )) {

            throw new RuntimeException(
                    "Wrong password"
            );

        }

        return user;

    }

    // Register
    public User register(
            String username,
            String password
    ) {

        if (repository.findByUsername(username).isPresent()) {

            throw new RuntimeException(
                    "Username already exists"
            );

        }

        User user = new User();

        user.setUsername(
                username
        );

        user.setPasswordHash(
                passwordEncoder.encode(
                        password
                )
        );

        user.setRole(
                "USER"
        );

        return repository.save(user);

    }

    // Lấy profile user đang đăng nhập
    public User getProfile(
            String username
    ) {

        return repository.findByUsername(username)
                .orElse(null);

    }

}
