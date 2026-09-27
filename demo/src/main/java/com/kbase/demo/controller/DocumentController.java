package com.kbase.demo.controller;

import com.kbase.demo.entity.Document;
import com.kbase.demo.service.DocumentService;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class DocumentController {

    private final DocumentService service;

    public DocumentController(
            DocumentService service
    ) {

        this.service = service;

    }

    // ==========================
    // UPLOAD DOCUMENT
    // ==========================
    @PostMapping(
            value = "/{projectId}/documents/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public Document upload(
            @PathVariable Long projectId,
            @RequestParam("file")
            @Parameter(
                    description = "Upload document file",
                    content = @Content(
                            mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            schema = @Schema(
                                    type = "string",
                                    format = "binary"
                            )
                    )
            ) MultipartFile file,
            Authentication authentication
    ) throws IOException {

        return service.upload(
                projectId,
                file,
                authentication.getName()
        );

    }

    // ==========================
    // GET DOCUMENT LIST BY PROJECT
    // ==========================
    @GetMapping("/{projectId}/documents")
    public List<Document> getDocuments(
            @PathVariable Long projectId
    ) {

        return service.getByProject(projectId);

    }

    // ==========================
    // SEARCH DOCUMENT
    // ==========================
    @GetMapping("/{projectId}/documents/search")
    public List<Document> searchDocuments(
            @PathVariable Long projectId,
            @RequestParam String keyword
    ) {

        return service.searchDocument(
                projectId,
                keyword
        );

    }

    // ==========================
    // DOWNLOAD DOCUMENT
    // ==========================
    @GetMapping("/documents/{id}/download")
    public ResponseEntity<Resource> download(
            @PathVariable Long id
    ) throws IOException {

        Document document = service.getById(id);

        if (document == null) {

            return ResponseEntity
                    .notFound()
                    .build();

        }

        Path path = Paths.get(
                document.getFilePath()
        );

        Resource resource = new UrlResource(
                path.toUri()
        );

        if (!resource.exists()) {

            return ResponseEntity
                    .notFound()
                    .build();

        }

        return ResponseEntity.ok()
                .contentType(
                        MediaType.parseMediaType(
                                document.getFileType()
                        )
                )
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\""
                        + document.getFileName()
                        + "\""
                )
                .body(resource);

    }

    // ==========================
    // DELETE DOCUMENT
    // ==========================
    @DeleteMapping("/documents/{id}")
    public String delete(
            @PathVariable Long id
    ) throws IOException {

        service.delete(id);

        return "Deleted";

    }

}
