package com.kbase.demo.service;

import com.kbase.demo.dto.ProjectMemberRequest;

import com.kbase.demo.entity.Project;
import com.kbase.demo.entity.ProjectMember;
import com.kbase.demo.entity.User;
import java.util.ArrayList;
import com.kbase.demo.repository.ProjectRepository;
import com.kbase.demo.repository.ProjectMemberRepository;
import com.kbase.demo.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    private final ProjectMemberRepository memberRepository;

    private final UserRepository userRepository;

    public ProjectService(
            ProjectRepository projectRepository,
            ProjectMemberRepository memberRepository,
            UserRepository userRepository
    ) {

        this.projectRepository = projectRepository;

        this.memberRepository = memberRepository;

        this.userRepository = userRepository;

    }

    // CREATE PROJECT + OWNER
    public Project create(
            Project project,
            String username
    ) {

        User user
                = userRepository.findByUsername(username)
                        .orElseThrow(
                                () -> new RuntimeException("User not found")
                        );

        project.setOwner(user);

        Project savedProject
                = projectRepository.save(project);

        ProjectMember member
                = new ProjectMember();

        member.setProject(savedProject);

        member.setUser(user);

        member.setRole("OWNER");

        ProjectMember savedMember
                = memberRepository.save(member);

        savedProject.getMembers()
                .add(savedMember);

        return savedProject;

    }

    // INVITE MEMBER
    public ProjectMember addMember(
            Long projectId,
            ProjectMemberRequest request,
            String ownerUsername
    ) {

        Project project
                = projectRepository.findById(projectId)
                        .orElseThrow(
                                () -> new RuntimeException("Project not found")
                        );

        User owner
                = userRepository.findByUsername(ownerUsername)
                        .orElseThrow(
                                () -> new RuntimeException("Owner not found")
                        );

        // kiểm tra người gọi có phải owner không
        if (!project.getOwner()
                .getId()
                .equals(owner.getId())) {

            throw new RuntimeException(
                    "Only owner can invite member"
            );

        }

        User memberUser
                = userRepository.findByUsername(
                        request.getUsername()
                )
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "User to invite not found"
                                )
                        );

        if (memberRepository.existsByProjectAndUser(
                project,
                memberUser
        )) {

            throw new RuntimeException(
                    "User already member"
            );

        }

        ProjectMember member
                = new ProjectMember();

        member.setProject(project);

        member.setUser(memberUser);

        member.setRole("MEMBER");

        return memberRepository.save(member);

    }

    public List<Project> getAll(String username) {

        User user
                = userRepository.findByUsername(username)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "User not found"
                                )
                        );

        // Project user là OWNER
        List<Project> projects
                = new ArrayList<>(
                        projectRepository.findByOwnerId(
                                user.getId()
                        )
                );

        // Project user là MEMBER
        List<ProjectMember> memberships
                = memberRepository.findByUserId(
                        user.getId()
                );

        for (ProjectMember member : memberships) {

            Project project
                    = member.getProject();

            if (!projects.contains(project)) {

                projects.add(project);

            }

        }

        return projects;

    }

    public Project getById(Long id) {

        return projectRepository.findById(id)
                .orElse(null);

    }

}
