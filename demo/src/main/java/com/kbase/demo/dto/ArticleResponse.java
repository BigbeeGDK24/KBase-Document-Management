package com.kbase.demo.dto;

import java.time.LocalDateTime;

public class ArticleResponse {

    private Long id;

    private String title;

    private String content;

    private String author;

    private String category;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public ArticleResponse(
            Long id,
            String title,
            String content,
            String author,
            String category,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {

        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.category = category;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

    }

    public Long getId() {

        return id;

    }

    public String getTitle() {

        return title;

    }

    public String getContent() {

        return content;

    }

    public String getAuthor() {

        return author;

    }

    public String getCategory() {

        return category;

    }

    public LocalDateTime getCreatedAt() {

        return createdAt;

    }

    public LocalDateTime getUpdatedAt() {

        return updatedAt;

    }

}
