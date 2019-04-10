package com.example.room.dao;

import com.example.room.entity.StudentInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yangna
 * @date 2019/3/15
 */
@Mapper
@Repository
public interface StudentDao {
    /**
     * 分页查询学生信息
     *
     * @param studentInfo
     * @return
     */
    List<StudentInfo> getStudentForPage(StudentInfo studentInfo);

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
     * 根据编码获取信息
     *
     * @param code
     * @return
     */
    StudentInfo getDataByCode(String code);

    /**
     * 根据id获取信息
     *
     * @param id
     * @return
     */
    String getDataById(String id);

    /**
     * 新增入住标志
     *
     * @param ids
     * @return
     */
    int addSettleFlag(List<String> ids);

    /**
     * 删除入住标志
     *
     * @param id
     * @return
     */
    int deleteSettleFlag(String id);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    int deleteSettleFlags(List<String> ids);
}
