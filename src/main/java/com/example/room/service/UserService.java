package com.example.room.service;

import com.example.room.entity.UserInfo;
import com.example.room.entity.dto.StaffInfoDto;

import java.util.List;

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

    /**
     * 修改用户信息
     * @param userInfo
     * @return
     */
    public int updateUser(UserInfo userInfo);

    /**
     * 删除用户信息
     * @param userInfo
     * @return
     */
    public int deleteUser(UserInfo userInfo);

    /**
     * 获取用户权限
     * @param userInfo
     * @return
     */
    List<String> getAuthorityList(UserInfo userInfo);
    /**
     * 获取用户权限
     * @param userName
     * @return
     */
    StaffInfoDto getStaffInfo(String userName);
}
