package com.example.room.entity;

import com.example.room.entity.common.Base;
import lombok.Data;

/**
 * @author yangna
 * @date 2019/3/13
 */
@Data
public class RoleInfo extends Base {
    /**
     * 角色编码
     */
    private String roleCode;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 模糊查询字段
     */
    private String queryString;

}
