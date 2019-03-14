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
}
