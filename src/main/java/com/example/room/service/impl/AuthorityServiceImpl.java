package com.example.room.service.impl;

import com.example.room.dao.AuthorityDao;
import com.example.room.entity.AuthorityInfo;
import com.example.room.entity.RoleInfo;
import com.example.room.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yangna
 * @date 2019/3/13
 */
@Service
public class AuthorityServiceImpl implements AuthorityService {
    @Autowired
    private AuthorityDao authorityDao;
    @Override
    public List<AuthorityInfo> getAuthorityTree() {
        return null;
    }

    @Override
    public List<RoleInfo> getRoleList() {
        return authorityDao.getRoleList();
    }
}
