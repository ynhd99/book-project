package com.example.room.entity;

import com.example.room.entity.common.Base;
import lombok.Data;

import java.util.List;

/**
 * @author yangna
 * @date 2019/3/13
 */
@Data
public class TeacherInfo extends Base {
    /**
     * 辅导员名称
     */
    private String teacherName;
    /**
     * 辅导员编码
     */
    private String teacherCode;
    /**
     * 辅导员性别
     */
    private String teacherSex;
    /**
     * 辅导员手机号
     */
    private String teacherPhone;
    /**
     * 角色id
     */
    private String roleId;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 模糊查询字段
     */
    private String queryString;
    /**
     * 学院名称
     */
    private String collegeName;
    /**
     * 学院id
     */
    private String collegeId;
    /**
     * 批量删除老师列表
     */
    private List<String> deleteTeacherList;
}
