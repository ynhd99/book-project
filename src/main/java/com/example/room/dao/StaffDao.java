package com.example.room.dao;

import com.example.room.entity.StaffInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yangna
 * @date 2019/3/13
 */
@Mapper
@Repository
public interface StaffDao {
    /**
     * 分页查询宿管员信息
     *
     * @param staffInfo
     * @return
     */
    List<StaffInfo> getStaffForPage(StaffInfo staffInfo);

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

    /**
     * 根据编码获取信息
     *
     * @param code
     * @return
     */
    StaffInfo getDataByCode(String code);

    /**
     * 批量删除宿管员表
     *
     * @param ids
     * @return
     */
    int batchDelete(List<String> ids);

    /**
     * 批量删除用户表
     */
    int batchDeleteUser(List<String> ids);
}
