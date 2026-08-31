package com.studentapp.model;
import jakarta.persistence.*;
@Entity @Table(name="users")
public class User {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(unique=true,nullable=false) private String email;
 @Column(nullable=false) private String passwordHash;
 @Column(nullable=false) private String role;
 private String fullName;
 public User(){}
 public Long getId(){return id;} public String getEmail(){return email;} public String getPasswordHash(){return passwordHash;}
 public String getRole(){return role;} public String getFullName(){return fullName;}
 public void setId(Long v){id=v;} public void setEmail(String v){email=v;} public void setPasswordHash(String v){passwordHash=v;}
 public void setRole(String v){role=v;} public void setFullName(String v){fullName=v;}
}
