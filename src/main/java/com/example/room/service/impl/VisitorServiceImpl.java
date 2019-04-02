package com.example.room.service.impl;

import com.example.room.controller.UserController;
import com.example.room.dao.VisitorDao;
import com.example.room.entity.VisitorInfo;
import com.example.room.service.VisitorService;
import com.example.room.utils.common.UUIDGenerator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author yangna
 * @date 2019/4/2
 */
@Service
public class VisitorServiceImpl implements VisitorService {
    @Autowired
    private VisitorDao visitorDao;
    @Autowired
    private UserController userController;

    /**
     * 新增访问者信息
     *
     * @param visitorInfo
     * @return
     */
    @Override
    public int addVisitor(VisitorInfo visitorInfo) {
        //封装参数
        visitorInfo.setId(UUIDGenerator.getUUID());
        visitorInfo.setCreateTime(new Date());
        visitorInfo.setCreateUser(userController.getUser());
        visitorInfo.setUpdateTime(new Date());
        visitorInfo.setUpdateUser(userController.getUser());
        return visitorDao.addVisitor(visitorInfo);
    }

    /**
     * 更新访问者信息
     *
     * @param visitorInfo
     * @return
     */
    @Override
    public int updateVisitor(VisitorInfo visitorInfo) {
        visitorInfo.setUpdateTime(new Date());
        visitorInfo.setUpdateUser(userController.getUser());
        return visitorDao.updateVisitor(visitorInfo);
    }

    /**
     * 分页查询访问者信息
     *
     * @param visitorInfo
     * @return
     */
    @Override
    public PageInfo<VisitorInfo> findVisitorForPage(VisitorInfo visitorInfo) {
        //分页处理
        PageHelper.startPage(visitorInfo.getPage(), visitorInfo.getSize());
        PageInfo pageInfo = new PageInfo(visitorDao.findVisitorForPage(visitorInfo));
        return pageInfo;
    }
}
