package com.example.room.controller;

import com.example.room.common.exception.SaleBusinessException;
import com.example.room.entity.StaffInfo;
import com.example.room.entity.dto.MessageBody;
import com.example.room.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yangna
 * @date 2019/3/13
 */
@RestController
@RequestMapping("/staff")
public class StaffController {
    @Autowired
    private StaffService staffService;

    /**
     * 分页查询宿管员信息
     *
     * @param staffInfo
     * @return
     */
    @PostMapping("findStaffForPage")
    public MessageBody findStaffForPage(@RequestBody StaffInfo staffInfo) {
        return MessageBody.getMessageBody(true, staffService.getStaffForPage(staffInfo));
    }

    /**
     * 新增宿管员信息
     *
     * @param staffInfo
     * @return
     */
    @PostMapping("addStaff")
    public MessageBody addStaff(@RequestBody StaffInfo staffInfo) {
        if (staffService.addStaff(staffInfo) <= 0) {
            throw new SaleBusinessException("新增失败");
        }
        return MessageBody.getMessageBody(true, "新增成功");
    }

    /**
     * 更新宿管员信息
     *
     * @param staffInfo
     * @return
     */
    @PostMapping("updateStaff")
    public MessageBody updateStaff(@RequestBody StaffInfo staffInfo) {
        if (staffService.updateStaff(staffInfo) <= 0) {
            throw new SaleBusinessException("更新失败");
        }
        return MessageBody.getMessageBody(true, "更新成功");
    }

    /**
     * 删除宿管员信息
     *
     * @param staffInfo
     * @return
     */
    @PostMapping("deleteStaff")
    public MessageBody deleteStaff(@RequestBody StaffInfo staffInfo) {
        if (staffService.deleteStaff(staffInfo) <= 0) {
            throw new SaleBusinessException("删除失败");
        }
        return MessageBody.getMessageBody(true, "删除成功");
    }
}
