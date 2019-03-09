package com.example.room.entity;

import com.example.room.common.advice.validatorGroup.Add;
import com.example.room.common.advice.validatorGroup.Update;
import com.example.room.entity.common.Base;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author yangna
 * @date 2019/2/25
 */
@Data
public class RoomCategory extends Base {
    /**
     * 分类名称
     */
    @NotBlank(message = "分类名称不能为空", groups = {Add.class,Update.class})
    private String cateName;
    /**
     * 分类编码
     */
    @NotBlank(message = "分类编码不能为空", groups = Add.class)
    private String cateCode;
    /**
     * 父类id
     */
    private String parentId;
    /**
     * 分类描述
     */
    @NotBlank(message = "分类描述不能为空", groups = {Add.class, Update.class})
    private String cateDesc;
    /**
     * 查询字段
     */
    private String queryString;
    /**
     * 子类列表
     */
    private List<RoomCategory> children;
}
