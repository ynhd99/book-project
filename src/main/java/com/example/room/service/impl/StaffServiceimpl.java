package com.example.room.service.impl;

import com.example.room.common.exception.SaleBusinessException;
import com.example.room.controller.UserController;
import com.example.room.dao.StaffDao;
import com.example.room.entity.StaffInfo;
import com.example.room.entity.UserInfo;
import com.example.room.service.StaffService;
import com.example.room.service.UserService;
import com.example.room.utils.common.AirUtils;
import com.example.room.utils.common.UUIDGenerator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author yangna
 * @date 2019/3/13
 */
@Service
public class StaffServiceimpl implements StaffService {
    @Autowired
    private UserController userController;
    @Autowired
    private StaffDao staffDao;
    @Autowired
    private UserService userService;

    /**
     * 分页查询宿管员信息
     *
     * @param staffInfo
     * @return
     */
    @Override
    public PageInfo<StaffInfo> getStaffForPage(StaffInfo staffInfo) {
        PageHelper.startPage(staffInfo.getPage(), staffInfo.getSize());
        PageInfo<StaffInfo> staffInfoPageInfo = new PageInfo<StaffInfo>(staffDao.getStaffForPage(staffInfo));
        return staffInfoPageInfo;
    }

    /**
     * 编辑宿管员信息
     *
     * @param staffInfo
     * @return
     */
    @Transactional
    @Override
    public int updateStaff(StaffInfo staffInfo) {
        staffInfo.setUpdateTime(new Date());
        staffInfo.setUpdateUser(userController.getUser());
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(staffInfo.getStaffName());
        userInfo.setRoleId(staffInfo.getRoleId());
        userInfo.setUpdateUser(userController.getUser());
        userService.updateUser(userInfo);
        return staffDao.updateStaff(staffInfo);
    }

    /**
     * 删除宿管员信息
     *
     * @param staffInfo
     * @return
     */
    @Transactional
    @Override
    public int deleteStaff(StaffInfo staffInfo) {
        staffInfo.setUpdateTime(new Date());
        staffInfo.setUpdateUser(userController.getUser());
        //删除用户表
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(staffInfo.getStaffName());
        userInfo.setUpdateUser(userController.getUser());
        userService.deleteUser(userInfo);
        return staffDao.deleteStaff(staffInfo);
    }

    /**
     * 新增宿管员信息
     *
     * @param staffInfo
     * @return
     */
    @Transactional
    @Override
    public int addStaff(StaffInfo staffInfo) {
        if (AirUtils.hv(staffDao.getDataByCode(staffInfo.getStaffCode()))) {
            throw new SaleBusinessException("编码已经存在");
        }
        staffInfo.setId(UUIDGenerator.getUUID());
        staffInfo.setCreateTime(new Date());
        staffInfo.setCreateUser(userController.getUser());
        staffInfo.setUpdateTime(new Date());
        staffInfo.setUpdateUser(userController.getUser());
        //同时将用户名，账号，角色写入user库
        UserInfo userInfo = new UserInfo();
        userInfo.setFullName(staffInfo.getStaffName());
        userInfo.setUserName(staffInfo.getStaffCode());
        userInfo.setUserPass("SJ123456");
        userInfo.setRoleId(staffInfo.getRoleId());
        userInfo.setCreateUser(userController.getUser());
        userInfo.setUpdateUser(userController.getUser());
        userService.userRegister(userInfo);
        return staffDao.addStaff(staffInfo);
    }
}
