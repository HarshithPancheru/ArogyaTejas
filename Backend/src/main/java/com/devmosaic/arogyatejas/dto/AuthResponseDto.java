// Source code is decompiled from a .class file using FernFlower decompiler.
package com.devmosaic.arogyatejas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDto {
    private String token;
    private String role;
    private String fullName;
    private String message;
}
