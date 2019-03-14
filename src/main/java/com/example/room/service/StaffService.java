package com.example.room.service;

import com.example.room.entity.StaffInfo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author yangna
 * @date 2019/3/13
 */
public interface StaffService {
    /**
     * 分页查询宿管员信息
     *
     * @param staffInfo
     * @return
     */
    PageInfo<StaffInfo> getStaffForPage(StaffInfo staffInfo);

    /**
     * 编辑宿管员信息
     *
     * @param staffInfo
     * @return
     */
    int updateStaff(StaffInfo staffInfo);

    /**
     * 删除宿管员信息
     *
     * @param staffInfo
     * @return
     */
    int deleteStaff(StaffInfo staffInfo);

    /**
     * 新增宿管员信息
     *
     * @param staffInfo
     * @return
     */
    int addStaff(StaffInfo staffInfo);
}
