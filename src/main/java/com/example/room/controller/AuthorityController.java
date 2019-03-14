package com.example.room.controller;

import com.example.room.entity.RoleInfo;
import com.example.room.entity.dto.MessageBody;
import com.example.room.service.AuthorityService;
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
    @PostMapping("role")
    public MessageBody getRoleList(){
        List<RoleInfo> roleInfoList = authorityService.getRoleList();
        return MessageBody.getMessageBody(true,roleInfoList);
    }
}
