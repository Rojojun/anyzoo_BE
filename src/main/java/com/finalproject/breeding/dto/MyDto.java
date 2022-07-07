package com.finalproject.breeding.dto;

import com.finalproject.breeding.error.ErrorCode;
import lombok.Data;

@Data
public class MyDto {
    private ErrorCode status;
    private String message;
    private Object data;

    public MyDto() {
        this.status = ErrorCode.COMMENT_WRONG_INPUT;
        this.data = null;
        this.message = null;
    }

}

