package com.example.room.dao;

import com.example.room.entity.VisitorInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yangna
 * @date 2019/4/2
 */
@Mapper
@Repository
public interface VisitorDao {
    /**
     * 新增访问信息
     * @param visitorInfo
     * @return
     */
    int addVisitor(VisitorInfo visitorInfo);

    /**
     * 修改访问者信息
     * @param visitorInfo
     * @return
     */
    int updateVisitor(VisitorInfo visitorInfo);

    /**
     * 分页查询访问者信息
     * @param visitorInfo
     * @return
     */
    List<VisitorInfo> findVisitorForPage(VisitorInfo visitorInfo);

    /**
     * 批量新增访问者信息
     * @param visitorInfos
     * @return
     */
    int batchAddVisitor(List<VisitorInfo> visitorInfos);
}
