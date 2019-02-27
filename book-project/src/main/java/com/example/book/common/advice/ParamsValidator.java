package com.example.book.common.advice;
import com.example.book.common.exception.ParamException;
import com.example.book.utils.common.AirUtils;
import org.springframework.util.ClassUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.SmartValidator;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author yangna
 * @date 2019/2/17
 */
public class ParamsValidator implements SmartValidator {

    private javax.validation.Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
    // 注解上没有有group的验证逻辑
    @Override
    public void validate(Object target, Errors errors) {
        validate(target,errors,null);
    }
    // 注解上带有group的验证逻辑
    // 第一个参数为我们要验证的参数，第二个不用管，第三个为注解上设置个groups
    @Override
    public void validate(Object target, Errors errors, Object... validationHints) {
        // 这里面为验证实现，可以根据自己的需要进行完善与修改
        if (!AirUtils.hv(target)) {
            throw new ParamException("参数不能为空！");
        } else if(target instanceof Collection) {
                for(Object o:(Collection)target){
                    validate(o,validationHints);
                }
            }else {
                validate(target,validationHints);
            }

        }
    private void validate(Object target,Object ... objs) {
        Set<ConstraintViolation<Object>> violations;
        // 没有groups的验证
        if(objs==null || objs.length==0) {
            violations = validator.validate(target);
        } else {
            // 基于groups的验证
            Set<Class<?>> groups = new LinkedHashSet<Class<?>>();
            for (Object hint : objs) {
                if (hint instanceof Class) {
                    groups.add((Class<?>) hint);
                }
            }
            violations = validator.validate(target, ClassUtils.toClassArray(groups));
        }
        // 若为空，则验证通过
        if(violations==null||violations.isEmpty()) {
            return;
        }
        // 验证不通过则抛出ParamsException异常。
        for(ConstraintViolation item:violations) {
            throw new ParamException(item.getMessage());
        }
    }
}