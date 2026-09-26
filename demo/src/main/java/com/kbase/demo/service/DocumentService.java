package com.kbase.demo.service;

import com.kbase.demo.entity.Document;
import com.kbase.demo.entity.Project;
import com.kbase.demo.entity.User;

import com.kbase.demo.repository.DocumentRepository;
import com.kbase.demo.repository.ProjectRepository;
import com.kbase.demo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.nio.file.*;

import java.util.List;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;

    private final ProjectRepository projectRepository;

    private final UserRepository userRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public DocumentService(
            DocumentRepository documentRepository,
            ProjectRepository projectRepository,
            UserRepository userRepository
    ) {

        this.documentRepository = documentRepository;

        this.projectRepository = projectRepository;

        this.userRepository = userRepository;

    }

    // UPLOAD FILE
    public Document upload(
            Long projectId,
            MultipartFile file,
            String username
    ) throws IOException {

        Project project
                = projectRepository.findById(projectId)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Project not found"
                                )
                        );

        User user
                = userRepository.findByUsername(username)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "User not found"
                                )
                        );

        Path folder
                = Paths.get(uploadDir);

        if (!Files.exists(folder)) {

            Files.createDirectories(folder);

        }

        String fileName
                = System.currentTimeMillis()
                + "_"
                + file.getOriginalFilename();

        Path path
                = folder.resolve(fileName);

        Files.copy(
                file.getInputStream(),
                path,
                StandardCopyOption.REPLACE_EXISTING
        );

        Document document
                = new Document();

        document.setFileName(
                file.getOriginalFilename()
        );

        document.setFileType(
                file.getContentType()
        );

        document.setFilePath(
                path.toString()
        );

        document.setFileSize(
                file.getSize()
        );

        document.setProject(project);

        document.setUploadedBy(user);

        return documentRepository.save(document);

    }

    // GET DOCUMENTS
    public List<Document> getByProject(Long projectId) {

        return documentRepository
                .findByProject_Id(projectId);

    }

    // GET BY ID
    public Document getById(Long id) {

        return documentRepository
                .findById(id)
                .orElse(null);

    }

    // DELETE DATABASE + FILE
    public void delete(Long id) throws IOException {

        Document document
                = documentRepository.findById(id)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Document not found"
                                )
                        );

        // lấy đường dẫn file
        Path path
                = Paths.get(
                        document.getFilePath()
                );

        // xóa file thật
        if (Files.exists(path)) {

            Files.delete(path);

        }

        // xóa database
        documentRepository.delete(document);

    }

}
