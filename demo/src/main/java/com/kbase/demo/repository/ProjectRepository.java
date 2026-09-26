package com.kbase.demo.repository;

import com.kbase.demo.entity.Project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository
        extends JpaRepository<Project, Long> {

    List<Project> findByOwnerId(Long ownerId);

}
