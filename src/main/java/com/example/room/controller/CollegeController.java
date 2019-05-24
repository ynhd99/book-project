package com.example.room.controller;

import com.example.room.common.excel.AbstractBaseExcelImportTask;
import com.example.room.common.excel.ExcelImportMessage;
import com.example.room.common.excel.ExcelMetaData;
import com.example.room.common.exception.SaleBusinessException;
import com.example.room.entity.CollegeInfo;
import com.example.room.entity.StaffInfo;
import com.example.room.entity.dto.MessageBody;
import com.example.room.service.CollegeService;
import com.example.room.service.ExcelBaseService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author yangna
 * @date 2019/3/11
 */
@RestController
@RequestMapping("/college")
public class CollegeController {
    @Autowired
    private CollegeService collegeService;
    @Autowired
    private ExcelBaseService excelBaseService;
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
    public MessageBody findDataForPage(@RequestBody CollegeInfo collegeInfo) {
        PageInfo<CollegeInfo> pageInfo = collegeService.findDataForPage(collegeInfo);
        return MessageBody.getMessageBody(true, pageInfo);
    }

    /**
     * 更新学院状态
     *
     * @param collegeInfo
     * @return
     */
    @PostMapping("updateStatus")
    public MessageBody updateStatus(@RequestBody CollegeInfo collegeInfo) {
        int num = collegeService.updateStatus(collegeInfo);
        if (num <= 0) {
            throw new SaleBusinessException("更新失败");
        }
        return MessageBody.getMessageBody(true, "更新成功");
    }

    /**
     * 更新学院
     *
     * @param collegeInfo
     * @return
     */
    @PostMapping("update")
    public MessageBody update(@RequestBody CollegeInfo collegeInfo) {
        int num = collegeService.update(collegeInfo);
        if (num <= 0) {
            throw new SaleBusinessException("更新失败");
        }
        return MessageBody.getMessageBody(true, "更新成功");
    }

    /**
     * 更新学院状态
     *
     * @param collegeInfo
     * @return
     */
    @PostMapping("delete")
    public MessageBody delete(@RequestBody CollegeInfo collegeInfo) {
        int num = collegeService.delete(collegeInfo);
        if (num <= 0) {
            throw new SaleBusinessException("删除失败");
        }
        return MessageBody.getMessageBody(true, "删除成功");
    }
    /**
     * 导出学院信息
     */
    @RequestMapping(value = "/exportCollege", method = RequestMethod.GET)
    @ResponseBody
    public void export(HttpServletRequest request, HttpServletResponse response){
        collegeService.exportCollege( response);
    }
    /**
     * 导入学院信息
     * @param file
     */
    @PostMapping("importCollege")
    public ExcelImportMessage importCollege(@RequestParam("file") MultipartFile file){
  return excelBaseService.importCollege(file,CollegeInfo.class);
    }
}
