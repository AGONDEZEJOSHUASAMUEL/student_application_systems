package com.studentapp.controller;
import com.studentapp.model.*; import com.studentapp.repository.*; import jakarta.validation.Valid; import org.springframework.http.*; import org.springframework.web.bind.annotation.*; import java.time.LocalDate; import java.util.*;
record ApplicationRequest(@jakarta.validation.constraints.NotBlank String firstName,@jakarta.validation.constraints.NotBlank String lastName,@jakarta.validation.constraints.NotBlank String dateOfBirth,String gender,@jakarta.validation.constraints.Email @jakarta.validation.constraints.NotBlank String email,String phone,String address,String previousSchool,Integer yearCompleted,@jakarta.validation.constraints.NotBlank String program,@jakarta.validation.constraints.NotBlank String intake,String additionalInfo){}
@RestController @RequestMapping("/api/applications") @CrossOrigin(origins="*")
public class ApplicationController{
 private final StudentRepository students;private final ApplicationRepository apps;
 public ApplicationController(StudentRepository s,ApplicationRepository a){students=s;apps=a;}
 @PostMapping public ResponseEntity<?> submit(@Valid @RequestBody ApplicationRequest r){
  if(students.findByEmailIgnoreCase(r.email()).isPresent())return ResponseEntity.badRequest().body(Map.of("message","A student with this email already exists."));
  Student s=new Student();s.setFirstName(r.firstName());s.setLastName(r.lastName());s.setDateOfBirth(r.dateOfBirth());s.setGender(r.gender());s.setEmail(r.email());s.setPhone(r.phone());s.setAddress(r.address());s.setPreviousSchool(r.previousSchool());s.setYearCompleted(r.yearCompleted());students.save(s);
  Application a=new Application();a.setApplicationNumber("APP-"+LocalDate.now().getYear()+"-"+String.format("%04d",s.getId()));a.setStudent(s);a.setProgram(r.program());a.setIntake(r.intake());a.setAdditionalInfo(r.additionalInfo());a.setStatus("Pending");a.setApplicationDate(LocalDate.now());apps.save(a);
  return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("applicationNumber",a.getApplicationNumber(),"status",a.getStatus()));
 }
}
