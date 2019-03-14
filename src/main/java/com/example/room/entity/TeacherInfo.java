package com.example.room.entity;

import com.example.room.entity.common.Base;
import lombok.Data;

/**
 * @author yangna
 * @date 2019/3/13
 */
@Data
public class TeacherInfo extends Base {
    private String teacherName;
    private String teacherCode;
    private String teacherSex;
    private String teacherPhone;
    private String roleId;
    private String queryString;
    private String collegeName;
    private String collegeId;
}
