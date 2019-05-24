package com.example.room.entity;

import com.example.room.entity.common.Base;
import lombok.Data;

import java.util.List;
import java.util.Objects;

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
     * 角色名称
     */
    private String roleName;
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
    /**
     * 床位号
     */
    private int bedCount;
    /**
     * 删除学生列表
     */
    private List<String> deleteStudentList;
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StudentInfo that = (StudentInfo) o;
        return Objects.equals(studentCode, that.studentCode);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (studentCode == null ? 0 : studentCode.hashCode());
        return result;
    }
}
