package com.example.room.controller;

import com.example.room.common.excel.ExcelImportMessage;
import com.example.room.common.exception.SaleBusinessException;
import com.example.room.entity.RoomDetailInfo;
import com.example.room.entity.RoomEntity;
import com.example.room.entity.dto.MessageBody;
import com.example.room.service.ExcelBaseService;
import com.example.room.service.RoomDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author yangna
 * @date 2019/4/10
 */
@RestController
@RequestMapping("/roomDetail")
public class RoomDetailController {
    @Autowired
    private RoomDetailService roomDetailService;
    @Autowired
    private ExcelBaseService excelBaseService;

    /**
     * 新增访问者信息
     *
     * @param roomEntity
     * @return
     */
    @PostMapping("addRoomDetail")
    public MessageBody addRoomDetail(@RequestBody RoomEntity roomEntity) {
        int num = roomDetailService.addRoomDetail(roomEntity.getRoomDetailInfoList());
        if (num < 0) {
            throw new SaleBusinessException("新增失败");
        }
        return MessageBody.getMessageBody(true, "新增成功");
    }

    /**
     * 新增访问者信息
     *
     * @param roomDetailInfo
     * @return
     */
    @PostMapping("deleteRoomDetail")
    public MessageBody deleteRoomDetail(@RequestBody RoomDetailInfo roomDetailInfo) {
        int num = roomDetailService.deleteRoomDetail(roomDetailInfo);
        if (num < 0) {
            throw new SaleBusinessException("删除失败");
        }
        return MessageBody.getMessageBody(true, "删除成功");
    }

    /**
     * 查询宿舍档案
     *
     * @param roomDetailInfo
     * @return
     */
    @PostMapping("findRoomDetailForPage")
    public MessageBody findDataForPage(@RequestBody RoomDetailInfo roomDetailInfo) {
        return MessageBody.getMessageBody(true, roomDetailService.getRoomDetailForPage(roomDetailInfo));
    }
    /**
     * 导入宿舍详情信息
     * @param file
     */
    @PostMapping("importRoomDetail")
    public ExcelImportMessage importRoomDetail(@RequestParam("file") MultipartFile file){
        return excelBaseService.importRoomDetail(file, RoomDetailInfo.class);
    }
}
