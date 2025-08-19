// Source code is decompiled from a .class file using FernFlower decompiler.
package com.devmosaic.arogyatejas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDto {
   private String token;
   private String role;
   private String message;
}
