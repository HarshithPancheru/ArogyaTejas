// Source code is decompiled from a .class file using FernFlower decompiler.
package com.devmosaic.arogyatejas.exception;

import com.devmosaic.arogyatejas.dto.AuthResponseDto;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
   public GlobalExceptionHandler() {
   }

   @ExceptionHandler({EmailAlreadyExistsException.class, InvalidEmailOrPasswordException.class})
   public ResponseEntity<AuthResponseDto> handleAuthExceptions(RuntimeException ex) {
      AuthResponseDto errorResponse = new AuthResponseDto(null, null, null,ex.getMessage());
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
   }

   @ExceptionHandler({MethodArgumentNotValidException.class})
public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getFieldErrors().forEach((error) -> {
        errors.put(error.getField(), error.getDefaultMessage());
    });
    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
}


@ExceptionHandler({IllegalArgumentException.class})
public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
}

}
