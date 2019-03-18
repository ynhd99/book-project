package com.example.room.service;

import com.example.room.entity.BuildingInfo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author yangna
 * @date 2019/3/18
 */
public interface BuildingService {
    /**
     * 新增宿舍楼信息
     *
     * @param buildingInfo
     * @return
     */
    int addBuilding(BuildingInfo buildingInfo);

    /**
     * 分页查询宿舍楼信息
     *
     * @param buildingInfo
     * @return
     */
    PageInfo<BuildingInfo> getBuildingForPage(BuildingInfo buildingInfo);

    /**
     * 更新宿舍楼信息
     *
     * @param buildingInfo
     * @return
     */
    int updateBuilding(BuildingInfo buildingInfo);

    /**
     * 删除宿舍楼信息
     *
     * @param buildingInfo
     * @return
     */
    int deleteBuilding(BuildingInfo buildingInfo);
}
