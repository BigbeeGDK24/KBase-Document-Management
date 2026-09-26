package com.kbase.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "documents")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // tên file gốc
    private String fileName;

    // loại file
    private String fileType;

    // đường dẫn lưu file
    private String filePath;

    // dung lượng file
    private Long fileSize;

    private LocalDateTime uploadedAt
            = LocalDateTime.now();

    // File thuộc project nào
    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonIgnore
    private Project project;

    // Người upload
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User uploadedBy;

    public Long getId() {

        return id;

    }

    public void setId(Long id) {

        this.id = id;

    }

    public String getFileName() {

        return fileName;

    }

    public void setFileName(String fileName) {

        this.fileName = fileName;

    }

    public String getFileType() {

        return fileType;

    }

    public void setFileType(String fileType) {

        this.fileType = fileType;

    }

    public String getFilePath() {

        return filePath;

    }

    public void setFilePath(String filePath) {

        this.filePath = filePath;

    }

    public Long getFileSize() {

        return fileSize;

    }

    public void setFileSize(Long fileSize) {

        this.fileSize = fileSize;

    }

    public LocalDateTime getUploadedAt() {

        return uploadedAt;

    }

    public void setUploadedAt(LocalDateTime uploadedAt) {

        this.uploadedAt = uploadedAt;

    }

    public Project getProject() {

        return project;

    }

    public void setProject(Project project) {

        this.project = project;

    }

    public User getUploadedBy() {

        return uploadedBy;

    }

    public void setUploadedBy(User uploadedBy) {

        this.uploadedBy = uploadedBy;

    }

}
