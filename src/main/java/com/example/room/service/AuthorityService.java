package com.example.room.service;

import com.example.room.entity.AuthorityInfo;
import com.example.room.entity.RoleInfo;

import java.util.List;

/**
 * @author yangna
 * @date 2019/3/13
 */
public interface AuthorityService {
    /**
     * 得到权限数
     * @return
     */
    public List<AuthorityInfo> getAuthorityTree();
    /**
     * 得到角色信息
     * @return
     */
    public List<RoleInfo> getRoleList();
}
