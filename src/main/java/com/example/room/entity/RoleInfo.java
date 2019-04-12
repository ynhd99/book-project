package com.example.room.entity;

import com.example.room.entity.common.Base;
import lombok.Data;

import java.util.List;

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
    /**
     * 包含人数
     */
    private Integer count;
    /**
     * 编码列表
     */
    private List<String> codeList;
    /**
     * 权限list
     */
    private List<RoleAuthority> roleAuthorityList;
    /**
     * 已经选择的权限
     */
    private List<String> nodeIdList;
}
