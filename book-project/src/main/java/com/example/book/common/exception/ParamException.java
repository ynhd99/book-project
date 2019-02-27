package com.example.book.common.exception;

import lombok.Data;

/**
 * @author yangna
 * @date 2019/2/17
 */
@Data
public class ParamException extends RuntimeException {
    private String code;
    private String message;
    public ParamException(){

    }
    public ParamException(String code,String message){
        this.message = message;
        this.code = code;
    }
    public ParamException(String message){
        this.message = message;
    }
}
