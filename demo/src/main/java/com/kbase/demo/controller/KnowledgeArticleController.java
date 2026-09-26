package com.kbase.demo.controller;

import jakarta.validation.Valid;

import com.kbase.demo.dto.ArticleMapper;
import com.kbase.demo.dto.ArticleRequest;
import com.kbase.demo.dto.ArticleResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.kbase.demo.entity.KnowledgeArticle;

import com.kbase.demo.service.KnowledgeArticleService;

import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/articles")
public class KnowledgeArticleController {

    private final KnowledgeArticleService service;

    public KnowledgeArticleController(
            KnowledgeArticleService service
    ) {

        this.service = service;

    }

    // Lấy tất cả bài viết
    @GetMapping
    public List<ArticleResponse> getAllArticles() {

        return service.getAllArticles()
                .stream()
                .map(ArticleMapper::toResponse)
                .toList();

    }

    // Pagination + Sort bài viết
    @GetMapping("/page")
    public Page<ArticleResponse> getArticlesPaging(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {

        PageRequest pageable
                = PageRequest.of(
                        page,
                        size,
                        Sort.by(
                                "id"
                        )
                                .descending()
                );

        return service
                .getArticlesPaging(pageable)
                .map(
                        ArticleMapper::toResponse
                );

    }

    // Thêm bài viết mới
    @PostMapping
    public ArticleResponse create(
            @Valid @RequestBody ArticleRequest request,
            Authentication authentication
    ) {

        if (authentication == null) {

            throw new RuntimeException(
                    "Chưa đăng nhập"
            );

        }

        String username
                = authentication.getName();

        KnowledgeArticle article
                = service.saveArticle(
                        request,
                        username
                );

        return ArticleMapper.toResponse(article);

    }

    // Tìm kiếm bài viết
    @GetMapping("/search")
    public List<ArticleResponse> searchArticles(
            @RequestParam String keyword
    ) {

        return service.searchArticles(keyword)
                .stream()
                .map(ArticleMapper::toResponse)
                .toList();

    }

    // Lọc bài viết theo category
    @GetMapping("/category")
    public List<ArticleResponse> getByCategory(
            @RequestParam String name
    ) {

        return service.getArticlesByCategory(name)
                .stream()
                .map(ArticleMapper::toResponse)
                .toList();

    }

    // Lấy bài viết theo id
    @GetMapping("/{id}")
    public ArticleResponse getArticleById(
            @PathVariable Long id
    ) {

        KnowledgeArticle article
                = service.getArticleById(id);

        return ArticleMapper.toResponse(article);

    }

    // Xóa bài viết
    @DeleteMapping("/{id}")
    public String deleteArticle(
            @PathVariable Long id,
            Authentication authentication
    ) {

        if (authentication == null) {

            throw new RuntimeException(
                    "Chưa đăng nhập"
            );

        }

        String username
                = authentication.getName();

        String role
                = authentication
                        .getAuthorities()
                        .iterator()
                        .next()
                        .getAuthority()
                        .replace(
                                "ROLE_",
                                ""
                        );

        service.deleteArticle(
                id,
                username,
                role
        );

        return "Deleted successfully";

    }

    // Update bài viết
    @PutMapping("/{id}")
    public ArticleResponse updateArticle(
            @PathVariable Long id,
            @Valid @RequestBody ArticleRequest request,
            Authentication authentication
    ) {

        if (authentication == null) {

            throw new RuntimeException(
                    "Chưa đăng nhập"
            );

        }

        String username
                = authentication.getName();

        String role
                = authentication
                        .getAuthorities()
                        .iterator()
                        .next()
                        .getAuthority()
                        .replace(
                                "ROLE_",
                                ""
                        );

        KnowledgeArticle article
                = service.updateArticle(
                        id,
                        request,
                        username,
                        role
                );

        return ArticleMapper.toResponse(article);

    }

}
