package com.example.room.service.impl;

import com.example.room.controller.UserController;
import com.example.room.dao.HealthDao;
import com.example.room.entity.HealthInfo;
import com.example.room.service.HealthService;
import com.example.room.utils.common.UUIDGenerator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author yangna
 * @date 2019/4/9
 */
@Service
public class HealthServiceImpl implements HealthService {
    @Autowired
    private HealthDao healthDao;
    @Autowired
    private UserController userController;

    /**
     * 添加卫生检查信息
     *
     * @param healthInfo
     * @return
     */
    @Override
    public int addHealth(HealthInfo healthInfo) {
        //封装参数
        healthInfo.setId(UUIDGenerator.getUUID());
        healthInfo.setCreateTime(new Date());
        healthInfo.setCreateUser(userController.getUser());
        healthInfo.setUpdateTime(new Date());
        healthInfo.setUpdateUser(userController.getUser());
        return healthDao.addHealth(healthInfo);
    }

    /**
     * 修改卫生检查情况
     *
     * @param healthInfo
     * @return
     */
    @Override
    public int updateHealth(HealthInfo healthInfo) {
        healthInfo.setUpdateTime(new Date());
        healthInfo.setUpdateUser(userController.getUser());
        return healthDao.updateHealth(healthInfo);
    }

    /**
     * 分页查询
     *
     * @param healthInfo
     * @return
     */
    @Override
    public PageInfo<HealthInfo> findHealthForPage(HealthInfo healthInfo) {
        PageHelper.startPage(healthInfo.getPage(), healthInfo.getSize());
        PageInfo<HealthInfo> pageInfo = new PageInfo<>(healthDao.findHealthForPage(healthInfo));
        return pageInfo;
    }
}
