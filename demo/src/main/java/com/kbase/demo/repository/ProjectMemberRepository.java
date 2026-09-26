package com.kbase.demo.repository;

import com.kbase.demo.entity.Project;
import com.kbase.demo.entity.ProjectMember;
import com.kbase.demo.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectMemberRepository
        extends JpaRepository<ProjectMember, Long> {

    boolean existsByProjectAndUser(
            Project project,
            User user
    );

    List<ProjectMember> findByUserId(Long userId);

}
