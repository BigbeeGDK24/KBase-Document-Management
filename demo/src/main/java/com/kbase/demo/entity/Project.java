package com.kbase.demo.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



@Entity
@Table(name="projects")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Project {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    private String name;



    private String description;





    @ManyToOne
    @JoinColumn(name="owner_id")
    private User owner;





    @OneToMany(
            mappedBy="project",
            cascade=CascadeType.ALL,
            orphanRemoval=true
    )
    private List<ProjectMember> members =
            new ArrayList<>();





    @OneToMany(
            mappedBy="project",
            cascade=CascadeType.ALL
    )
    private List<KnowledgeArticle> articles =
            new ArrayList<>();





    // DOCUMENT STORAGE

    @OneToMany(
            mappedBy="project",
            cascade=CascadeType.ALL,
            orphanRemoval=true
    )
    private List<Document> documents =
            new ArrayList<>();





    private LocalDateTime createdAt =
            LocalDateTime.now();






    public Long getId(){

        return id;

    }


    public void setId(Long id){

        this.id=id;

    }






    public String getName(){

        return name;

    }


    public void setName(String name){

        this.name=name;

    }






    public String getDescription(){

        return description;

    }


    public void setDescription(String description){

        this.description=description;

    }






    public User getOwner(){

        return owner;

    }


    public void setOwner(User owner){

        this.owner=owner;

    }






    public List<ProjectMember> getMembers(){

        return members;

    }


    public void setMembers(List<ProjectMember> members){

        this.members=members;

    }






    public List<KnowledgeArticle> getArticles(){

        return articles;

    }


    public void setArticles(List<KnowledgeArticle> articles){

        this.articles=articles;

    }






    public List<Document> getDocuments(){

        return documents;

    }


    public void setDocuments(List<Document> documents){

        this.documents=documents;

    }






    public LocalDateTime getCreatedAt(){

        return createdAt;

    }


    public void setCreatedAt(LocalDateTime createdAt){

        this.createdAt=createdAt;

    }



}