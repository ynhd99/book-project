package com.example.room.service.impl;

import com.example.room.controller.UserController;
import com.example.room.dao.HealthDao;
import com.example.room.entity.HealthInfo;
import com.example.room.entity.VisitorInfo;
import com.example.room.service.HealthService;
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
import java.util.Date;
import java.util.List;

/**
 * @author yangna
 * @date 2019/4/9
 */
@Service
public class HealthServiceImpl implements HealthService {
    @Autowired
    private HealthDao healthDao;
    @Autowired
    private UserController userController;

    /**
     * 添加卫生检查信息
     *
     * @param healthInfo
     * @return
     */
    @Override
    public int addHealth(HealthInfo healthInfo) {
        //封装参数
        healthInfo.setId(UUIDGenerator.getUUID());
        healthInfo.setCreateTime(new Date());
        healthInfo.setCreateUser(userController.getUser());
        healthInfo.setUpdateTime(new Date());
        healthInfo.setUpdateUser(userController.getUser());
        return healthDao.addHealth(healthInfo);
    }

    /**
     * 修改卫生检查情况
     *
     * @param healthInfo
     * @return
     */
    @Override
    public int updateHealth(HealthInfo healthInfo) {
        healthInfo.setUpdateTime(new Date());
        healthInfo.setUpdateUser(userController.getUser());
        return healthDao.updateHealth(healthInfo);
    }

    /**
     * 分页查询
     *
     * @param healthInfo
     * @return
     */
    @Override
    public PageInfo<HealthInfo> findHealthForPage(HealthInfo healthInfo) {
        PageHelper.startPage(healthInfo.getPage(), healthInfo.getSize());
        PageInfo<HealthInfo> pageInfo = new PageInfo<>(healthDao.findHealthForPage(healthInfo));
        return pageInfo;
    }

    /**
     * 导出卫生检查情况
     * @param response
     */
    @Override
    public void exportHealth(HttpServletResponse response) {
        HealthInfo healthInfo = new HealthInfo();
        //获取到学生列表
        List<HealthInfo> healthInfos = healthDao.findHealthForPage(healthInfo);
        String header[] = {"宿舍名称","检查日期","检查分数","备注"};
        String title = "卫生检查信息表";
        String fileName = "卫生检查信息表";
        int rowNum = 1;
        HSSFWorkbook workbook = ExcelUtils.exportExcel(title,header);
        HSSFSheet sheet = workbook.getSheet(title);
        CellStyle cellStyle = ExcelUtils.getCellStyle(workbook);
        for (HealthInfo e : healthInfos) {
            HSSFRow rows = sheet.createRow(rowNum);
            ExcelUtils.addCell(rows, 0,e.getRoomCode(), cellStyle,sheet);
            ExcelUtils.addCell(rows, 1,e.getCheckDate(), cellStyle,sheet);
            ExcelUtils.addCell(rows, 2,e.getCheckPoint().toString(), cellStyle,sheet);
            ExcelUtils.addCell(rows, 3,e.getRemark(), cellStyle,sheet);
            rowNum++;
        };
        ExcelUtils.returnExport(workbook,response,fileName);
    }
}
