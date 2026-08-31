package com.studentapp.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="applications")
public class Application {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(unique=true, nullable=false) private String applicationNumber;
    @ManyToOne(optional=false) private Student student;
    @Column(nullable=false) private String program;
    @Column(nullable=false) private String intake;
    private String additionalInfo;
    @Column(nullable=false) private String status;
    @Column(nullable=false) private LocalDate applicationDate;

    public Application(){}
    public Long getId(){return id;} public String getApplicationNumber(){return applicationNumber;}
    public Student getStudent(){return student;} public String getProgram(){return program;}
    public String getIntake(){return intake;} public String getAdditionalInfo(){return additionalInfo;}
    public String getStatus(){return status;} public LocalDate getApplicationDate(){return applicationDate;}
    public void setId(Long v){id=v;} public void setApplicationNumber(String v){applicationNumber=v;}
    public void setStudent(Student v){student=v;} public void setProgram(String v){program=v;}
    public void setIntake(String v){intake=v;} public void setAdditionalInfo(String v){additionalInfo=v;}
    public void setStatus(String v){status=v;} public void setApplicationDate(LocalDate v){applicationDate=v;}
}
