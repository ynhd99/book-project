package com.example.room.common.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Component
@Aspect
public class ParamsValidateAdvice {
    // 这里重用上面定义的那个validator
    private ParamsValidator validator = new ParamsValidator();
    /**
     * 拦截参数上加了@Validated的注解的方法
     * 排除掉controller，因为controller有自己的参数校验实现 不需要aop
     */
    @Pointcut("execution(* com.example.room.service.*.*(..))")
    public void pointCut(){}
 
    @Before("pointCut()")
    public void doBefore(JoinPoint joinPoint){
        Object[] params=joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Parameter[] parameters = method.getParameters();
        // 验证参数上的注解
        for(int i=0;i<parameters.length;i++) {
            Parameter p = parameters[i];
            // 获取参数上的注解
            Validated validated = p.getAnnotation(Validated.class);
            if(validated==null) {
                continue;
            }
            // 如果设置了group
            if(validated.value()!=null && validated.value().length>0) {
                validator.validate(params[i],null,validated.value());
            } else {
                validator.validate(params[i],null);
            }
        }
    }
}