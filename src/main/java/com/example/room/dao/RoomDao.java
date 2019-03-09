package com.example.room.dao;

import com.example.room.entity.RoomEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yangna
 * @date 2019/2/21
 */
@Mapper
@Repository
public interface RoomDao {
    /**
     * 新增仓库信息
     * @return
     */
    int addRoom(RoomEntity roomEntity);

    /**
     * 根据编码获取仓库信息
     * @param deportCode
     * @return
     */
    RoomEntity getRoomByCode(String deportCode);

    /**
     * 获取仓库档案
     * @param roomEntity
     * @return
     */
    List<RoomEntity> findDataForPage(RoomEntity roomEntity);

    /**
     * 更新仓库状态
     * @param roomEntity
     * @return
     */
    int updateStatus(RoomEntity roomEntity);

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
    int update(RoomEntity roomEntity);
}
