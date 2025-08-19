// Source code is decompiled from a .class file using FernFlower decompiler.
package com.devmosaic.arogyatejas.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginDto {
   private @NotBlank(
   message = "Email is required"
) @Email(
   message = "Invalid email format"
) String email;
   private @NotBlank(
   message = "Password is required"
) @Pattern(
   regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
   message = "Password must contain at least 8 characters, including uppercase, lowercase, number, and special character"
) String password;
}
