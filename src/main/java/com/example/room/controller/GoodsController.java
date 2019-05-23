package com.example.room.controller;

import com.example.room.common.exception.SaleBusinessException;
import com.example.room.entity.GoodsInfo;
import com.example.room.entity.dto.MessageBody;
import com.example.room.service.GoodsService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yangna
 * @date 2019/3/24
 */
@RestController
@RequestMapping("goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    /**
     * 新增物品信息
     *
     * @param goodsInfo
     * @return
     */
    @PostMapping("addGoods")
    public MessageBody add(@RequestBody GoodsInfo goodsInfo) {
        int num = goodsService.addGoods(goodsInfo);
        if (num < 0) {
            throw new SaleBusinessException("新增失败");
        }
        return MessageBody.getMessageBody(true, "新增成功");
    }

    /**
     * 分页查询物品信息
     *
     * @param goodsInfo
     * @return
     */
    @PostMapping("findGoodsForPage")
    public MessageBody findGoodsForPage(@RequestBody GoodsInfo goodsInfo) {
        PageInfo<GoodsInfo> pageInfo = goodsService.getGoodsForPage(goodsInfo);
        return MessageBody.getMessageBody(true, pageInfo);
    }

    /**
     * 更新物品信息
     *
     * @param goodsInfo
     * @return
     */
    @PostMapping("updateGoods")
    public MessageBody updateStatus(@RequestBody GoodsInfo goodsInfo) {
        int num = goodsService.updateGoods(goodsInfo);
        if (num <= 0) {
            throw new SaleBusinessException("更新失败");
        }
        return MessageBody.getMessageBody(true, "更新成功");
    }

    /**
     * 删除物品信息
     *
     * @param goodsInfo
     * @return
     */
    @PostMapping("deleteGoods")
    public MessageBody deleteGoods(@RequestBody GoodsInfo goodsInfo) {
        int num = goodsService.deleteGoods(goodsInfo);
        if (num <= 0) {
            throw new SaleBusinessException("删除失败");
        }
        return MessageBody.getMessageBody(true, "删除成功");
    }
    /**
     * 导出物品信息表
     */
    @RequestMapping(value = "/exportGoods", method = RequestMethod.GET)
    @ResponseBody
    public void export(HttpServletRequest request, HttpServletResponse response){
        goodsService.exportGoods( response);
    }
}
