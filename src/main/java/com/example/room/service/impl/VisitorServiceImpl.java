package com.example.room.service.impl;

import com.example.room.controller.UserController;
import com.example.room.dao.VisitorDao;
import com.example.room.entity.StudentInfo;
import com.example.room.entity.VisitorInfo;
import com.example.room.service.VisitorService;
import com.example.room.utils.common.ExcelUtils;
import com.example.room.utils.common.UUIDGenerator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @author yangna
 * @date 2019/4/2
 */
@Service
public class VisitorServiceImpl implements VisitorService {
    @Autowired
    private VisitorDao visitorDao;
    @Autowired
    private UserController userController;

    /**
     * 新增访问者信息
     *
     * @param visitorInfo
     * @return
     */
    @Override
    public int addVisitor(VisitorInfo visitorInfo) {
        //封装参数
        visitorInfo.setId(UUIDGenerator.getUUID());
        visitorInfo.setCreateTime(new Date());
        visitorInfo.setCreateUser(userController.getUser());
        visitorInfo.setUpdateTime(new Date());
        visitorInfo.setUpdateUser(userController.getUser());
        return visitorDao.addVisitor(visitorInfo);
    }

    /**
     * 更新访问者信息
     *
     * @param visitorInfo
     * @return
     */
    @Override
    public int updateVisitor(VisitorInfo visitorInfo) {
        visitorInfo.setUpdateTime(new Date());
        visitorInfo.setUpdateUser(userController.getUser());
        return visitorDao.updateVisitor(visitorInfo);
    }

    /**
     * 分页查询访问者信息
     *
     * @param visitorInfo
     * @return
     */
    @Override
    public PageInfo<VisitorInfo> findVisitorForPage(VisitorInfo visitorInfo) {
        //分页处理
        PageHelper.startPage(visitorInfo.getPage(), visitorInfo.getSize());
        PageInfo pageInfo = new PageInfo(visitorDao.findVisitorForPage(visitorInfo));
        return pageInfo;
    }

    /**
     * 导出外来人员访问信息
     * @param response
     */
    @Override
    public void exportVisitor(HttpServletResponse response) {
        VisitorInfo visitorInfo = new VisitorInfo();
        //获取到学生列表
        List<VisitorInfo> visitorInfos = visitorDao.findVisitorForPage(visitorInfo);
        String header[] = {"人员姓名","身份证号","手机号","接待人","开始时间","结束时间","备注"};
        String title = "外来人员访问登记表";
        String fileName = "外来人员访问登记表";
        int rowNum = 1;
        HSSFWorkbook workbook = ExcelUtils.exportExcel(title,header);
        HSSFSheet sheet = workbook.getSheet(title);
        for (VisitorInfo e : visitorInfos) {
            HSSFRow rows = sheet.createRow(rowNum);
            rows.createCell(0).setCellValue(e.getVisitorName());
            rows.createCell(1).setCellValue(e.getIdentityCode());
            rows.createCell(2).setCellValue(e.getPhoneNumber());
            rows.createCell(3).setCellValue(e.getReceptName());
            rows.createCell(4).setCellValue(e.getStartTime());
            rows.createCell(5).setCellValue(e.getEndTime());
            rows.createCell(6).setCellValue(e.getRemark());
            rowNum++;
        };
        ExcelUtils.returnExport(workbook,response,fileName);
    }
}
