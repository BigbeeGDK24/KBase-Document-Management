package com.kbase.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    @JsonIgnore
    private String passwordHash;

    private String role;

    // Project user làm OWNER
    @OneToMany(
            mappedBy = "owner"
    )
    @JsonIgnore
    private List<Project> ownedProjects;

    // Project user tham gia
    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL
    )
    @JsonIgnore
    private List<ProjectMember> projectMembers;

    @OneToMany(mappedBy = "author")
    @JsonIgnore
    private List<KnowledgeArticle> articles;

    public User() {

    }

    public User(
            String username,
            String passwordHash,
            String role
    ) {

        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Project> getOwnedProjects() {
        return ownedProjects;
    }

    public void setOwnedProjects(List<Project> ownedProjects) {
        this.ownedProjects = ownedProjects;
    }

    public List<ProjectMember> getProjectMembers() {
        return projectMembers;
    }

    public void setProjectMembers(List<ProjectMember> projectMembers) {
        this.projectMembers = projectMembers;
    }

    @OneToMany(
            mappedBy = "uploadedBy"
    )
    @JsonIgnore
    private List<Document> documents;

    public List<Document> getDocuments() {

        return documents;

    }

    public void setDocuments(List<Document> documents) {

        this.documents = documents;

    }

}
