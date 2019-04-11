package com.example.room.dao;

import com.example.room.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yangna
 * @date 2019/2/11
 */
@Repository
@Mapper
public interface UserDao {
    UserInfo getUserInfo(String userName);

    int userRegister(UserInfo userInfo);

    int userForgetPass(UserInfo userInfo);

    int updateUser(UserInfo userInfo);

    int deleteUser(UserInfo userInfo);

    List<String> getAuthorityList(UserInfo userInfo);

    List<String> getUserList(List<String> ids);
}

