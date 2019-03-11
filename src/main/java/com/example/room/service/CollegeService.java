package com.example.room.service;

import com.example.room.entity.CollegeInfo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author yangna
 * @date 2019/3/11
 */
public interface CollegeService {
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
    PageInfo<CollegeInfo> findDataForPage(CollegeInfo collegeInfo);
}