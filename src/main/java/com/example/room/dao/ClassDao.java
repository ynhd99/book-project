package com.example.room.dao;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.example.room.entity.ClassInfo;
import org.springframework.stereotype.Repository;

/**
 * 班级管理数据持久化接口
 * @author yangna
 * @Date 2019-03-12 03:20:47
*/
@Mapper
@Repository
public interface ClassDao {

	/**
	 * 根据条件查询班级管理数据
	 * @param classInfo 查询实体对象
	 * @return List集合
	 */
	List<ClassInfo> getClassList(ClassInfo classInfo);

	/**
	 * 通过ID查询单条班级管理数据
	 * @param id
	 * @param tenantId 商户id
	 * @return 实体对象
	 */
	ClassInfo getClass(@Param("id") String id, @Param("tenantId") String tenantId);

	/**
	 * 新增班级管理数据
	 * @param classInfo 实体对象
	 * @return List集合
	 */
	int createClass(ClassInfo classInfo);

	/**
	 * 新增多条班级管理数据
	 * @param classses 新增实体对象
	 * @return 执行条数
	 */
	int createClasss(List<ClassInfo> classses);
	/**
	 * 更新班级管理数据（
	 * @param classInfo 实体对象
	 * @return 执行条数
	 */
	int updateClass(ClassInfo classInfo);

	/**
	 * 删除班级管理数据
	 * @param classInfo 班级管理实体对象
	 * @return 执行条数
	 */
	int deleteClass(ClassInfo classInfo);
	/**
	 * 根据code获取班级信息
	 * @param classCode 班级管理实体对象
	 * @return 执行条数
	 */
	ClassInfo getClassByCode(String classCode);
}