package com.kbase.demo.service;

import com.kbase.demo.dto.ArticleRequest;
import com.kbase.demo.entity.KnowledgeArticle;
import com.kbase.demo.entity.User;

import com.kbase.demo.repository.KnowledgeArticleRepository;
import com.kbase.demo.repository.UserRepository;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public class KnowledgeArticleService {

    private final KnowledgeArticleRepository repository;

    private final UserRepository userRepository;

    public KnowledgeArticleService(
            KnowledgeArticleRepository repository,
            UserRepository userRepository
    ) {

        this.repository = repository;
        this.userRepository = userRepository;

    }

    // Lấy tất cả bài viết
    public List<KnowledgeArticle> getAllArticles() {

        return repository.findAll();

    }

    // Lưu bài viết mới + gán author + category
    public KnowledgeArticle saveArticle(
            ArticleRequest request,
            String username
    ) {

        User user
                = userRepository
                        .findByUsername(username)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "User not found"
                                )
                        );

        KnowledgeArticle article
                = new KnowledgeArticle();

        article.setTitle(
                request.getTitle()
        );

        article.setContent(
                request.getContent()
        );

        // thêm category
        article.setCategory(
                request.getCategory()
        );

        article.setAuthor(user);

        return repository.save(article);

    }

    // Tìm bài viết theo id
    public KnowledgeArticle getArticleById(
            Long id
    ) {

        return repository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Article not found"
                        )
                );

    }

    // Xóa bài viết có kiểm tra quyền
    public void deleteArticle(
            Long id,
            String username,
            String role
    ) {

        KnowledgeArticle article
                = repository.findById(id)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Article not found"
                                )
                        );

        boolean isAdmin
                = role.equals("ADMIN");

        boolean isOwner
                = article.getAuthor()
                        .getUsername()
                        .equals(username);

        if (!isAdmin && !isOwner) {

            throw new AccessDeniedException(
                    "You cannot delete this article"
            );

        }

        repository.delete(article);

    }

    // Update bài viết có kiểm tra quyền
    public KnowledgeArticle updateArticle(
            Long id,
            ArticleRequest request,
            String username,
            String role
    ) {

        KnowledgeArticle oldArticle
                = repository.findById(id)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Article not found"
                                )
                        );

        boolean isAdmin
                = role.equals("ADMIN");

        boolean isOwner
                = oldArticle.getAuthor()
                        .getUsername()
                        .equals(username);

        if (!isAdmin && !isOwner) {

            throw new AccessDeniedException(
                    "You cannot update this article"
            );

        }

        oldArticle.setTitle(
                request.getTitle()
        );

        oldArticle.setContent(
                request.getContent()
        );

        // thêm update category
        oldArticle.setCategory(
                request.getCategory()
        );

        return repository.save(oldArticle);

    }

    // Search bài viết
    public List<KnowledgeArticle> searchArticles(
            String keyword
    ) {

        return repository
                .findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(
                        keyword,
                        keyword
                );

    }

// Lọc theo category
    public List<KnowledgeArticle> getArticlesByCategory(
            String category
    ) {

        return repository
                .findByCategoryIgnoreCase(
                        category
                );

    }

    // Pagination bài viết
    public Page<KnowledgeArticle> getArticlesPaging(
            Pageable pageable
    ) {

        return repository.findAll(pageable);

    }
}
