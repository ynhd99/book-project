package com.example.room.entity;

import com.example.room.entity.common.Base;
import lombok.Data;

/**
 * @author yangna
 * @date 2019/3/11
 */
@Data
public class StudentInfo extends Base {
    /**
     * 学生学号
     */
    private String studentCode;
    /**
     * 学生姓名
     */
    private String studentName;
    /**
     * 学院id
     */
    private String collegeId;
    /**
     * 班级id
     */
    private String classId;
    /**
     * 学生手机号
     */
    private String studentPhone;
    /**
     * 学生性别
     */
    private String studentSex;
    /**
     * 模糊查询字段
     */
    private String queryString;
    /**
     * 角色id
     */
    private String roleId;
    /**
     * 学院名称
     */
    private String collegeName;
    /**
     * 班级名称
     */
    private String className;
    /**
     * 入住标志
     */
    private int settleFlag;
}
