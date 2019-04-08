package com.example.room.service;

import com.example.room.entity.RepairInfo;
import com.github.pagehelper.PageInfo;


/**
 * @author yangna
 * @date 2019/4/8
 */
public interface RepairService {
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
    PageInfo<RepairInfo> findRepairForPage(RepairInfo repairInfo);
}
