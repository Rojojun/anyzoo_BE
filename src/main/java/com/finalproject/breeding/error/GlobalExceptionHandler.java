package com.finalproject.breeding.error;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionResponse> customException(CustomException ex){
        ExceptionResponse response = new ExceptionResponse(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getErrorCode()));
    }

//    @ExceptionHandler(ExpiredJwtException.class)
////    @ResponseStatus(HttpStatus.FORBIDDEN)
////    public CustomException ExpiredJwtExceptionException(Exception e) {
////        return new CustomException(ErrorCode.JWT_TOKEN_EXPIRED);
////    }
}
