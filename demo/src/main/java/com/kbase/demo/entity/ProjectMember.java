package com.kbase.demo.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;


import jakarta.persistence.*;



@Entity
@Table(name="project_members")
public class ProjectMember {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @ManyToOne
    @JoinColumn(name="project_id")
    @JsonIgnore
    private Project project;



    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;



    private String role;




    public Long getId(){
        return id;
    }


    public void setId(Long id){
        this.id=id;
    }



    public Project getProject(){
        return project;
    }


    public void setProject(Project project){
        this.project=project;
    }



    public User getUser(){
        return user;
    }


    public void setUser(User user){
        this.user=user;
    }



    public String getRole(){
        return role;
    }


    public void setRole(String role){
        this.role=role;
    }

}