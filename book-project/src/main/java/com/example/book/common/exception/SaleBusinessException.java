package com.example.book.common.exception;


import com.example.book.common.response.ResponseEumn;

/**
 * 业务异常类
 *  @author yangna
 */
public class SaleBusinessException extends RuntimeException{

    private String code = "400";

    private Object data;

    private Exception exception;

    public SaleBusinessException() {
    }

    public SaleBusinessException(String code, String message) {
        super(message);
        this.code = code;
    }
    public SaleBusinessException(ResponseEumn responseEumn) {
        super(responseEumn.getDesc());
        this.code = responseEumn.getCode();
    }
    public SaleBusinessException(String code, String message, Object data) {
        super(message);
        this.code = code;
        this.data = data;
    }
    public SaleBusinessException(String message) {
        super(message);
    }

    public SaleBusinessException(String message, Throwable cause) {
        super(message, cause);
    }
    public SaleBusinessException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
    public SaleBusinessException(Throwable cause) {
        super(cause);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
