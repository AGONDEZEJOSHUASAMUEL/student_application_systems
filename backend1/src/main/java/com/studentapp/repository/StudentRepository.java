package com.studentapp.repository;
import com.studentapp.model.Student; import org.springframework.data.jpa.repository.JpaRepository; import java.util.Optional;
public interface StudentRepository extends JpaRepository<Student,Long>{Optional<Student> findByEmailIgnoreCase(String email);}
