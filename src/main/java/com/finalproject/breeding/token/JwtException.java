package com.finalproject.breeding.token;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JwtException extends Exception{
    private String message;

}
