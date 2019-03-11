package com.example.room.controller;

import com.example.room.common.exception.SaleBusinessException;
import com.example.room.entity.CollegeInfo;
import com.example.room.entity.dto.MessageBody;
import com.example.room.service.CollegeService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yangna
 * @date 2019/3/11
 */
@RestController
@RequestMapping("/college")
public class CollegeController {
    @Autowired
    private CollegeService collegeService;

    /**
     * 新增学院信息
     *
     * @param collegeInfo
     * @return
     */
    @PostMapping("add")
    public MessageBody add(@RequestBody CollegeInfo collegeInfo) {
        int num = collegeService.add(collegeInfo);
        if (num < 0) {
            throw new SaleBusinessException("新增失败");
        }
        return MessageBody.getMessageBody(true, "新增成功");
    }

    /**
     * 分页查询学院信息
     *
     * @param collegeInfo
     * @return
     */
    @PostMapping("findDataForPage")
    public MessageBody findDataForPage(@RequestBody  CollegeInfo collegeInfo) {
        PageInfo<CollegeInfo> pageInfo = collegeService.findDataForPage(collegeInfo);
        return MessageBody.getMessageBody(true, pageInfo);
    }
}
