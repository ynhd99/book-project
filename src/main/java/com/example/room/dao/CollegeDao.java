package com.example.room.dao;

import com.example.room.entity.CollegeInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yangna
 * @date 2019/3/11
 */
@Mapper
@Repository
public interface CollegeDao {
    /**
     * 添加学院信息
     * @param collegeInfo
     * @return
     */
    int add(CollegeInfo collegeInfo);

    /**
     * 分页查询学院信息
     * @param collegeInfo
     * @return
     */
    List<CollegeInfo> findDataForPage(CollegeInfo collegeInfo);

    /**
     * 根据编码获取学院信息
     * @param code
     * @return
     */
    CollegeInfo getDataByCode(String code);
}