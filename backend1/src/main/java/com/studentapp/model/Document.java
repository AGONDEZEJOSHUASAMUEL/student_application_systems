package com.studentapp.model;
import jakarta.persistence.*; import java.time.LocalDateTime;
@Entity @Table(name="documents")
public class Document {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(optional=false) private Application application;
 private String originalFilename,storedFilename,contentType;
 private long size;
 private LocalDateTime uploadedAt;
 public Document(){}
 public Long getId(){return id;} public Application getApplication(){return application;} public String getOriginalFilename(){return originalFilename;}
 public String getStoredFilename(){return storedFilename;} public String getContentType(){return contentType;} public long getSize(){return size;} public LocalDateTime getUploadedAt(){return uploadedAt;}
 public void setId(Long v){id=v;} public void setApplication(Application v){application=v;} public void setOriginalFilename(String v){originalFilename=v;}
 public void setStoredFilename(String v){storedFilename=v;} public void setContentType(String v){contentType=v;} public void setSize(long v){size=v;} public void setUploadedAt(LocalDateTime v){uploadedAt=v;}
}
