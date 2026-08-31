package com.studentapp.controller;
import com.studentapp.model.Document; import com.studentapp.repository.*; import org.springframework.core.io.*; import org.springframework.http.*; import org.springframework.security.core.Authentication; import org.springframework.util.StringUtils; import org.springframework.web.bind.annotation.*; import org.springframework.web.multipart.MultipartFile; import java.nio.file.*; import java.time.LocalDateTime; import java.util.*;
@RestController @RequestMapping("/api/documents") @CrossOrigin(origins="*")
public class DocumentController{
 private final ApplicationRepository apps;private final DocumentRepository docs;private final StudentRepository students;
 private final Path root=Paths.get("uploads");
 public DocumentController(ApplicationRepository a,DocumentRepository d,StudentRepository s){apps=a;docs=d;students=s;try{Files.createDirectories(root);}catch(Exception e){throw new RuntimeException(e);}}
 @PostMapping(value="/{number}",consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
 ResponseEntity<?> upload(@PathVariable String number,@RequestParam("file") MultipartFile file,Authentication auth)throws Exception{
  var app=apps.findByApplicationNumber(number).orElseThrow();
  if(!app.getStudent().getEmail().equalsIgnoreCase(auth.getName()))return ResponseEntity.status(403).body(Map.of("message","You can only upload to your own application."));
  if(file.isEmpty()||file.getSize()>10_000_000)return ResponseEntity.badRequest().body(Map.of("message","File is empty or larger than 10MB."));
  String ext=StringUtils.getFilenameExtension(file.getOriginalFilename());Set<String> allowed=Set.of("pdf","jpg","jpeg","png");
  if(ext==null||!allowed.contains(ext.toLowerCase()))return ResponseEntity.badRequest().body(Map.of("message","Only PDF, JPG and PNG files are allowed."));
  String stored=UUID.randomUUID()+"."+ext.toLowerCase();Files.copy(file.getInputStream(),root.resolve(stored),StandardCopyOption.REPLACE_EXISTING);
  Document d=new Document();d.setApplication(app);d.setOriginalFilename(file.getOriginalFilename());d.setStoredFilename(stored);d.setContentType(file.getContentType());d.setSize(file.getSize());d.setUploadedAt(LocalDateTime.now());docs.save(d);
  return ResponseEntity.status(201).body(Map.of("message","Document uploaded","id",d.getId(),"filename",d.getOriginalFilename()));
 }
 @GetMapping("/{number}") Object list(@PathVariable String number,Authentication auth){
  var app=apps.findByApplicationNumber(number).orElseThrow();
  if(!app.getStudent().getEmail().equalsIgnoreCase(auth.getName())&&!auth.getAuthorities().stream().anyMatch(x->x.getAuthority().equals("ROLE_ADMIN")))throw new org.springframework.web.server.ResponseStatusException(HttpStatus.FORBIDDEN);
  return docs.findByApplicationApplicationNumber(number);
 }
 @GetMapping("/file/{id}") ResponseEntity<Resource> download(@PathVariable Long id,Authentication auth)throws Exception{
  Document d=docs.findById(id).orElseThrow();boolean admin=auth.getAuthorities().stream().anyMatch(x->x.getAuthority().equals("ROLE_ADMIN"));
  if(!admin&&!d.getApplication().getStudent().getEmail().equalsIgnoreCase(auth.getName()))throw new org.springframework.web.server.ResponseStatusException(HttpStatus.FORBIDDEN);
  Path p=root.resolve(d.getStoredFilename());Resource r=new UrlResource(p.toUri());return ResponseEntity.ok().contentType(MediaType.parseMediaType(d.getContentType()==null?"application/octet-stream":d.getContentType())).header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=""+d.getOriginalFilename()+""").body(r);
 }
}
