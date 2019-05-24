package com.example.room.dao;

import com.example.room.entity.TeacherInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yangna
 * @date 2019/3/13
 */
@Mapper
@Repository
public interface TeacherDao {
    /**
     * 分页查询辅导员信息
     *
     * @param teacherInfo
     * @return
     */
    List<TeacherInfo> getTeacherForPage(TeacherInfo teacherInfo);

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
     * 根据编码获取信息
     *
     * @param code
     * @return
     */
    TeacherInfo getDataByCode(String code);

    /**
     * 根据id获取信息
     *
     * @param id
     * @return
     */
    String getDataById(String id);
    /**
     * 批量删除老师表
     * @param ids
     * @return
     */
    int batchDelete(List<String> ids);
    /**
     * 批量删除用户表
     */
    int batchDeleteUser(List<String> ids);

    /**
     * 根据编码批量查询
     * @param codeList
     * @return
     */
    List<TeacherInfo> getDataByCodes(List<String> codeList);

    /**
     * 批量新增老师信息
     * @param teacherInfos
     * @return
     */
    int batchAddTeacher(List<TeacherInfo> teacherInfos);
}
