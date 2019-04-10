package com.example.room.service;

import com.example.room.entity.RoomDetailInfo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author yangna
 * @date 2019/4/10
 */
public interface RoomDetailService {
    /**
     * 新增详情表
     *
     * @param roomDetailInfo
     * @return
     */
    int addRoomDetail(List<RoomDetailInfo> roomDetailInfo);

    /**
     * 删除详情表
     *
     * @param roomDetailInfo
     * @return
     */
    int deleteRoomDetail(RoomDetailInfo roomDetailInfo);
    PageInfo<RoomDetailInfo> getRoomDetailForPage(RoomDetailInfo roomDetailInfo);
}
