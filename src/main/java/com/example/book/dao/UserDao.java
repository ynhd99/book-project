package com.example.book.dao;

import com.example.book.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author yangna
 * @date 2019/2/11
 */
@Repository
@Mapper
public interface UserDao {
    public UserInfo getUserInfo(String userName);
    public int userRegister(UserInfo userInfo);
    public int userForgetPass(UserInfo userInfo);
}

