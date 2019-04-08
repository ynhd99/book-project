package com.example.room.controller;

import com.example.room.common.exception.SaleBusinessException;
import com.example.room.entity.RepairInfo;
import com.example.room.entity.dto.MessageBody;
import com.example.room.service.RepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yangna
 * @date 2019/4/8
 */
@RestController
@RequestMapping("repair")
public class RepairController {
    @Autowired
    private RepairService repairService;

    /**
     * 新增维修信息
     *
     * @param repairInfo
     * @return
     */
    @PostMapping("addRepair")
    public MessageBody addRepair(@RequestBody RepairInfo repairInfo) {
        if (repairService.addRepair(repairInfo) < 0) {
            throw new SaleBusinessException("新增失败");
        }
        return MessageBody.getMessageBody(true, "新增成功");
    }

    /**
     * 分页查询宿舍档案
     *
     * @param repairInfo
     * @return
     */
    @PostMapping("findRepairForPage")
    public MessageBody findRepairForPage(@RequestBody RepairInfo repairInfo) {
        return MessageBody.getMessageBody(true, repairService.findRepairForPage(repairInfo));
    }

    /**
     * 修改宿舍信息
     *
     * @param repairInfo
     * @return
     */
    @PostMapping("updateRepair")
    public MessageBody updateRepair(@RequestBody RepairInfo repairInfo) {
        if (repairService.updateRepair(repairInfo) < 0) {
            throw new SaleBusinessException("修改失败");
        }
        return MessageBody.getMessageBody(true, "修改成功");
    }
}
