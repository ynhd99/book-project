package com.example.room.service;

import com.example.room.entity.VisitorInfo;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author yangna
 * @date 2019/4/2
 */
public interface VisitorService {
    /**
     * 新增访问信息
     *
     * @param visitorInfo
     * @return
     */
    int addVisitor(VisitorInfo visitorInfo);

    /**
     * 修改访问者信息
     *
     * @param visitorInfo
     * @return
     */
    int updateVisitor(VisitorInfo visitorInfo);

    /**
     * 分页查询访问者信息
     *
     * @param visitorInfo
     * @return
     */
    PageInfo<VisitorInfo> findVisitorForPage(VisitorInfo visitorInfo);
    /**
     * 导出外来人员访问信息
     *
     * @return
     */
    void exportVisitor(HttpServletResponse response);
}
