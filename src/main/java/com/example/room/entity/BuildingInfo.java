package com.example.room.entity;

import com.example.room.entity.common.Base;
import lombok.Data;

/**
 * @author yangna
 * @date 2019/3/18
 */
@Data
public class BuildingInfo extends Base {
    /**
     * 宿舍楼编码
     */
    private String buildingCode;
    /**
     * 宿舍楼名称
     */
    private String buildingName;
    /**
     * 宿管员id
     */
    private String staffId;
    /**
     * 宿管员名称
     */
    private String staffName;
    /**
     * 宿管员手机号
     */
    private String staffPhone;
    /**
     * 模糊查询字段
     */
    private String queryString;
}
