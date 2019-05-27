package com.example.room.controller;

import com.example.room.common.excel.ExcelImportMessage;
import com.example.room.common.exception.SaleBusinessException;
import com.example.room.entity.HealthInfo;
import com.example.room.entity.RepairInfo;
import com.example.room.entity.dto.MessageBody;
import com.example.room.service.ExcelBaseService;
import com.example.room.service.RepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yangna
 * @date 2019/4/8
 */
@RestController
@RequestMapping("repair")
public class RepairController {
    @Autowired
    private RepairService repairService;
    @Autowired
    private ExcelBaseService excelBaseService;

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
    /**
     * 导出维修登记信息
     */
    @RequestMapping(value = "/exportRepair", method = RequestMethod.GET)
    @ResponseBody
    public void export(HttpServletRequest request, HttpServletResponse response){
        repairService.exportRepair( response);
    }
    /**
     * 导入学生信息
     * @param file
     */
    @PostMapping("importRepair")
    public ExcelImportMessage importRepair(@RequestParam("file") MultipartFile file){
        return excelBaseService.importRepair(file, RepairInfo.class);
    }
}
