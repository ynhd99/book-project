package com.example.room.controller;

import com.example.room.common.exception.SaleBusinessException;
import com.example.room.entity.AuthorityInfo;
import com.example.room.entity.RoleAuthority;
import com.example.room.entity.RoleInfo;
import com.example.room.entity.dto.MessageBody;
import com.example.room.service.AuthorityService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yangna
 * @date 2019/3/13
 */
@RestController
@RequestMapping("/authority")
public class AuthorityController {
    @Autowired
    private AuthorityService authorityService;

    /**
     * 获取权限树
     *
     * @return
     */
    @PostMapping("getAuthorityList")
    public MessageBody getAuthorityList(@RequestBody RoleAuthority roleAuthority) {
        List<AuthorityInfo> roleInfoList = authorityService.getAuthorityTree(roleAuthority.getId());
        return MessageBody.getMessageBody(true, roleInfoList);
    }

    @PostMapping("role")
    public MessageBody getRoleList(RoleInfo roleInfo) {
        PageInfo<RoleInfo> roleInfoList = authorityService.getRoleList(roleInfo);
        return MessageBody.getMessageBody(true, roleInfoList);
    }

    @PostMapping("addRole")
    public MessageBody addRole(@RequestBody RoleInfo roleInfo) {
        int num = authorityService.addRole(roleInfo);
        if (num <= 0) {
            throw new SaleBusinessException("新增失败");
        }
        return MessageBody.getMessageBody(true, "新增成功");
    }

    /**
     * 更新角色信息
     *
     * @param roleInfo
     * @return
     */
    @PostMapping("updateRole")
    public MessageBody updateRole(@RequestBody RoleInfo roleInfo) {
        int num = authorityService.updateRole(roleInfo);
        if (num <= 0) {
            throw new SaleBusinessException("更新失败");
        }
        return MessageBody.getMessageBody(true, "更新成功");
    }
}
