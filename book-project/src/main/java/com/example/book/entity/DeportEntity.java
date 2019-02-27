package com.example.book.entity;

import com.example.book.entity.common.Base;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @author yangna
 * @date 2019/2/19
 */
@Data
public class DeportEntity extends Base {
    /**
     * 仓库名称
     */
    @NotBlank(message = "仓库名称不能为空")
    private String deportName;
    /**
     * 仓库编码
     */
    @NotBlank(message = "仓库编码不能为空")
    private String deportCode;
    /**
     * 期初时间
     */
    private int beginFlag;
    /**
     * 期初时间
     */
    private Date beginTime;
    /**
     * 模糊查询字段
     */
    private String queryString;
}
