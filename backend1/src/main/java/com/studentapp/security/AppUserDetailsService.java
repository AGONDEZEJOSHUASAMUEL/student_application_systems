package com.studentapp.security;
import com.studentapp.repository.UserRepository; import org.springframework.security.core.userdetails.*; import org.springframework.stereotype.Service;
@Service
public class AppUserDetailsService implements UserDetailsService{
 private final UserRepository repo; public AppUserDetailsService(UserRepository repo){this.repo=repo;}
 public UserDetails loadUserByUsername(String email)throws UsernameNotFoundException{
  var u=repo.findByEmailIgnoreCase(email).orElseThrow(()->new UsernameNotFoundException("User not found"));
  return User.withUsername(u.getEmail()).password(u.getPasswordHash()).roles(u.getRole()).build();
 }
}
