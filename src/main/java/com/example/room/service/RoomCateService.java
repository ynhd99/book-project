package com.example.room.service;

import com.example.room.entity.RoomCategory;

import java.util.List;
import java.util.Map;

/**
 * @author yangna
 * @date 2019/3/5
 */
public interface RoomCateService {
    /**
     * 新增
     * @param roomCategory
     * @return
     */
    public int addRoomCate(RoomCategory roomCategory);
    /**
     * 获取到最大编码
     *
     * @param roomCategory
     * @return
     */
    public Map<String, String> getMaxCode(RoomCategory roomCategory);
    /**
     * 分页查询分类信息
     *
     * @param roomCategory
     * @return
     */
    public List<RoomCategory> findDataForPage(RoomCategory roomCategory);
    /**
     * 修改图书分类信息
     * @param roomCategory
     * @return
     */
    public int updateRoomCate(RoomCategory roomCategory);
}
