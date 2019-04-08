package com.example.room.service.impl;

import com.example.room.controller.UserController;
import com.example.room.dao.RepairDao;
import com.example.room.entity.RepairInfo;
import com.example.room.service.RepairService;
import com.example.room.utils.common.UUIDGenerator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.Date;

/**
 * @author yangna
 * @date 2019/4/8
 */
@Service
public class RepairServiceImpl implements RepairService {
    @Autowired
    private RepairDao repairDao;
    @Autowired
    private UserController userController;

    /**
     * 新增维修信息
     *
     * @param repairInfo
     * @return
     */
    @Override
    public int addRepair(RepairInfo repairInfo) {
        //封装参数
        repairInfo.setId(UUIDGenerator.getUUID());
        repairInfo.setCreateTime(new Date());
        repairInfo.setCreateUser(userController.getUser());
        repairInfo.setUpdateTime(new Date());
        repairInfo.setUpdateUser(userController.getUser());
        return repairDao.addRepair(repairInfo);
    }

    /**
     * 更新维修信息
     *
     * @param repairInfo
     * @return
     */
    @Override
    public int updateRepair(RepairInfo repairInfo) {
        repairInfo.setUpdateTime(new Date());
        repairInfo.setUpdateUser(userController.getUser());
        return repairDao.updateRepair(repairInfo);
    }

    /**
     * 分页查询维修信息
     *
     * @param repairInfo
     * @return
     */
    @Override
    public PageInfo<RepairInfo> findRepairForPage(RepairInfo repairInfo) {
        PageHelper.startPage(repairInfo.getPage(), repairInfo.getSize());
        PageInfo<RepairInfo> pageInfo = new PageInfo<>(repairDao.findRepairForPage(repairInfo));
        return pageInfo;
    }
}
