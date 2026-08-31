package com.studentapp.controller;

import com.studentapp.dto.ApplicationRequest;
import com.studentapp.model.*;
import com.studentapp.repository.*;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/applications")
@CrossOrigin(origins="*")
public class ApplicationController {
    private final StudentRepository students;
    private final ApplicationRepository applications;

    public ApplicationController(StudentRepository students, ApplicationRepository applications){
        this.students=students; this.applications=applications;
    }

    @PostMapping
    public ResponseEntity<?> submit(@Valid @RequestBody ApplicationRequest r){
        if(students.findByEmailIgnoreCase(r.email()).isPresent())
            return ResponseEntity.badRequest().body(Map.of("message","An application already exists for this email."));
        Student s=new Student();
        s.setFirstName(r.firstName()); s.setLastName(r.lastName()); s.setDateOfBirth(r.dateOfBirth());
        s.setGender(r.gender()); s.setEmail(r.email()); s.setPhone(r.phone()); s.setAddress(r.address());
        s.setPreviousSchool(r.previousSchool()); s.setYearCompleted(r.yearCompleted());
        students.save(s);
        Application a=new Application();
        a.setApplicationNumber("APP-"+LocalDate.now().getYear()+"-"+String.format("%04d", s.getId()));
        a.setStudent(s); a.setProgram(r.program()); a.setIntake(r.intake());
        a.setAdditionalInfo(r.additionalInfo()); a.setStatus("Pending"); a.setApplicationDate(LocalDate.now());
        applications.save(a);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(a));
    }

    @GetMapping("/{number}")
    public ResponseEntity<?> get(@PathVariable String number){
        return applications.findByApplicationNumber(number).map(a->ResponseEntity.ok(toResponse(a)))
          .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public List<Map<String,Object>> search(@RequestParam String email){
        return applications.findByStudentEmailIgnoreCase(email).stream().map(this::toResponse).toList();
    }

    @GetMapping
    public List<Map<String,Object>> all(){return applications.findAll().stream().map(this::toResponse).toList();}

    @PatchMapping("/{number}/status")
    public ResponseEntity<?> status(@PathVariable String number,@RequestParam String value){
        if(!Set.of("Pending","Under Review","Accepted","Rejected").contains(value))
            return ResponseEntity.badRequest().body(Map.of("message","Invalid status."));
        return applications.findByApplicationNumber(number).map(a->{a.setStatus(value); applications.save(a); return ResponseEntity.ok(toResponse(a));})
          .orElse(ResponseEntity.notFound().build());
    }

    private Map<String,Object> toResponse(Application a){
        Student s=a.getStudent();
        return Map.of("applicationNumber",a.getApplicationNumber(),"status",a.getStatus(),
          "applicationDate",a.getApplicationDate().toString(),"program",a.getProgram(),"intake",a.getIntake(),
          "student",Map.of("id",s.getId(),"firstName",s.getFirstName(),"lastName",s.getLastName(),
          "email",s.getEmail(),"phone",s.getPhone(),"previousSchool",s.getPreviousSchool()));
    }
}
