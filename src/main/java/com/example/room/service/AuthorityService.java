package com.example.room.service;

import com.example.room.entity.AuthorityInfo;
import com.example.room.entity.RoleInfo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author yangna
 * @date 2019/3/13
 */
public interface AuthorityService {
    /**
     * 得到权限数
     *
     * @return
     */
    public List<AuthorityInfo> getAuthorityTree(String id);

    /**
     * 得到角色信息
     *
     * @return
     */
    public PageInfo<RoleInfo> getRoleList(RoleInfo roleInfo);

    /**
     * 新增角色信息
     *
     * @param roleInfo
     * @return
     */
    int addRole(RoleInfo roleInfo);

    /**
     * 批量新增角色权限关系
     *
     * @param roleInfo
     * @return
     */
    int updateRole(RoleInfo roleInfo);
}
