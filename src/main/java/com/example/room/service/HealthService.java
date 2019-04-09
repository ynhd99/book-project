package com.example.room.service;

import com.example.room.entity.HealthInfo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author yangna
 * @date 2019/4/9
 */
public interface HealthService {
    /**
     * 新增检查信息
     *
     * @param healthInfo
     * @return
     */
    int addHealth(HealthInfo healthInfo);

    /**
     * 修改卫生检查信息
     *
     * @param healthInfo
     * @return
     */
    int updateHealth(HealthInfo healthInfo);

    /**
     * 分页查询卫生检查情况
     *
     * @param healthInfo
     * @return
     */
    PageInfo<HealthInfo> findHealthForPage(HealthInfo healthInfo);
}
