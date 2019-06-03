package com.example.room.controller;

import com.example.room.common.advice.validatorGroup.Add;
import com.example.room.common.exception.SaleBusinessException;
import com.example.room.entity.RoomCategory;
import com.example.room.entity.dto.MessageBody;
import com.example.room.service.RoomCateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yangna
 * @date 2019/2/25
 */
@RestController
@RequestMapping("/roomCate")
public class RoomCateController {
    @Autowired
    private RoomCateService roomCateService;

    /**
     * 新增分类信息
     *
     * @param roomCategory
     * @return
     */
    @PostMapping("addRoomCate")
    public MessageBody addRoomCate(@Validated({Add.class}) @RequestBody RoomCategory roomCategory) {
        if (roomCateService.addRoomCate(roomCategory) < 0) {
            throw new SaleBusinessException("新增失败");
        }
        return MessageBody.getMessageBody(true, "新增成功");
    }

    /**
     * 获取到最大的编码
     *
     * @param roomCategory
     * @return
     */
    @PostMapping("getMaxCode")
    public MessageBody getMaxCode(@RequestBody RoomCategory roomCategory) {
        return MessageBody.getMessageBody(true, roomCateService.getMaxCode(roomCategory));
    }

    /**
     * 查询分类信息
     *
     * @param roomCategory
     * @return
     */
    @PostMapping("findDataForPage")
    public MessageBody findDataForPage(@RequestBody RoomCategory roomCategory) {
        return MessageBody.getMessageBody(true, roomCateService.findDataForPage(roomCategory));
    }

    /**
     * 修改图书分类信息
     *
     * @param roomCategory
     * @return
     */
    @PostMapping("updateRoomCate")
    public MessageBody updateBookCate(@RequestBody RoomCategory roomCategory) {
        if (roomCateService.updateRoomCate(roomCategory) < 0) {
            throw new SaleBusinessException("修改失败");
        }
        return MessageBody.getMessageBody(true, "修改成功");
    }
    /**
     * 删除宿舍分类信息
     *
     * @param roomCategory
     * @return
     */
    @PostMapping("deleteRoomCate")
    public MessageBody deleteRoomCate(@RequestBody RoomCategory roomCategory) {
        if (roomCateService.deleteRoomCate(roomCategory) < 0) {
            throw new SaleBusinessException("删除失败");
        }
        return MessageBody.getMessageBody(true, "删除成功");
    }
}
