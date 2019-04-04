package com.example.room.controller;

import com.example.room.common.exception.SaleBusinessException;
import com.example.room.entity.RecordInfo;
import com.example.room.entity.dto.MessageBody;
import com.example.room.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yangna
 * @date 2019/4/4
 */
@RestController
@RequestMapping("/record")
public class RecordController {
    @Autowired
    private RecordService recordService;

    /**
     * 新增公告信息
     *
     * @param recordInfo
     * @return
     */
    @PostMapping("addRecord")
    public MessageBody add(@RequestBody RecordInfo recordInfo) {
        int num = recordService.addRecord(recordInfo);
        if (num < 0) {
            throw new SaleBusinessException("新增失败");
        }
        return MessageBody.getMessageBody(true, "新增成功");
    }

    /**
     * 分页查询公告信息
     *
     * @param recordInfo
     * @return
     */
    @PostMapping("findRecordList")
    public MessageBody findGoodsForPage(@RequestBody RecordInfo recordInfo) {
        List<RecordInfo> recordInfos = recordService.findRecordList(recordInfo);
        return MessageBody.getMessageBody(true, recordInfos);
    }

    /**
     * 更新公告信息
     *
     * @param recordInfo
     * @return
     */
    @PostMapping("updateRecord")
    public MessageBody updateStatus(@RequestBody RecordInfo recordInfo) {
        int num = recordService.updateRecord(recordInfo);
        if (num <= 0) {
            throw new SaleBusinessException("更新失败");
        }
        return MessageBody.getMessageBody(true, "更新成功");
    }

    /**
     * 更新公告信息
     *
     * @param recordInfo
     * @return
     */
    @PostMapping("deleteRecord")
    public MessageBody deleteRecord(@RequestBody RecordInfo recordInfo) {
        int num = recordService.deleteRecord(recordInfo);
        if (num <= 0) {
            throw new SaleBusinessException("删除失败");
        }
        return MessageBody.getMessageBody(true, "删除成功");
    }
}
