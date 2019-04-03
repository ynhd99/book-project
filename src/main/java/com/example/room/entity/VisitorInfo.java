package com.example.room.entity;

import com.example.room.entity.common.Base;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author yangna
 * @date 2019/4/2
 */
@Data
public class VisitorInfo extends Base {
    /**
     * 访问者姓名
     */
    private String visitorName;
    /**
     * 身份证号
     */
    private String identityCode;
    /**
     * 手机号
     */
    private String phoneNumber;
    /**
     * 访问开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;
    /**
     * 访问结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;
    /**
     * 接待者姓名
     */
    private String receptName;
    /**
     * 访问事由
     */
    private String remark;
}
