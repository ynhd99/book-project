package com.example.room.common.advice;

import com.example.room.common.exception.ParamException;
import com.example.room.common.exception.SaleBusinessException;
import com.example.room.entity.dto.MessageBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常拦截器
 * @author yangna
 */
@ControllerAdvice
public class ExceptionAdvice {

    private static Logger L = LoggerFactory.getLogger(ExceptionAdvice.class);


    @ExceptionHandler(value = SaleBusinessException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public MessageBody handleBusinessException(HttpServletRequest request, SaleBusinessException e){
        L.error(e.getMessage(),e);
        return getErrorMessageBody(e.getData(),e.getMessage(),e.getMessage(),e.getCode());
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public MessageBody handleException(HttpServletRequest request, Exception e){
        String uri = request.getRequestURI();
        L.error("url:"+uri+";message:"+e.getMessage(),e);
        return getErrorMessageBody(null,e.getMessage(),"系统异常，请稍后重试！","500");
    }

    @ExceptionHandler(value = ParamException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public MessageBody handleBusinessException(HttpServletRequest request, ParamException e){
        L.error(e.getMessage(),e);
        return getErrorMessageBody(null,e.getMessage(),e.getMessage(),"400");
    }

    private MessageBody getErrorMessageBody(Object data,String message,String errorInfo,String code){
        MessageBody body = new MessageBody();
        body.setCode(code);
        body.setData(data);
        body.setErrorCode(Integer.parseInt(code));
        body.setErrorInfo(errorInfo);
        body.setMessage(message);
        body.setSuccess(false);
        return body;
    }
}
