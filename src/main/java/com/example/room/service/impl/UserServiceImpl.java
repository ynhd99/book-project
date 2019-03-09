package com.example.room.service.impl;

import com.example.room.common.advice.validatorGroup.Add;
import com.example.room.common.advice.validatorGroup.Update;
import com.example.room.common.exception.SaleBusinessException;
import com.example.room.dao.UserDao;
import com.example.room.entity.UserInfo;
import com.example.room.service.UserService;
import com.example.room.utils.UUIDUtils;
import com.example.room.utils.common.AirUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONObject;
import org.springframework.validation.annotation.Validated;

import javax.validation.groups.Default;

/**
 * @author yangna
 * @date 2019/2/11
 */
@Service
public class UserServiceImpl implements UserService {
    private static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserDao userDao;

    /**
     * 获取用户信息
     *
     * @param userName
     * @return
     */
    @Override
    public UserInfo getUserInfo(@Validated String userName) {
        log.info("获取用户信息，请求参数为：{}", JSONObject.toJSONString(userName));
        return userDao.getUserInfo(userName);
    }

    /**
     * 注册用户信息
     *
     * @param userInfo
     * @return
     */
    @Override
    public int userRegister(@Validated({Add.class, Default.class}) UserInfo userInfo) {
        //判断用户名是否已经注册过
        UserInfo user = userDao.getUserInfo(userInfo.getUserName());
        if (AirUtils.hv(user)) {
            throw new SaleBusinessException("改手机号已经注册过");
        }
        userInfo.setId(UUIDUtils.getUUID());
        return userDao.userRegister(userInfo);
    }

    @Override
    public int userForgetPass(@Validated({Update.class, Default.class}) UserInfo userInfo) {
        UserInfo user = userDao.getUserInfo(userInfo.getUserName());
        if (!user.getUserPass().equals(userInfo.getOldUserPass())) {
            throw new SaleBusinessException("旧密码输入错误");
        }
        return userDao.userForgetPass(userInfo);
    }

}
