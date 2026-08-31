package com.studentapp.config;
import com.studentapp.model.User; import com.studentapp.repository.UserRepository; import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*; import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration public class DataInitializer{
 @Bean CommandLineRunner seed(UserRepository repo,PasswordEncoder encoder){return args->{
  if(repo.findByEmailIgnoreCase("admin@school.com").isEmpty()){
   User u=new User();u.setEmail("admin@school.com");u.setPasswordHash(encoder.encode("Admin@123"));u.setRole("ADMIN");u.setFullName("System Administrator");repo.save(u);
  }
 };}
}
