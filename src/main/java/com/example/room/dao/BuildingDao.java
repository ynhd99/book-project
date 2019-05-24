package com.example.room.dao;

import com.example.room.entity.BuildingInfo;
import com.example.room.entity.ClassInfo;
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
    /**
     * 根据编码批量查询
     * @param codeList
     * @return
     */
    List<BuildingInfo> getDataByCodes(List<String> codeList);

    /**
     * 批量新增学院信息
     * @param buildingInfos
     * @return
     */
    int batchAddBuilding(List<BuildingInfo> buildingInfos);
    /**
     * 根据名称获取班级数据
     * @param nameList
     * @return
     */
    List<BuildingInfo> getBuildingByName(List<String> nameList);
}
