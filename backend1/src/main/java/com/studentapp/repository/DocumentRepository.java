package com.studentapp.repository;
import com.studentapp.model.Document; import org.springframework.data.jpa.repository.JpaRepository; import java.util.List;
public interface DocumentRepository extends JpaRepository<Document,Long>{List<Document> findByApplicationApplicationNumber(String n);}
