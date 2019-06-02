package com.example.room.service.impl;

import com.example.room.common.exception.SaleBusinessException;
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
    public List<AuthorityInfo> getAuthorityTree() {
        List<AuthorityInfo> authorityInfos = authorityDao.getAuthorityTree();
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
     * 查询角色信息和关系信息
     *
     * @param roleInfo
     * @return
     */
    @Override
    public PageInfo<RoleInfo> getRoleList(RoleInfo roleInfo) {
        PageHelper.startPage(roleInfo.getPage(), roleInfo.getSize());
        List<RoleInfo> roleInfoList = authorityDao.getRoleList(roleInfo);
        List<String> ids = roleInfoList.stream().map(e -> e.getId()).collect(Collectors.toList());
        //获取角色的人数
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
            List<RoleAuthority> roleAuthorities = authorityDao.getRoleAuthorityList(ids);
            roleInfoList.forEach(item -> {
                List<String> idss = new ArrayList<>();
                roleAuthorities.forEach(index -> {
                    if (index.getRoleId().equals(item.getId())) {
                        idss.add(index.getAuthorityId());
                    }
                });
                item.setNodeIdList(idss);
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
            if(AirUtils.hv(authorityDao.getRoleByCode(roleInfo.getRoleCode()))){
                throw new SaleBusinessException("角色编码已经存在");
            }
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
        //如果进行了权限的修改再进行删除原来的新增新的
        if (AirUtils.hv(roleInfo.getRoleAuthorityList())) {
            authorityDao.batchDeleteAuthority(roleInfo.getId());
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
        return authorityDao.updateRole(roleInfo);
    }

    /**
     * 删除角色信息
     *
     * @param roleInfo
     * @return
     */
    @Override
    public int deleteRole(RoleInfo roleInfo) {
        //如果存在权限信息则删除权限信息
        if (AirUtils.hv(roleInfo.getNodeIdList())) {
            authorityDao.batchDeleteAuthority(roleInfo.getId());
        }
        return authorityDao.deleteRole(roleInfo);
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
