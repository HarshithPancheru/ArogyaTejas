// Source code is decompiled from a .class file using FernFlower decompiler.
package com.devmosaic.arogyatejas.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminRegistrationDto {
   @NotBlank(message = "First name is required") 
   private String firstName;

   @NotBlank(message = "Last name is required") 
   private String lastName;

   @NotBlank(message = "Password is required") 
   @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", 
            message = "Password must contain at least 8 characters, including uppercase, lowercase, number, and special character") 
   private String password;
   
   
   @NotBlank(message = "Email is required") 
   @Email(message = "Invalid email format") 
   private String email;
}
