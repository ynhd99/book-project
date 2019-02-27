package com.example.book.service;

import com.example.book.entity.UserInfo;
import org.springframework.stereotype.Service;

/**
 * @author yangna
 * @date 2019/2/11
 */
public interface UserService {
    /**
     * 获取用户信息
     * @param userName
     * @return
     */
    public UserInfo getUserInfo(String userName);

    /**
     * 注册用户信息
     * @param userInfo
     * @return
     */
    public int userRegister(UserInfo userInfo);

    /**
     * 忘记密码
     * @param userInfo
     * @return
     */
    public int userForgetPass(UserInfo userInfo);
}
