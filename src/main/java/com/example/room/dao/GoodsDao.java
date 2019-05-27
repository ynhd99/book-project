package com.example.room.dao;

import com.example.room.entity.GoodsInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yangna
 * @date 2019/3/20
 */
@Mapper
@Repository
public interface GoodsDao {
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
    List<GoodsInfo> getGoodsForPage(GoodsInfo goodsInfo);

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
     * 根据编码获取物品信息
     *
     * @param code
     * @return
     */
    GoodsInfo getGoodsByCode(String code);

    /**
     * 根据名称获取物品列表
     * @param nameList
     * @return
     */
    List<GoodsInfo> getGoodsByName(List<String> nameList);

}
