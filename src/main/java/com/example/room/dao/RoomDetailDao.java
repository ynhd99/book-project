package com.example.room.dao;

import com.example.room.entity.RoomDetailInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

/**
 * @author yangna
 * @date 2019/4/10
 */
@Mapper
@Repository
public interface RoomDetailDao {
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

    /**
     * 查询详情列表
     *
     * @param ids
     * @return
     */
    List<RoomDetailInfo> getRoomDetailList(List<String> ids);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    int deleteRoomDetails(@Param("list") List<String> ids, @Param("date")Date date);
    List<RoomDetailInfo> getDetailById(String id);
    List<RoomDetailInfo> getRoomDetailForPage(RoomDetailInfo roomDetailInfo);
}

