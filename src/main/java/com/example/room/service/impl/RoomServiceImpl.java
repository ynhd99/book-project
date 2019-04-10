package com.example.room.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.room.common.advice.validatorGroup.UpdateStatus;
import com.example.room.common.exception.SaleBusinessException;
import com.example.room.controller.UserController;
import com.example.room.dao.RoomDao;
import com.example.room.dao.RoomDetailDao;
import com.example.room.dao.StudentDao;
import com.example.room.entity.RoomDetailInfo;
import com.example.room.entity.RoomEntity;
import com.example.room.service.RoomService;
import com.example.room.utils.UUIDUtils;
import com.example.room.utils.common.AirUtils;
import com.example.room.utils.common.UUIDGenerator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yangna
 * @date 2019/2/21
 */
@Service
public class RoomServiceImpl implements RoomService {
    private static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private RoomDao roomDao;
    @Autowired
    private UserController userController;
    @Autowired
    private RoomDetailDao roomDetailDao;
    @Autowired
    private StudentDao studentDao;

    /**
     * 新增宿舍信息
     *
     * @param roomEntity
     * @return
     */
    @Override
    public int addRoom(@Validated RoomEntity roomEntity) {
        //判断仓库编码是否已经存在
        RoomEntity deport = roomDao.getRoomByCode(roomEntity.getRoomCode());
        if (AirUtils.hv(deport)) {
            throw new SaleBusinessException("宿舍号已经存在,请重新输入");
        }
        roomEntity.setId(UUIDGenerator.getUUID());
        roomEntity.setCreateTime(new Date());
        roomEntity.setUpdateTime(new Date());
        roomEntity.setCreateUser(userController.getUser());
        roomEntity.setUpdateUser(userController.getUser());
        log.info("新增宿舍信息，请求参数为:{}", JSONObject.toJSONString(roomEntity));
        return roomDao.addRoom(roomEntity);
    }

    /**
     * 分页查询宿舍档案信息
     *
     * @param roomEntity
     * @return
     */
    @Override
    public PageInfo<RoomEntity> findDataForPage(RoomEntity roomEntity) {
        //分页查询
        PageHelper.startPage(roomEntity.getPage(), roomEntity.getSize());
        List<RoomEntity> roomEntityList = roomDao.findDataForPage(roomEntity);
        List<String> ids = roomEntityList.stream().map(e -> e.getId()).collect(Collectors.toList());
        if (AirUtils.hv(ids)) {
            List<RoomDetailInfo> roomDetailInfos = roomDetailDao.getRoomDetailList(ids);
            buildDetailList(roomEntityList, roomDetailInfos);
        }
        roomEntityList.forEach(e -> {
            e.setCurrentCount(e.getRoomDetailInfoList().size());
        });
        PageInfo<RoomEntity> pageInfo = new PageInfo<>(roomEntityList);
        return pageInfo;
    }

    /**
     * 删除宿舍
     *
     * @param roomEntity
     * @return
     */
    @Override
    public int deleteRoom(RoomEntity roomEntity) {
        //封装参数
        roomEntity.setUpdateTime(new Date());
        roomEntity.setCreateUser(userController.getUser());
//        List<RoomDetailInfo> roomDetailInfos = roomDetailDao.getDetailById(roomEntity.getId());
//        List<String> detailList = roomDetailInfos.stream().map(e -> e.getId()).collect(Collectors.toList());
//        roomDetailDao.deleteRoomDetails(detailList);
//        List<String> studentList = roomDetailInfos.stream().map(e -> e.getStudentId()).collect(Collectors.toList());
//        studentDao.deleteSettleFlags(studentList);
        return roomDao.deleteRoom(roomEntity);
    }

    /**
     * 修改宿舍信息
     *
     * @param roomEntity
     * @return
     */
    @Override
    public int updateRoom(RoomEntity roomEntity) {
        //封装参数
        roomEntity.setUpdateTime(new Date());
        roomEntity.setCreateUser(userController.getUser());
        if (roomEntity.getStatus() == 1) {
            List<RoomDetailInfo> roomDetailInfos = roomDetailDao.getDetailById(roomEntity.getId());
            List<String> detailList = roomDetailInfos.stream().map(e -> e.getId()).collect(Collectors.toList());
            List<String> studentList = roomDetailInfos.stream().map(e -> e.getStudentId()).collect(Collectors.toList());
            if (AirUtils.hv(detailList) && AirUtils.hv(studentList)) {
                studentDao.deleteSettleFlags(studentList);
                roomDetailDao.deleteRoomDetails(detailList);
            }
        }
        return roomDao.updateRoom(roomEntity);
    }

    /**
     * 获取宿舍列表
     *
     * @param roomEntity
     * @return
     */
    @Override
    public List<RoomEntity> findRoomList(RoomEntity roomEntity) {
        return roomDao.findRoomList(roomEntity);
    }

    /**
     * 封装参数
     *
     * @param roomEntities
     * @param roomDetailInfos
     */
    private void buildDetailList(List<RoomEntity> roomEntities, List<RoomDetailInfo> roomDetailInfos) {
        roomEntities.forEach(e -> {
            List<RoomDetailInfo> roomDetailInfoList = new ArrayList<>();
            roomDetailInfos.forEach(item -> {
                if (item.getRoomId().equals(e.getId())) {
                    roomDetailInfoList.add(item);
                }
            });
            e.setRoomDetailInfoList(roomDetailInfoList);
        });

    }
}
