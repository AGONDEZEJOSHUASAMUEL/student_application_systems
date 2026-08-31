package com.studentapp.dto;
import jakarta.validation.constraints.*;
public record ApplicationRequest(
 @NotBlank String firstName,@NotBlank String lastName,@NotBlank String dateOfBirth,
 @NotBlank String gender,@Email @NotBlank String email,@NotBlank String phone,String address,
 @NotBlank String previousSchool,Integer yearCompleted,@NotBlank String program,
 @NotBlank String intake,String additionalInfo) {}
