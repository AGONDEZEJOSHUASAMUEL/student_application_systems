package com.studentapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name="students")
public class Student {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @NotBlank private String firstName;
    @NotBlank private String lastName;
    @NotBlank private String dateOfBirth;
    @NotBlank private String gender;
    @Email @NotBlank @Column(unique=true) private String email;
    @NotBlank private String phone;
    private String address;
    @NotBlank private String previousSchool;
    private Integer yearCompleted;

    public Student() {}
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public String getFirstName(){return firstName;} public void setFirstName(String v){firstName=v;}
    public String getLastName(){return lastName;} public void setLastName(String v){lastName=v;}
    public String getDateOfBirth(){return dateOfBirth;} public void setDateOfBirth(String v){dateOfBirth=v;}
    public String getGender(){return gender;} public void setGender(String v){gender=v;}
    public String getEmail(){return email;} public void setEmail(String v){email=v;}
    public String getPhone(){return phone;} public void setPhone(String v){phone=v;}
    public String getAddress(){return address;} public void setAddress(String v){address=v;}
    public String getPreviousSchool(){return previousSchool;} public void setPreviousSchool(String v){previousSchool=v;}
    public Integer getYearCompleted(){return yearCompleted;} public void setYearCompleted(Integer v){yearCompleted=v;}
}
