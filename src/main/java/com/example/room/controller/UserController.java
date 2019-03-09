package com.example.room.controller;

import com.example.room.common.advice.validatorGroup.Add;
import com.example.room.common.advice.validatorGroup.Update;
import com.example.room.common.exception.SaleBusinessException;
import com.example.room.entity.UserInfo;
import com.example.room.entity.dto.MessageBody;
import com.example.room.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.groups.Default;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yangna
 * @date 2019/2/11
 */
@RequestMapping("/user")
@RestController
public class UserController {
    private static Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @PostMapping("userLogin")
    MessageBody getUserInfo(@Validated @RequestBody UserInfo userInfo) {
        Map<String, String> map = new HashMap<>();
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userInfo.getUserName(), userInfo.getUserPass());
        try {
            //登录
            subject.login(token);
            String sessionId = (String) subject.getSession().getId();
            map.put("sessionId", sessionId);
        } catch (IncorrectCredentialsException e) {
            throw new SaleBusinessException("密码错误,请重新输入密码");
        } catch (AuthenticationException e) {
            throw new SaleBusinessException("改用户不存在");
        } catch (Exception e) {
            throw new SaleBusinessException("系统错误");
        }
        return MessageBody.getMessageBody(true, map);
    }

    @PostMapping("userRegister")
    MessageBody userRegister(@Validated({Add.class, Default.class}) @RequestBody UserInfo userInfo) {
        userInfo.setCreateTime(new Date());
        userInfo.setUpdateTime(new Date());
        int num = userService.userRegister(userInfo);
        if (num <= 0) {
            throw new SaleBusinessException("注册失败");
        }
        return MessageBody.getMessageBody(true, "注册成功");
    }

    @PostMapping("userForgetPass")
    public MessageBody userForgetPass(@Validated({Update.class, Default.class}) @RequestBody UserInfo userInfo) {
        if (userService.userForgetPass(userInfo) <= 0) {
            throw new SaleBusinessException("重置失败");
        }
        return MessageBody.getMessageBody(true, "修改成功");
    }

    @RequestMapping(value = "unAnth")
    @ResponseBody
    public MessageBody unAnth() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        MessageBody map = new MessageBody();
        map.setCode("401");
        map.setErrorInfo("token验证失败");
        map.setMessage("token验证失败");
        return map;
    }

    @RequestMapping(value = "userLogout")
    @ResponseBody
    public MessageBody userLogout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        MessageBody map = new MessageBody();
        map.setCode("304");
        return map;
    }
}
