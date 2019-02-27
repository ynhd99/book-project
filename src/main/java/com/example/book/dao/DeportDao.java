package com.example.book.dao;

import com.example.book.entity.DeportEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yangna
 * @date 2019/2/21
 */
@Mapper
@Repository
public interface DeportDao {
    /**
     * 新增仓库信息
     * @return
     */
    int addDeport(DeportEntity deportEntity);

    /**
     * 根据编码获取仓库信息
     * @param deportCode
     * @return
     */
    DeportEntity getDeportByCode(String deportCode);

    /**
     * 获取仓库档案
     * @param deportEntity
     * @return
     */
    List<DeportEntity> findDataForPage(DeportEntity deportEntity);

    /**
     * 更新仓库状态
     * @param deportEntity
     * @return
     */
    int updateStatus(DeportEntity deportEntity);

    /**
     * 删除仓库
     * @param deportEntity
     * @return
     */
    int deleteDeport(DeportEntity deportEntity);

    /**
     * 修改仓库信息
     * @param deportEntity
     * @return
     */
    int update(DeportEntity deportEntity);
}
