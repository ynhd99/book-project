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
     * 根据编码获取信息
     *
     * @param codes
     * @return
     */
    List<StudentInfo> getDataByCodes(List<String> codes);
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

    /**
     * 根据id获取床号
     *
     * @param ids
     * @return
     */
    List<StudentInfo> getBedCountById(List<String> ids);

    /**
     * 批量删除学生表
     *
     * @param ids
     * @return
     */
    int batchDelete(List<String> ids);

    /**
     * 批量删除用户表
     */
    int batchDeleteUser(List<String> ids);

    /**
     * 批量新增学生信息
     * @param studentInfos
     * @return
     */
    int batchAddStudent(List<StudentInfo> studentInfos);
}
