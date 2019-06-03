package com.example.room.dao;

import com.example.room.entity.ClassInfo;
import com.example.room.entity.RoomCategory;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yangna
 * @date 2019/2/25
 */
@Mapper
@Repository
public interface RoomCateDao {
    /**
     * 添加图书种类
     *
     * @param roomCategory
     * @return
     */
    int addRoomCate(RoomCategory roomCategory);

    /**
     * 根据编码获取分类信息
     *
     * @param cateCode
     * @return
     */
    int getBookCateByCode(String cateCode);
    /**
     * 获取最大编码
     *
     * @param roomCategory
     * @return
     */
    String getMaxCode(RoomCategory roomCategory);

    /**
     * 根据id获取编码
     * @param id
     * @return
     */
    String getCodeById(String id);

    /**
     * 获取分类列表
     * @param roomCategory
     * @return
     */
    List<RoomCategory> findDataForPage(RoomCategory roomCategory);

    /**
     * 可以筛选客户分类
     * @param roomCategory
     * @return
     */
    List<RoomCategory> getCategoryByQuery(RoomCategory roomCategory);

    /**
     * 修改图书分类
     * @param roomCategory
     * @return
     */
    int updateRoomCate(RoomCategory roomCategory);
    /**
     * 根据名称获取班级数据
     * @param nameList
     * @return
     */
    List<RoomCategory> getCateByName(List<String> nameList);

    /**
     * 根据父级查子级
     * @param id
     * @return
     */
    int findRoomByParent(String id);

    /**
     * 根据id删除宿舍分类
     * @param id
     * @return
     */
    int deleteRoomCate(String id);
}
