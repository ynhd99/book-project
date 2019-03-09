package com.example.room.common.shiroConfig;

import com.example.room.entity.UserInfo;
import com.example.room.service.UserService;
import com.example.room.utils.common.AirUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author yangna
 * @date 2019/2/20
 */
public class MyShiroRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String userName = (String) token.getPrincipal();
        //获取到用户信息
        UserInfo userInfo = userService.getUserInfo(userName);
        if (!AirUtils.hv(userInfo)) {
            return null;
        }
        //判断密码是否正确
        SimpleAuthenticationInfo authorizationInfo = new SimpleAuthenticationInfo(userInfo, userInfo.getUserPass(), null, getName());
        return authorizationInfo;
    }
}
