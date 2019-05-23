package com.example.room.service;

import com.example.room.entity.StudentInfo;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author yangna
 * @date 2019/3/15
 */
public interface StudentService {
    /**
     * 分页查询学生信息
     *
     * @param studentInfo
     * @return
     */
    PageInfo<StudentInfo> getStudentForPage(StudentInfo studentInfo);

    /**
     * 编辑学生信息
     *
     * @param studentInfo
     * @return
     */
    int updateStudent(StudentInfo studentInfo);

    /**
     * 删除学生信息
     *
     * @param studentInfo
     * @return
     */
    int deleteStudent(StudentInfo studentInfo);

    /**
     * 新增学生信息
     *
     * @param studentInfo
     * @return
     */
    int addStudent(StudentInfo studentInfo);
    /**
     * 导出学生信息
     *
     * @return
     */
    void exportStudent(HttpServletResponse response);
}
