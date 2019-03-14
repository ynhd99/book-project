package com.example.room.dao;

import com.example.room.entity.AuthorityInfo;
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
     * @return
     */
    public List<AuthorityInfo> getAuthorityTree();

    /**
     * 得到角色信息
     * @return
     */
    public List<RoleInfo> getRoleList();

}
