package com.example.room.service;

import com.example.room.entity.GoodsInfo;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author yangna
 * @date 2019/3/20
 */
public interface GoodsService {
    /**
     * 新增物品信息
     *
     * @param goodsInfo
     * @return
     */
    int addGoods(GoodsInfo goodsInfo);

    /**
     * 分页查询物品信息
     *
     * @param goodsInfo
     * @return
     */
    PageInfo<GoodsInfo> getGoodsForPage(GoodsInfo goodsInfo);

    /**
     * 更新物品信息
     *
     * @param goodsInfo
     * @return
     */
    int updateGoods(GoodsInfo goodsInfo);

    /**
     * 删除物品信息
     *
     * @param goodsInfo
     * @return
     */
    int deleteGoods(GoodsInfo goodsInfo);
    /**
     * 导出物品信息
     *
     * @return
     */
    void exportGoods(HttpServletResponse response);

}
