package com.example.book.common.configution;

import com.example.book.common.advice.ParamsValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author yangna
 * @date 2019/2/19
 */
@Configuration
public class WebConfig  extends WebMvcConfigurationSupport {
    @Override
    public Validator getValidator(){
      return createValidator();
    }
    @Bean
    public ParamsValidator createValidator(){
        return new ParamsValidator();
    }
}
