package com.studentapp.controller;
import com.studentapp.model.User; import com.studentapp.repository.UserRepository; import jakarta.validation.constraints.*; import org.springframework.http.*; import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.web.bind.annotation.*;
record RegisterRequest(@Email @NotBlank String email,@NotBlank @Size(min=8) String password,@NotBlank String fullName){}
@RestController @RequestMapping("/api/auth") @CrossOrigin(origins="*")
public class AuthController{
 private final UserRepository repo; private final PasswordEncoder encoder;
 public AuthController(UserRepository r,PasswordEncoder e){repo=r;encoder=e;}
 @PostMapping("/register") ResponseEntity<?> register(@RequestBody RegisterRequest r){
  if(repo.findByEmailIgnoreCase(r.email()).isPresent()) return ResponseEntity.badRequest().body(java.util.Map.of("message","Email already registered."));
  User u=new User();u.setEmail(r.email());u.setPasswordHash(encoder.encode(r.password()));u.setRole("STUDENT");u.setFullName(r.fullName());repo.save(u);
  return ResponseEntity.status(HttpStatus.CREATED).body(java.util.Map.of("message","Registration successful. You can now log in."));
 }
 @PostMapping("/login") ResponseEntity<?> login(){return ResponseEntity.ok(java.util.Map.of("message","Login handled by HTTP Basic authentication. Use your email and password in the Authorization header."));}
}
