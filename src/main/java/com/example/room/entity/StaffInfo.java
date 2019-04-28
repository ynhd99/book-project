package com.example.room.entity;

import com.example.room.entity.common.Base;
import lombok.Data;

import java.util.List;

/**
 * @author yangna
 * @date 2019/3/13
 */
@Data
public class StaffInfo extends Base {
    /**
     * 宿管员姓名
     */
    private String staffName;
    /**
     * 宿管员编码
     */
    private String staffCode;
    /**
     * 宿管员手机号
     */
    private String staffPhone;
    /**
     * 角色id
     */
    private String roleId;
    /**
     * 宿管员性别
     */
    private String staffSex;
    /**
     * 模糊查询字段
     */
    private String queryString;
    /**
     * 批量删除宿管员字段
     */
    private List<String> deleteStaffList;
}
