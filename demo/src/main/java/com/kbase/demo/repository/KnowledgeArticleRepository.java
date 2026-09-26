package com.kbase.demo.repository;

import com.kbase.demo.entity.KnowledgeArticle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KnowledgeArticleRepository
        extends JpaRepository<KnowledgeArticle, Long> {

    // Tìm theo title hoặc content
    List<KnowledgeArticle>
            findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(
                    String title,
                    String content
            );

    // Lọc theo category
    List<KnowledgeArticle>
            findByCategoryIgnoreCase(
                    String category
            );

    // Xóa tất cả bài viết của user
    void deleteByAuthorId(
            Long authorId
    );

}
