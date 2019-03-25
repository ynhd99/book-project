package com.example.room.entity;

import com.example.room.entity.common.Base;
import lombok.Data;

/**
 * @author yangna
 * @date 2019/3/20
 */
@Data
public class GoodsInfo extends Base {
    /**
     * 物品编码
     */
    private String goodsCode;
    /**
     * 物品名称
     */
    private String goodsName;
    /**
     * 模糊搜索字段
     */
    private String queryString;
}
