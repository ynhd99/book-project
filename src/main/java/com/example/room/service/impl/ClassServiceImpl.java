package com.example.room.service.impl;

import com.example.room.common.exception.SaleBusinessException;
import com.example.room.controller.UserController;
import com.example.room.entity.ClassInfo;
import com.example.room.service.ClassService;
import com.example.room.dao.ClassDao;
import com.alibaba.fastjson.JSONObject;
import com.example.room.utils.common.AirUtils;
import com.example.room.utils.common.UUIDGenerator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 班级管理接口实现
 *
 * @author yangna
 * @Date 2019-03-12 03:20:47
 */
@Service
public class ClassServiceImpl implements ClassService {

    private static Logger logger = LoggerFactory.getLogger(ClassServiceImpl.class);
    @Autowired
    private ClassDao classDao;
    @Autowired
    private UserController userController;

    /**
     * 根据条件查询班级管理数据（不分页）
     *
     * @param classInfo 查询实体对象
     * @return List集合
     */
    @Override
    public List<ClassInfo> findAllClass(ClassInfo classInfo) {
        logger.info("根据条件查询班级管理数据（不分页），请求参数为：{}", JSONObject.toJSONString(classInfo));
        return classDao.getClassList(classInfo);
    }

    /**
     * 根据条件查询班级管理数据（含有分页）
     *
     * @param classInfo 查询实体对象
     * @return 分页对象
     */
    @Override
    public PageInfo<ClassInfo> findClassForPage(ClassInfo classInfo) {
        logger.info("根据条件查询班级管理数据（含有分页），请求参数为：{}", JSONObject.toJSONString(classInfo));
        PageHelper.startPage(classInfo.getPage(), classInfo.getSize());
        PageInfo<ClassInfo> pageInfo = new PageInfo<>(classDao.getClassList(classInfo));
        return pageInfo;
    }

    /**
     * 通过ID查询单条班级管理数据
     *
     * @param id
     * @param tenantId 商户id
     * @return 班级管理对象
     */
    @Override
    public ClassInfo findClassById(String id, String tenantId) {
        logger.info("通过ID查询单条班级管理数据，请求参数为：{}", JSONObject.toJSONString(id));
        ClassInfo classInfo = classDao.getClass(id, tenantId);
        return classInfo;
    }

    /**
     * 新增班级管理数据
     *
     * @param classInfo 班级管理实体对象
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addClass(ClassInfo classInfo) {
        //判断编码是否已经存在
        if (AirUtils.hv(classDao.getClassByCode(classInfo.getClassCode()))) {
            throw new SaleBusinessException("该班级编码已经存在");
        }
        logger.info("新增班级管理数据，请求参数为：{}", JSONObject.toJSONString(classInfo));
        classInfo.setId(UUIDGenerator.getUUID());
        classInfo.setCreateTime(new Date());
        classInfo.setCreateUser(userController.getUser());
        classInfo.setUpdateTime(new Date());
        classInfo.setUpdateUser(userController.getUser());
        if (classDao.createClass(classInfo) <= 0) {
            logger.error("新增班级管理数据失败，请求参数为：{}", JSONObject.toJSONString(classInfo));
            return false;
        }
        return true;
    }

    /**
     * 编辑班级管理数据
     *
     * @param classInfo 班级管理实体对象
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateClass(ClassInfo classInfo) {
        //封装参数
        classInfo.setUpdateTime(new Date());
        classInfo.setUpdateUser(userController.getUser());
        logger.info("编辑班级管理数据，请求参数为：{}", JSONObject.toJSONString(classInfo));
        if (classDao.updateClass(classInfo) <= 0) {
            logger.error("编辑班级管理数据失败，请求参数为：{}", JSONObject.toJSONString(classInfo));
            return false;
        }
        return true;
    }

    /**
     * 删除班级管理数据
     *
     * @param classInfo 班级管理实体对象
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteClassById(ClassInfo classInfo) {
        classInfo.setUpdateTime(new Date());
        classInfo.setUpdateUser(userController.getUser());
        logger.info("删除班级管理数据，请求参数为：{}", JSONObject.toJSONString(classInfo));
        if (classDao.deleteClass(classInfo) <= 0) {
            logger.error("删除班级管理数据失败，请求参数为：{}", JSONObject.toJSONString(classInfo));
            return false;
        }
        return true;
    }
}
