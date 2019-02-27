package com.example.book.service;

import com.example.book.entity.DeportEntity;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author yangna
 * @date 2019/2/21
 */
public interface DeportService {
    /**
     * 新增仓库信息
     * @param deportEntity
     * @return
     */
    int addDeport(DeportEntity deportEntity);
    /**
     * 获取仓库档案
     * @param deportEntity
     * @return
     */
    PageInfo<DeportEntity> findDataForPage(DeportEntity deportEntity);

    /**
     * 更新仓库状态
     *
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
