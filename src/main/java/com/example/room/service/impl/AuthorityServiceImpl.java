package com.example.room.service.impl;

import com.example.room.controller.UserController;
import com.example.room.dao.AuthorityDao;
import com.example.room.dao.UserDao;
import com.example.room.entity.AuthorityInfo;
import com.example.room.entity.RoleAuthority;
import com.example.room.entity.RoleInfo;
import com.example.room.service.AuthorityService;
import com.example.room.utils.common.AirUtils;
import com.example.room.utils.common.UUIDGenerator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yangna
 * @date 2019/3/13
 */
@Service
public class AuthorityServiceImpl implements AuthorityService {
    @Autowired
    private AuthorityDao authorityDao;
    @Autowired
    private UserController userController;
    @Autowired
    private UserDao userDao;

    /**
     * 查询权限树
     *
     * @return
     */
    @Override
    public List<AuthorityInfo> getAuthorityTree(String id) {
        List<AuthorityInfo> authorityInfos = authorityDao.getAuthorityTree();
        if (AirUtils.hv(id)) {
            List<AuthorityInfo> presentList = authorityDao.getAuthorityTreeById(id);
            authorityInfos.forEach(item -> {
                presentList.forEach(index -> {
                    if (item.getId().equals(index.getId())) {
                        item.setIsSelect(1);
                    }
                });
            });
        }
        List<AuthorityInfo> oneList = new ArrayList<>();
        //生成父亲树
        authorityInfos.forEach(item -> {
            if (item.getParentId().equals("-1")) {
                oneList.add(item);
            }
        });
        //封装子树
        buildDataTree(oneList, authorityInfos);
        return oneList;
    }

    /**
     * 查询角色信息
     *
     * @param roleInfo
     * @return
     */
    @Override
    public PageInfo<RoleInfo> getRoleList(RoleInfo roleInfo) {
        PageHelper.startPage(roleInfo.getPage(), roleInfo.getSize());
        List<RoleInfo> roleInfoList = authorityDao.getRoleList(roleInfo);
        List<String> ids = roleInfoList.stream().map(e -> e.getId()).collect(Collectors.toList());
        if (AirUtils.hv(ids)) {
            List<String> userInfos = userDao.getUserList(ids);
            roleInfoList.forEach(e -> {
                List<String> idss = new ArrayList<>();
                userInfos.forEach(item -> {
                    if (item.equals(e.getId())) {
                        idss.add(item);
                    }
                });
                e.setCount(idss.size());
            });
        }
        PageInfo<RoleInfo> pageInfo = new PageInfo<>(roleInfoList);
        return pageInfo;
    }

    /**
     * 新增角色
     *
     * @param roleInfo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addRole(RoleInfo roleInfo) {
        //f封装参数
        roleInfo.setId(UUIDGenerator.getUUID());
        roleInfo.setCreateTime(new Date());
        roleInfo.setCreateUser(userController.getUser());
        roleInfo.setUpdateTime(new Date());
        roleInfo.setUpdateUser(userController.getUser());
        if (AirUtils.hv(roleInfo.getRoleAuthorityList())) {
            roleInfo.getRoleAuthorityList().forEach(item -> {
                item.setId(UUIDGenerator.getUUID());
                item.setRoleId(roleInfo.getId());
                item.setCreateTime(new Date());
                item.setCreateUser(userController.getUser());
                item.setUpdateTime(new Date());
                item.setUpdateUser(userController.getUser());
            });
            authorityDao.addRoleAuthority(roleInfo.getRoleAuthorityList());
        }
        return authorityDao.addRole(roleInfo);
    }

    /**
     * 更新角色信息
     *
     * @param roleInfo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateRole(RoleInfo roleInfo) {
        authorityDao.batchDeleteAuthority(roleInfo.getId());
        authorityDao.addRoleAuthority(roleInfo.getRoleAuthorityList());
        return authorityDao.updateRole(roleInfo);
    }

    /**
     * 循环封装子树
     *
     * @param list
     */
    void buildDataTree(List<AuthorityInfo> list, List<AuthorityInfo> allList) {
        list.forEach(item -> {
            List<AuthorityInfo> center = new ArrayList<>();
            allList.forEach(index -> {
                if (index.getParentId().equals(item.getId())) {
                    center.add(index);
                }
            });
            item.setChildren(center);
            if (AirUtils.hv(center)) {
                buildDataTree(center, allList);
            }
        });
    }
}
