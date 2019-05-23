package com.example.room.service;

import com.example.room.entity.ClassInfo;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * 班级管理接口
 * @author yangna
 * @Date 2019-03-12 03:20:47
 */
public interface ClassService {

	/**
	 * 根据条件查询班级管理数据（不分页）
	 * @param classInfo 查询实体对象
	 * @return List集合
	 */
	public List<ClassInfo> findAllClass(ClassInfo classInfo);
	/**
	 * 根据条件查询班级管理数据（含有分页）
	 * @param classInfo 查询实体对象
	 * @return 分页对象
	 */
	public PageInfo<ClassInfo> findClassForPage(ClassInfo classInfo);
	/**
	 * 通过ID查询单条班级管理数据
	 * @param id
	 * @param tenantId 商户id
	 * @return 班级管理对象
	 */
	public ClassInfo findClassById(String id, String tenantId);
	/**
	 * 新增班级管理数据
	 * @param classInfo 班级管理实体对象
	 * @return 是否成功
	 */
	public boolean addClass(ClassInfo classInfo);

	/**
	 * 编辑班级管理数据
	 * @param classInfo 班级管理实体对象
	 * @return 是否成功
	 */
	public boolean updateClass(ClassInfo classInfo);
	/**
	 * 删除班级管理数据
	 * @param classInfo 班级管理实体对象
	 * @return 是否成功
	 */
	public boolean deleteClassById(ClassInfo classInfo);
	/**
	 * 导出班级信息
	 *
	 * @return
	 */
	void exportClass(HttpServletResponse response);
}