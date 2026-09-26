package com.kbase.demo.dto;

import com.kbase.demo.entity.KnowledgeArticle;

public class ArticleMapper {

    public static ArticleResponse toResponse(
            KnowledgeArticle article
    ) {

        return new ArticleResponse(
                article.getId(),
                article.getTitle(),
                article.getContent(),
                article.getAuthor()
                        .getUsername(),
                article.getCategory(),
                article.getCreatedAt(),
                article.getUpdatedAt()
        );

    }

}
