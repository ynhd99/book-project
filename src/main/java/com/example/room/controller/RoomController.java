package com.example.room.controller;

import com.example.room.common.advice.validatorGroup.Delete;
import com.example.room.common.exception.SaleBusinessException;
import com.example.room.entity.RoomEntity;
import com.example.room.entity.dto.MessageBody;
import com.example.room.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
     * 查询宿舍档案
     *
     * @param roomEntity
     * @return
     */
    @PostMapping("findRoomList")
    public MessageBody findRoomList(@RequestBody RoomEntity roomEntity) {
        return MessageBody.getMessageBody(true, roomService.findRoomList(roomEntity));
    }

    /**
     * 删除宿舍
     *
     * @return
     */
    @PostMapping("deleteRoom")
    public MessageBody deleteRoom(@Validated({Delete.class}) @RequestBody RoomEntity roomEntity) {
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
    @PostMapping("updateRoom")
    public MessageBody updateRoom(@RequestBody RoomEntity roomEntity) {
        if (roomService.updateRoom(roomEntity) < 0) {
            throw new SaleBusinessException("修改失败");
        }
        return MessageBody.getMessageBody(true, "修改成功");
    }
    /**
     * 导出宿舍信息
     */
    @RequestMapping(value = "/exportRoom", method = RequestMethod.GET)
    @ResponseBody
    public void export(HttpServletRequest request, HttpServletResponse response){
        roomService.exportRoom( response);
    }
}
