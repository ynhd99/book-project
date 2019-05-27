package com.example.room.dao;

import com.example.room.entity.RepairInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yangna
 * @date 2019/4/8
 */
@Mapper
@Repository
public interface RepairDao {
    /**
     * 新增维修信息
     *
     * @param repairInfo
     * @return
     */
    int addRepair(RepairInfo repairInfo);

    /**
     * 更新维修信息
     *
     * @param repairInfo
     * @return
     */
    int updateRepair(RepairInfo repairInfo);

    /**
     * 查询分页信息
     *
     * @param repairInfo
     * @return
     */
    List<RepairInfo> findRepairForPage(RepairInfo repairInfo);
    /**
     * 批量新增
     */
    int batchAddRepair(List<RepairInfo> repairInfos);
}
