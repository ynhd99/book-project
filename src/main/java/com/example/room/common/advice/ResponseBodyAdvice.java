package com.example.room.common.advice;

import com.example.room.entity.dto.MessageBody;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;

/**
 * 返回统一处理
 *  @author fanjichao
 */
//@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class ResponseBodyAdvice implements org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice {
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if(body==null) {
            return MessageBody.getMessageBody(null);
        }
        if(body instanceof MessageBody) {
            return body;
        }
        return MessageBody.getMessageBody(body);
    }


}
