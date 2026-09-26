package com.kbase.demo.controller;

import com.kbase.demo.dto.ProjectMemberRequest;
import com.kbase.demo.entity.Project;
import com.kbase.demo.entity.ProjectMember;
import com.kbase.demo.service.ProjectService;

import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService service;

    public ProjectController(ProjectService service) {

        this.service = service;

    }

    @PostMapping
    public Project create(
            @RequestBody Project project,
            Authentication authentication
    ) {

        return service.create(
                project,
                authentication.getName()
        );

    }

    @GetMapping
    public List<Project> getAll(
            Authentication authentication
    ) {

        return service.getAll(
                authentication.getName()
        );

    }

    @GetMapping("/{id}")
    public Project get(
            @PathVariable Long id
    ) {

        return service.getById(id);

    }

    // INVITE MEMBER
    @PostMapping("/{id}/members")
    public ProjectMember addMember(
            @PathVariable Long id,
            @RequestBody ProjectMemberRequest request,
            Authentication authentication
    ) {

        return service.addMember(
                id,
                request,
                authentication.getName()
        );

    }

}
