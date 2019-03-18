package com.example.room.dao;

import com.example.room.entity.BuildingInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yangna
 * @date 2019/3/18
 */
@Mapper
@Repository
public interface BuildingDao {
    /**
     * 新增宿舍楼信息
     * @param buildingInfo
     * @return
     */
    int addBuilding(BuildingInfo buildingInfo);

    /**
     * 分页查询宿舍楼信息
     * @param buildingInfo
     * @return
     */
    List<BuildingInfo> getBuildingForPage(BuildingInfo buildingInfo);

    /**
     * 更新宿舍楼信息
     * @param buildingInfo
     * @return
     */
    int updateBuilding(BuildingInfo buildingInfo);

    /**
     * 删除宿舍楼信息
     * @param buildingInfo
     * @return
     */
    int deleteBuilding(BuildingInfo buildingInfo);

    /**
     * 根据编码获取宿舍楼西信息
     * @param code
     * @return
     */
    BuildingInfo getDataByCode(String code);
}
