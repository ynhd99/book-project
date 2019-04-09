package com.example.room.dao;

import com.example.room.entity.HealthInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yangna
 * @date 2019/4/9
 */
@Mapper
@Repository
public interface HealthDao {
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
    List<HealthInfo> findHealthForPage(HealthInfo healthInfo);
}
