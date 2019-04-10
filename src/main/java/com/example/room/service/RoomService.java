package com.example.room.service;

import com.example.room.entity.RoomEntity;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author yangna
 * @date 2019/2/21
 */
public interface RoomService {
    /**
     * 新增仓库信息
     * @param roomEntity
     * @return
     */
    int addRoom(RoomEntity roomEntity);
    /**
     * 获取仓库档案
     * @param roomEntity
     * @return
     */
    PageInfo<RoomEntity> findDataForPage(RoomEntity roomEntity);
    /**
     * 删除仓库
     * @param roomEntity
     * @return
     */
    int deleteRoom(RoomEntity roomEntity);
    /**
     * 修改仓库信息
     * @param roomEntity
     * @return
     */
    int updateRoom(RoomEntity roomEntity);

    /**
     * 查询宿舍列表
     * @param roomEntity
     * @return
     */
    List<RoomEntity> findRoomList(RoomEntity roomEntity);
}
