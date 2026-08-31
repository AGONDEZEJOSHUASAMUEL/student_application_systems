package com.studentapp.controller;
import com.studentapp.repository.*; import org.springframework.security.core.Authentication; import org.springframework.web.bind.annotation.*; import java.util.*;
@RestController @RequestMapping("/api/student") @CrossOrigin(origins="*")
public class StudentController{
 private final StudentRepository students;private final ApplicationRepository apps;private final DocumentRepository docs;
 public StudentController(StudentRepository s,ApplicationRepository a,DocumentRepository d){students=s;apps=a;docs=d;}
 @GetMapping("/me") Object me(Authentication auth){return students.findByEmailIgnoreCase(auth.getName()).orElseThrow();}
 @GetMapping("/applications") Object applications(Authentication auth){return apps.findByStudentEmailIgnoreCase(auth.getName()).stream().map(a->Map.of("applicationNumber",a.getApplicationNumber(),"program",a.getProgram(),"intake",a.getIntake(),"status",a.getStatus(),"applicationDate",a.getApplicationDate().toString(),"documents",docs.findByApplicationApplicationNumber(a.getApplicationNumber()).size())).toList();}
}
