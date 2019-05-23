package com.example.room.service;

import com.example.room.entity.StaffInfo;
import com.example.room.entity.TeacherInfo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author yangna
 * @date 2019/3/13
 */
public interface TeacherService {
    /**
     * 分页查询辅导员信息
     *
     * @param teacherInfo
     * @return
     */
    PageInfo<TeacherInfo> getTeacherForPage(TeacherInfo teacherInfo);

    /**
     * 编辑辅导员信息
     *
     * @param teacherInfo
     * @return
     */
    int updateTeacher(TeacherInfo teacherInfo);

    /**
     * 删除辅导员信息
     *
     * @param teacherInfo
     * @return
     */
    int deleteTeacher(TeacherInfo teacherInfo);

    /**
     * 新增辅导员信息
     *
     * @param teacherInfo
     * @return
     */
    int addTeacher(TeacherInfo teacherInfo);
    /**
     * 导出老师信息
     *
     * @return
     */
    void exportTeacher(HttpServletResponse response);
}
