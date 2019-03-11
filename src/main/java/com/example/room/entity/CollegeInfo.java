package com.example.room.entity;

import com.example.room.common.advice.validatorGroup.Add;
import com.example.room.entity.common.Base;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

/**
 * @author yangna
 * @date 2019/3/11
 */
@Data
public class CollegeInfo extends Base {
    /**
     * 学院编码
     */
    @NotBlank(message = "学院编码不能为空",groups = {Add.class})
    private String collegeCode;
    /**
     * 学院名称
     */
    @NotBlank(message = "学院名称不能为空",groups = {Add.class})
    private String collegeName;
    /**
     * 查询字段
     */
    private String queryString;
}
