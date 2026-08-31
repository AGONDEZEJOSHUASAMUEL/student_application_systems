package com.studentapp.config;
import org.springframework.context.annotation.*; import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration public class SecurityConfig{
 @Bean PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}
 @Bean SecurityFilterChain filterChain(HttpSecurity http)throws Exception{
  http.csrf(c->c.disable()).cors(c->{}).authorizeHttpRequests(a->a
   .requestMatchers("/api/auth/register","/api/auth/login").permitAll()
   .requestMatchers("/api/admin/**").hasRole("ADMIN")
   .requestMatchers("/api/student/**").hasRole("STUDENT")
   .requestMatchers("/api/applications").hasRole("STUDENT")
   .anyRequest().authenticated())
   .httpBasic(b->{});
  return http.build();
 }
}
