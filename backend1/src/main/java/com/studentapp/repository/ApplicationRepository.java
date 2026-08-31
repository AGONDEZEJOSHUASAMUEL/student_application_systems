package com.studentapp.repository;
import com.studentapp.model.Application; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface ApplicationRepository extends JpaRepository<Application,Long>{
 Optional<Application> findByApplicationNumber(String n); List<Application> findByStudentEmailIgnoreCase(String e);
 long countByStatus(String s);
}
