package com.example.room.entity.common;

import com.example.room.common.advice.validatorGroup.Delete;
import com.example.room.common.advice.validatorGroup.Update;
import com.example.room.common.advice.validatorGroup.UpdateStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author yangna
 * @date 2019/2/11
 */
@Data
public class Base implements Serializable {
    private static final long serialVersionUID = 6622679317467444649L;
    @NotNull(message = "id不能为空", groups = {UpdateStatus.class, Delete.class,Update.class})
    private String id;
    private Date createTime;
    private String createUser;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date updateTime;
    private String updateUser;
    private int deleteFlag;
    private Integer status;
    private int page;
    private int size;
    private int Row;
}
