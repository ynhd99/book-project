package com.example.room.service.impl;

import com.example.room.controller.UserController;
import com.example.room.dao.RecordDao;
import com.example.room.entity.RecordInfo;
import com.example.room.service.RecordService;
import com.example.room.utils.common.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author yangna
 * @date 2019/4/4
 */
@Service
public class RecordServiceImpl implements RecordService {
    @Autowired
    private RecordDao recordDao;
    @Autowired
    private UserController userController;

    /**
     * 新增公告信息
     *
     * @param recordInfo
     * @return
     */
    @Override
    public int addRecord(RecordInfo recordInfo) {
        //封装参数
        recordInfo.setId(UUIDGenerator.getUUID());
        recordInfo.setCreateTime(new Date());
        recordInfo.setCreateUser(userController.getUser());
        recordInfo.setUpdateTime(new Date());
        recordInfo.setUpdateUser(userController.getUser());
        return recordDao.addRecord(recordInfo);
    }

    /**
     * 更新公告信息
     *
     * @param recordInfo
     * @return
     */
    @Override
    public int updateRecord(RecordInfo recordInfo) {
        recordInfo.setUpdateTime(new Date());
        recordInfo.setUpdateUser(userController.getUser());
        return recordDao.updateRecord(recordInfo);
    }

    /**
     * 删除公告信息
     *
     * @param recordInfo
     * @return
     */
    @Override
    public int deleteRecord(RecordInfo recordInfo) {
        recordInfo.setUpdateTime(new Date());
        recordInfo.setUpdateUser(userController.getUser());
        return recordDao.deleteRecord(recordInfo);
    }

    /**
     * 查询公告信息
     *
     * @return
     */
    @Override
    public List<RecordInfo> findRecordList(RecordInfo recordInfo) {
        return recordDao.findRecordList(recordInfo);
    }
}
