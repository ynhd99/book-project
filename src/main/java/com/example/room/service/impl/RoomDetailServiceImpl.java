package com.example.room.service.impl;

import com.example.room.controller.UserController;
import com.example.room.dao.RoomDetailDao;
import com.example.room.dao.StudentDao;
import com.example.room.entity.RoomDetailInfo;
import com.example.room.service.RoomDetailService;
import com.example.room.utils.common.AirUtils;
import com.example.room.utils.common.UUIDGenerator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yangna
 * @date 2019/4/10
 */
@Service
public class RoomDetailServiceImpl implements RoomDetailService {
    @Autowired
    private RoomDetailDao roomDetailDao;
    @Autowired
    private UserController userController;
    @Autowired
    private StudentDao studentDao;

    /**
     * 新增住宿人员
     *
     * @param roomDetailInfo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addRoomDetail(List<RoomDetailInfo> roomDetailInfo) {
        roomDetailInfo.forEach(e -> {
            e.setId(UUIDGenerator.getUUID());
            e.setCreateTime(new Date());
            e.setCreateUser(userController.getUser());
            e.setUpdateTime(new Date());
            e.setUpdateUser(userController.getUser());
        });
        List<String> ids = roomDetailInfo.stream().map(e -> e.getStudentId()).collect(Collectors.toList());
        if (AirUtils.hv(ids)) {
            studentDao.addSettleFlag(ids);
        }
        return roomDetailDao.addRoomDetail(roomDetailInfo);
    }

    /**
     * 删除住宿人员
     *
     * @param roomDetailInfo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteRoomDetail(RoomDetailInfo roomDetailInfo) {
        roomDetailInfo.setUpdateTime(new Date());
        roomDetailInfo.setUpdateUser(userController.getUser());
        roomDetailInfo.setDeleteDate(new Date());
        studentDao.deleteSettleFlag(roomDetailInfo.getStudentId());
        return roomDetailDao.deleteRoomDetail(roomDetailInfo);
    }

    @Override
    public PageInfo<RoomDetailInfo> getRoomDetailForPage(RoomDetailInfo roomDetailInfo) {
        PageHelper.startPage(roomDetailInfo.getPage(), roomDetailInfo.getSize());
        PageInfo<RoomDetailInfo> pageInfo = new PageInfo<>(roomDetailDao.getRoomDetailForPage(roomDetailInfo));
        return pageInfo;
    }
}
