// Source code is decompiled from a .class file using FernFlower decompiler.
package com.devmosaic.arogyatejas.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PatientRegistrationDto {
   private @NotBlank(
   message = "First name is required"
) String firstName;
   private @NotBlank(
   message = "Last name is required"
) String lastName;
   private @NotBlank(
   message = "Password is required"
) @Pattern(
   regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$",
   message = "Password must contain at least one digit, one lowercase, one uppercase letter, one special character, and be at least 8 characters long"
) String password;
   private @NotBlank(
   message = "Email is required"
) @Email(
   message = "Invalid email format"
) String email;
}
