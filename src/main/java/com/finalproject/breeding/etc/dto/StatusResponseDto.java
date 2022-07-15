package com.finalproject.breeding.etc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StatusResponseDto {
    private String msg;
    private Object data;

    public StatusResponseDto(String msg) {
        this.msg = msg;
    }
}
