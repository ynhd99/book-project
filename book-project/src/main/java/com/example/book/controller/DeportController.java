package com.example.book.controller;

import com.example.book.common.advice.validatorGroup.Delete;
import com.example.book.common.advice.validatorGroup.UpdateStatus;
import com.example.book.common.exception.SaleBusinessException;
import com.example.book.entity.DeportEntity;
import com.example.book.entity.dto.MessageBody;
import com.example.book.service.DeportService;
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
@RequestMapping("/deport")
@RestController
public class DeportController {
    @Autowired
    private DeportService deportService;

    /**
     * 新增仓库档案
     *
     * @param deportEntity
     * @return
     */
    @PostMapping("addDeport")
    public MessageBody add(@Validated @RequestBody DeportEntity deportEntity) {
        if (deportService.addDeport(deportEntity) < 0) {
            throw new SaleBusinessException("新增失败");
        }
        return MessageBody.getMessageBody(true, "新增成功");
    }

    /**
     * 查询仓库档案
     *
     * @param deportEntity
     * @return
     */
    @PostMapping("findDataForPage")
    public MessageBody findDataForPage(@RequestBody DeportEntity deportEntity) {
        return MessageBody.getMessageBody(true, deportService.findDataForPage(deportEntity));
    }

    /**
     * 修改仓库档案状态
     *
     * @return
     */
    @PostMapping("updateStatus")
    public MessageBody updateStatus(@Validated({UpdateStatus.class}) @RequestBody DeportEntity deportEntity) {
        if (deportService.updateStatus(deportEntity) < 0) {
            throw new SaleBusinessException("更新仓库状态失败");
        }
        return MessageBody.getMessageBody(true, "更新仓库状态成功");
    }

    /**
     * 删除仓库
     *
     * @return
     */
    @PostMapping("deleteDeport")
    public MessageBody deleteDeport(@Validated({Delete.class}) @RequestBody DeportEntity deportEntity ){
        if (deportService.deleteDeport(deportEntity) < 0) {
            throw new SaleBusinessException("删除仓库失败");
        }
        return MessageBody.getMessageBody(true, "删除仓库成功");
    }

    /**
     * 修改仓库信息
     *
     * @param deportEntity
     * @return
     */
    @PostMapping("update")
    public MessageBody update(@RequestBody DeportEntity deportEntity) {
        if (deportService.update(deportEntity) < 0) {
            throw new SaleBusinessException("修改失败");
        }
        return MessageBody.getMessageBody(true, "修改成功");
    }
}
