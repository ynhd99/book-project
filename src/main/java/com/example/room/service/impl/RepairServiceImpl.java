package com.example.room.service.impl;

import com.example.room.controller.UserController;
import com.example.room.dao.RepairDao;
import com.example.room.entity.GoodsInfo;
import com.example.room.entity.RepairInfo;
import com.example.room.service.RepairService;
import com.example.room.utils.common.ExcelUtils;
import com.example.room.utils.common.UUIDGenerator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.util.Date;
import java.util.List;

/**
 * @author yangna
 * @date 2019/4/8
 */
@Service
public class RepairServiceImpl implements RepairService {
    @Autowired
    private RepairDao repairDao;
    @Autowired
    private UserController userController;

    /**
     * 新增维修信息
     *
     * @param repairInfo
     * @return
     */
    @Override
    public int addRepair(RepairInfo repairInfo) {
        //封装参数
        repairInfo.setId(UUIDGenerator.getUUID());
        repairInfo.setCreateTime(new Date());
        repairInfo.setCreateUser(userController.getUser());
        repairInfo.setUpdateTime(new Date());
        repairInfo.setUpdateUser(userController.getUser());
        return repairDao.addRepair(repairInfo);
    }

    /**
     * 更新维修信息
     *
     * @param repairInfo
     * @return
     */
    @Override
    public int updateRepair(RepairInfo repairInfo) {
        repairInfo.setUpdateTime(new Date());
        repairInfo.setUpdateUser(userController.getUser());
        return repairDao.updateRepair(repairInfo);
    }

    /**
     * 分页查询维修信息
     *
     * @param repairInfo
     * @return
     */
    @Override
    public PageInfo<RepairInfo> findRepairForPage(RepairInfo repairInfo) {
        PageHelper.startPage(repairInfo.getPage(), repairInfo.getSize());
        PageInfo<RepairInfo> pageInfo = new PageInfo<>(repairDao.findRepairForPage(repairInfo));
        return pageInfo;
    }

    /**
     * 导出维修情况
     * @param response
     */
    @Override
    public void exportRepair(HttpServletResponse response) {
        RepairInfo repairInfo = new RepairInfo();
        //获取到学生列表
        List<RepairInfo> repairInfos = repairDao.findRepairForPage(repairInfo);
        String header[] = {"宿舍号","维修日期","申请人","申请原因","状态","驳回原因"};
        String title = "物品维修表";
        String fileName = "物品维修表";
        int rowNum = 1;
        HSSFWorkbook workbook = ExcelUtils.exportExcel(title,header);
        HSSFSheet sheet = workbook.getSheet(title);
        CellStyle cellStyle = ExcelUtils.getCellStyle(workbook);
        for (RepairInfo e : repairInfos) {
            String message;
            if(e.getStatus() == 1){
                message = "待审核";
            }else if(e.getStatus() == 2){
                message = "已审核";
            }else{
                message = "已驳回";
            }
            HSSFRow rows = sheet.createRow(rowNum);
            ExcelUtils.addCell(rows, 0,e.getRoomCode(), cellStyle);
            ExcelUtils.addCell(rows, 1,e.getRepairDate(), cellStyle);
            ExcelUtils.addCell(rows, 2,e.getRepairPerson(), cellStyle);
            ExcelUtils.addCell(rows, 3,e.getRemark(), cellStyle);
            ExcelUtils.addCell(rows, 4,message, cellStyle);
            ExcelUtils.addCell(rows, 5,e.getReason(), cellStyle);
            rowNum++;
        };
        ExcelUtils.returnExport(workbook,response,fileName);
    }
}
