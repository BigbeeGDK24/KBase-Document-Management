package com.kbase.demo.repository;


import com.kbase.demo.entity.Document;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;



@Repository
public interface DocumentRepository 
        extends JpaRepository<Document, Long> {


    List<Document> findByProject_Id(Long projectId);


}