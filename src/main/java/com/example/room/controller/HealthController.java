package com.example.room.controller;

import com.example.room.common.excel.ExcelImportMessage;
import com.example.room.common.exception.SaleBusinessException;
import com.example.room.entity.HealthInfo;
import com.example.room.entity.VisitorInfo;
import com.example.room.entity.dto.MessageBody;
import com.example.room.service.ExcelBaseService;
import com.example.room.service.HealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yangna
 * @date 2019/4/9
 */
@RestController
@RequestMapping("/health")
public class HealthController {
    @Autowired
    private HealthService healthService;
    @Autowired
    private ExcelBaseService excelBaseService;

    /**
     * 新增卫生信息
     *
     * @param healthInfo
     * @return
     */
    @PostMapping("addHealth")
    public MessageBody addHealth(@RequestBody HealthInfo healthInfo) {
        if (healthService.addHealth(healthInfo) < 0) {
            throw new SaleBusinessException("新增失败");
        }
        return MessageBody.getMessageBody(true, "新增成功");
    }

    /**
     * 分页查询宿舍档案
     *
     * @param healthInfo
     * @return
     */
    @PostMapping("findHealthForPage")
    public MessageBody findHealthForPage(@RequestBody HealthInfo healthInfo) {
        return MessageBody.getMessageBody(true, healthService.findHealthForPage(healthInfo));
    }

    /**
     * 修改宿舍信息
     *
     * @param healthInfo
     * @return
     */
    @PostMapping("updateHealth")
    public MessageBody updateHealth(@RequestBody HealthInfo healthInfo) {
        if (healthService.updateHealth(healthInfo) < 0) {
            throw new SaleBusinessException("修改失败");
        }
        return MessageBody.getMessageBody(true, "修改成功");
    }
    /**
     * 导出卫生检查信息表
     */
    @RequestMapping(value = "/exportHealth", method = RequestMethod.GET)
    @ResponseBody
    public void export(HttpServletRequest request, HttpServletResponse response){
        healthService.exportHealth( response);
    }
    /**
     * 导入学生信息
     * @param file
     */
    @PostMapping("importHealth")
    public ExcelImportMessage importVisitor(@RequestParam("file") MultipartFile file){
        return excelBaseService.importHealth(file, HealthInfo.class);
    }
}
