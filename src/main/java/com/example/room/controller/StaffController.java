package com.example.room.controller;

import com.example.room.common.excel.AbstractBaseExcelImportTask;
import com.example.room.common.excel.ExcelImportMessage;
import com.example.room.common.exception.SaleBusinessException;
import com.example.room.dao.StaffDao;
import com.example.room.entity.StaffInfo;
import com.example.room.entity.TeacherInfo;
import com.example.room.entity.dto.MessageBody;
import com.example.room.service.ExcelBaseService;
import com.example.room.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yangna
 * @date 2019/3/13
 */
@RestController
@RequestMapping("/staff")
public class StaffController {
    @Autowired
    private StaffService staffService;
    @Autowired
    private ExcelBaseService excelBaseService;
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
    /**
     * 导出宿管员信息
     */
    @RequestMapping(value = "/exportStaff", method = RequestMethod.GET)
    @ResponseBody
    public void export(HttpServletRequest request, HttpServletResponse response){
        staffService.exportStaff( response);
    }
    /**
     * 导入宿管员信息
     * @param file
     */
    @PostMapping("importStaff")
    public ExcelImportMessage importStaff(@RequestParam("file") MultipartFile file){
    return excelBaseService.importStaff(file, StaffInfo.class);
    }
}
