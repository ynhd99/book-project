package com.example.room.controller;

import com.example.room.common.advice.validatorGroup.Delete;
import com.example.room.common.advice.validatorGroup.UpdateStatus;
import com.example.room.common.exception.SaleBusinessException;
import com.example.room.entity.RoomEntity;
import com.example.room.entity.dto.MessageBody;
import com.example.room.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yangna
 * @date 2019/2/21
 */
@RequestMapping("/room")
@RestController
public class RoomController {
    @Autowired
    private RoomService roomService;

    /**
     * 新增宿舍档案
     *
     * @param roomEntity
     * @return
     */
    @PostMapping("addRoom")
    public MessageBody add(@Validated @RequestBody RoomEntity roomEntity) {
        if (roomService.addRoom(roomEntity) < 0) {
            throw new SaleBusinessException("新增失败");
        }
        return MessageBody.getMessageBody(true, "新增成功");
    }

    /**
     * 查询宿舍档案
     *
     * @param roomEntity
     * @return
     */
    @PostMapping("findDataForPage")
    public MessageBody findDataForPage(@RequestBody RoomEntity roomEntity) {
        return MessageBody.getMessageBody(true, roomService.findDataForPage(roomEntity));
    }

    /**
     * 修改宿舍档案状态
     *
     * @return
     */
    @PostMapping("updateStatus")
    public MessageBody updateStatus(@Validated({UpdateStatus.class}) @RequestBody RoomEntity roomEntity) {
        if (roomService.updateStatus(roomEntity) < 0) {
            throw new SaleBusinessException("更新仓库状态失败");
        }
        return MessageBody.getMessageBody(true, "更新仓库状态成功");
    }

    /**
     * 删除宿舍
     *
     * @return
     */
    @PostMapping("deleteRoom")
    public MessageBody deleteRoom(@Validated({Delete.class}) @RequestBody RoomEntity roomEntity){
        if (roomService.deleteRoom(roomEntity) < 0) {
            throw new SaleBusinessException("删除仓库失败");
        }
        return MessageBody.getMessageBody(true, "删除仓库成功");
    }

    /**
     * 修改宿舍信息
     *
     * @param roomEntity
     * @return
     */
    @PostMapping("update")
    public MessageBody update(@RequestBody RoomEntity roomEntity) {
        if (roomService.update(roomEntity) < 0) {
            throw new SaleBusinessException("修改失败");
        }
        return MessageBody.getMessageBody(true, "修改成功");
    }
}