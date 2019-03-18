package com.example.room.entity;

import com.example.room.entity.common.Base;

import lombok.Data;

import java.io.Serializable;

/**
 * 班级管理实体类
 *
 * @author yangna
 * @Date 2019-03-12 03:20:47
 */
@Data
public class ClassInfo extends Base implements Serializable {
    /**
     * 学院id
     */
    private String collegeId;
    /**
     * 班级名称
     */
    private String className;
    /**
     * 班级编码
     */
    private String classCode;
    /**
     * 学院名称
     */
    private String collegeName;
    /**
     * 查询条件
     */
    private String queryString;
}