package com.example.room.service.impl;

import com.example.room.common.exception.SaleBusinessException;
import com.example.room.controller.UserController;
import com.example.room.dao.TeacherDao;
import com.example.room.dao.UserDao;
import com.example.room.entity.TeacherInfo;
import com.example.room.entity.UserInfo;
import com.example.room.service.TeacherService;
import com.example.room.service.UserService;
import com.example.room.utils.common.AirUtils;
import com.example.room.utils.common.UUIDGenerator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author yangna
 * @date 2019/3/13
 */
@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private UserController userController;
    @Autowired
    private TeacherDao teacherDao;
    @Autowired
    private UserService userService;
    @Autowired
    private UserDao userDao;

    /**
     * 分页查询宿管员信息
     *
     * @param teacherInfo
     * @return
     */
    @Override
    public PageInfo<TeacherInfo> getTeacherForPage(TeacherInfo teacherInfo) {
        PageHelper.startPage(teacherInfo.getPage(), teacherInfo.getSize());
        PageInfo<TeacherInfo> TeacherInfoPageInfo = new PageInfo<TeacherInfo>(teacherDao.getTeacherForPage(teacherInfo));
        return TeacherInfoPageInfo;
    }

    /**
     * 编辑宿管员信息
     *
     * @param teacherInfo
     * @return
     */
    @Transactional
    @Override
    public int updateTeacher(TeacherInfo teacherInfo) {
        teacherInfo.setUpdateTime(new Date());
        teacherInfo.setUpdateUser(userController.getUser());
        String userName = teacherDao.getDataById(teacherInfo.getId());
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(userName);
        userInfo.setFullName(teacherInfo.getTeacherName());
        userInfo.setRoleId(teacherInfo.getRoleId());
        userInfo.setUpdateUser(userController.getUser());
        userService.updateUser(userInfo);
        return teacherDao.updateTeacher(teacherInfo);
    }

    /**
     * 删除宿管员信息
     *
     * @param teacherInfo
     * @return
     */
    @Transactional
    @Override
    public int deleteTeacher(TeacherInfo teacherInfo) {
        teacherInfo.setUpdateTime(new Date());
        teacherInfo.setUpdateUser(userController.getUser());
        //批量删除老师列表
        List<String> list = teacherInfo.getDeleteTeacherList();
        if(AirUtils.hv(list)){
            teacherDao.batchDeleteUser(list);
            return teacherDao.batchDelete(list);
        }
        //删除用户表
        String userName = teacherDao.getDataById(teacherInfo.getId());
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(userName);
        userInfo.setUpdateUser(userController.getUser());
        userService.deleteUser(userInfo);
        return teacherDao.deleteTeacher(teacherInfo);
    }

    /**
     * 新增宿管员信息
     *
     * @param teacherInfo
     * @return
     */
    @Transactional
    @Override
    public int addTeacher(TeacherInfo teacherInfo) {
        if (AirUtils.hv(userDao.getDataByCode(teacherInfo.getTeacherCode()))) {
            throw new SaleBusinessException("编码已经存在");
        }
        teacherInfo.setId(UUIDGenerator.getUUID());
        teacherInfo.setCreateTime(new Date());
        teacherInfo.setCreateUser(userController.getUser());
        teacherInfo.setUpdateTime(new Date());
        teacherInfo.setUpdateUser(userController.getUser());
        //同时将用户名，账号，角色写入user库
        UserInfo userInfo = new UserInfo();
        userInfo.setFullName(teacherInfo.getTeacherName());
        userInfo.setUserName(teacherInfo.getTeacherCode());
        userInfo.setUserPass("SJ123456");
        userInfo.setRoleId(teacherInfo.getRoleId());
        userInfo.setCreateUser(userController.getUser());
        userInfo.setUpdateUser(userController.getUser());
        userService.userRegister(userInfo);
        return teacherDao.addTeacher(teacherInfo);
    }
}
