package com.example.room.dao;

import com.example.room.entity.AuthorityInfo;
import com.example.room.entity.RoleAuthority;
import com.example.room.entity.RoleInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yangna
 * @date 2019/3/13
 */
@Mapper
@Repository
public interface AuthorityDao {
    /**
     * 得到权限数
     *
     * @return
     */
    public List<AuthorityInfo> getAuthorityTree();

    /**
     * 得到角色信息
     *
     * @return
     */
    public List<RoleInfo> getRoleList(RoleInfo roleInfo);

    /**
     * 新增角色信息
     *
     * @param roleInfo
     * @return
     */
    int addRole(RoleInfo roleInfo);

    /**
     * 批量查询权限信息
     *
     * @param ids
     * @return
     */
    List<RoleAuthority> getAuthorityList(List<String> ids);

    /**
     * 批量新增角色权限关系
     *
     * @param roleAuthorities
     * @return
     */
    int addRoleAuthority(List<RoleAuthority> roleAuthorities);

    /**
     * 更新角色信息
     *
     * @param roleInfo
     * @return
     */
    int updateRole(RoleInfo roleInfo);

    /**
     * 批量获取角色权限关系
     *
     * @return
     */
    List<RoleAuthority> getRoleAuthorityList(List<String> ids);

    /**
     * 批量删除权限
     *
     * @param id
     * @return
     */
    int batchDeleteAuthority(String id);

    /**
     * 删除角色信息
     *
     * @param roleInfo
     * @return
     */
    int deleteRole(RoleInfo roleInfo);

    /**
     * 根据名称获取角色id
     * @param nameList
     * @return
     */
    List<RoleInfo> getRoleByName(List<String> nameList);

    /**
     * 根据编码获取角色信息
     * @param roleCode
     * @return
     */
    RoleInfo getRoleByCode(String roleCode);
}
