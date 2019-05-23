package com.example.room.service;

import com.example.room.entity.CollegeInfo;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletResponse;
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
    /**
     * 修改学院状态
     * @param collegeInfo
     * @return
     */
    int updateStatus(CollegeInfo collegeInfo);
    /**
     * 删除学院
     * @param collegeInfo
     * @return
     */
    int delete(CollegeInfo collegeInfo);
    /**
     * 修改学院信息
     * @param collegeInfo
     * @return
     */
    int update(CollegeInfo collegeInfo);
    /**
     * 导出学院信息
     *
     * @return
     */
    void exportCollege(HttpServletResponse response);
}
