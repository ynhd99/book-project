package com.example.room.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.room.common.advice.validatorGroup.UpdateStatus;
import com.example.room.common.exception.SaleBusinessException;
import com.example.room.dao.RoomDao;
import com.example.room.entity.RoomEntity;
import com.example.room.service.RoomService;
import com.example.room.utils.UUIDUtils;
import com.example.room.utils.common.AirUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Date;

/**
 * @author yangna
 * @date 2019/2/21
 */
@Service
public class RoomServiceImpl implements RoomService {
    private static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private RoomDao roomDao;

    /**
     * 新增仓库信息
     *
     * @param roomEntity
     * @return
     */
    @Override
    public int addRoom(@Validated RoomEntity roomEntity) {
        //判断仓库编码是否已经存在
        RoomEntity deport = roomDao.getRoomByCode(roomEntity.getRoomCode());
        if (AirUtils.hv(deport)) {
            throw new SaleBusinessException("宿舍号已经存在");
        }
        roomEntity.setCreateTime(new Date());
        roomEntity.setUpdateTime(new Date());
        roomEntity.setId(UUIDUtils.getUUID());
        log.info("新增仓库信息，请求参数为:{}", JSONObject.toJSONString(roomEntity));
        return roomDao.addRoom(roomEntity);
    }

    /**
     * 分页查询仓库档案信息
     *
     * @param roomEntity
     * @return
     */
    @Override
    public PageInfo<RoomEntity> findDataForPage(RoomEntity roomEntity) {
        //分页查询
        PageHelper.startPage(roomEntity.getPage(), roomEntity.getSize());
        PageInfo<RoomEntity> pageInfo = new PageInfo<>(roomDao.findDataForPage(roomEntity));
        return pageInfo;
    }

    /**
     * 更新仓库状态
     *
     * @param roomEntity
     * @return
     */
    @Override
    public int updateStatus(@Validated({UpdateStatus.class}) RoomEntity roomEntity) {
        roomEntity.setUpdateTime(new Date());
        return roomDao.updateStatus(roomEntity);
    }

    /**
     * 删除仓库
     *
     * @param roomEntity
     * @return
     */
    @Override
    public int deleteRoom(RoomEntity roomEntity) {
        roomEntity.setUpdateTime(new Date());
        return roomDao.deleteRoom(roomEntity);
    }

    /**
     * 修改仓库信息
     *
     * @param roomEntity
     * @return
     */
    @Override
    public int update(RoomEntity roomEntity) {
        roomEntity.setUpdateTime(new Date());
        return roomDao.update(roomEntity);
    }
}
