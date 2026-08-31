package com.studentapp.model;
import jakarta.persistence.*; import java.time.LocalDate;
@Entity @Table(name="applications")
public class Application {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(unique=true,nullable=false) private String applicationNumber;
 @ManyToOne(optional=false) private Student student;
 @Column(nullable=false) private String program,intake,status;
 private String additionalInfo;
 @Column(nullable=false) private LocalDate applicationDate;
 public Application(){}
 public Long getId(){return id;} public void setId(Long v){id=v;}
 public String getApplicationNumber(){return applicationNumber;} public void setApplicationNumber(String v){applicationNumber=v;}
 public Student getStudent(){return student;} public void setStudent(Student v){student=v;}
 public String getProgram(){return program;} public void setProgram(String v){program=v;}
 public String getIntake(){return intake;} public void setIntake(String v){intake=v;}
 public String getStatus(){return status;} public void setStatus(String v){status=v;}
 public String getAdditionalInfo(){return additionalInfo;} public void setAdditionalInfo(String v){additionalInfo=v;}
 public LocalDate getApplicationDate(){return applicationDate;} public void setApplicationDate(LocalDate v){applicationDate=v;}
}
