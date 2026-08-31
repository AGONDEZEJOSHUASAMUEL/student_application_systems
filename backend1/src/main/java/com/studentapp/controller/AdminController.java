package com.studentapp.controller;
import com.studentapp.repository.*; import org.springframework.http.*; import org.springframework.web.bind.annotation.*; import java.util.*;
@RestController @RequestMapping("/api/admin") @CrossOrigin(origins="*")
public class AdminController{
 private final ApplicationRepository apps;
 public AdminController(ApplicationRepository a){apps=a;}
 @GetMapping("/dashboard") Object dashboard(){
  return Map.of("total",apps.count(),"pending",apps.countByStatus("Pending"),"underReview",apps.countByStatus("Under Review"),"accepted",apps.countByStatus("Accepted"),"rejected",apps.countByStatus("Rejected"));
 }
 @GetMapping("/applications") Object all(){return apps.findAll();}
 @PatchMapping("/applications/{number}/status") Object status(@PathVariable String number,@RequestParam String value){
  return apps.findByApplicationNumber(number).map(a->{a.setStatus(value);apps.save(a);return a;}).orElseThrow();
 }
}
