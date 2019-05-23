package com.example.room.controller;

import com.example.room.common.exception.SaleBusinessException;
import com.example.room.entity.VisitorInfo;
import com.example.room.entity.dto.MessageBody;
import com.example.room.service.VisitorService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yangna
 * @date 2019/4/2
 */
@RestController
@RequestMapping("/visitor")
public class VisitorController {
    @Autowired
    private VisitorService visitorService;

    /**
     * 新增访问者信息
     *
     * @param visitorInfo
     * @return
     */
    @PostMapping("addVisitor")
    public MessageBody add(@RequestBody VisitorInfo visitorInfo) {
        int num = visitorService.addVisitor(visitorInfo);
        if (num < 0) {
            throw new SaleBusinessException("新增失败");
        }
        return MessageBody.getMessageBody(true, "新增成功");
    }

    /**
     * 分页查询访问者信息
     *
     * @param visitorInfo
     * @return
     */
    @PostMapping("findVisitorForPage")
    public MessageBody findGoodsForPage(@RequestBody VisitorInfo visitorInfo) {
        PageInfo<VisitorInfo> pageInfo = visitorService.findVisitorForPage(visitorInfo);
        return MessageBody.getMessageBody(true, pageInfo);
    }

    /**
     * 更新访问者信息
     *
     * @param visitorInfo
     * @return
     */
    @PostMapping("updateVisitor")
    public MessageBody updateStatus(@RequestBody VisitorInfo visitorInfo) {
        int num = visitorService.updateVisitor(visitorInfo);
        if (num <= 0) {
            throw new SaleBusinessException("更新失败");
        }
        return MessageBody.getMessageBody(true, "更新成功");
    }
    /**
     * 导出外来人员访问登记信息
     */
    @RequestMapping(value = "/exportVisitor", method = RequestMethod.GET)
    @ResponseBody
    public void export(HttpServletRequest request, HttpServletResponse response){
        visitorService.exportVisitor( response);
    }
}
