package com.example.room.service.impl;

import com.example.room.common.exception.SaleBusinessException;
import com.example.room.controller.UserController;
import com.example.room.dao.CollegeDao;
import com.example.room.entity.CollegeInfo;
import com.example.room.entity.StudentInfo;
import com.example.room.service.CollegeService;
import com.example.room.utils.common.AirUtils;
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
 * @date 2019/3/11
 */
@Service
public class CollegeServiceImpl implements CollegeService {
    @Autowired
    private CollegeDao collegeDao;
    @Autowired
    private UserController userController;

    /**
     * 添加学院信息
     *
     * @param collegeInfo
     * @return
     */
    @Override
    public int add(CollegeInfo collegeInfo) {
        if (AirUtils.hv(collegeDao.getDataByCode(collegeInfo.getCollegeCode()))) {
            throw new SaleBusinessException("学院编码已经存在");
        }
        collegeInfo.setId(UUIDGenerator.getUUID());
        collegeInfo.setCreateTime(new Date());
        collegeInfo.setCreateUser(userController.getUser());
        collegeInfo.setUpdateTime(new Date());
        collegeInfo.setUpdateUser(userController.getUser());
        return collegeDao.add(collegeInfo);
    }

    /**
     * 查询学院信息
     *
     * @param collegeInfo
     * @return
     */
    @Override
    public PageInfo<CollegeInfo> findDataForPage(CollegeInfo collegeInfo) {
        //分页查询
        PageHelper.startPage(collegeInfo.getPage(), collegeInfo.getSize());
        PageInfo<CollegeInfo> pageInfo = new PageInfo<>(collegeDao.findDataForPage(collegeInfo));
        return pageInfo;
    }

    /**
     * 更新学院状态
     *
     * @param collegeInfo
     * @return
     */
    @Override
    public int updateStatus(CollegeInfo collegeInfo) {
        collegeInfo.setUpdateTime(new Date());
        collegeInfo.setUpdateUser(userController.getUser());
        return collegeDao.updateStatus(collegeInfo);
    }

    /**
     * 删除学院
     *
     * @param collegeInfo
     * @return
     */
    @Override
    public int delete(CollegeInfo collegeInfo) {
        collegeInfo.setUpdateTime(new Date());
        collegeInfo.setUpdateUser(userController.getUser());
        return collegeDao.delete(collegeInfo);
    }

    /**
     * 修改学院
     * @param collegeInfo
     * @return
     */
    @Override
    public int update(CollegeInfo collegeInfo) {
        collegeInfo.setUpdateTime(new Date());
        collegeInfo.setUpdateUser(userController.getUser());
        return collegeDao.update(collegeInfo);
    }

    /**
     * 导出学院信息
     * @param response
     */
    @Override
    public void exportCollege(HttpServletResponse response) {
        CollegeInfo collegeInfo = new CollegeInfo();
        //获取到学生列表
        List<CollegeInfo> collegeInfos = collegeDao.findDataForPage(collegeInfo);
        String header[] = {"学院编号","学院名称","状态"};
        String title = "学院信息表";
        String fileName = "学院信息表";
        int rowNum = 1;
        HSSFWorkbook workbook = ExcelUtils.exportExcel(title,header);
        HSSFSheet sheet = workbook.getSheet(title);
        for (CollegeInfo e : collegeInfos) {
            HSSFRow rows = sheet.createRow(rowNum);
            rows.createCell(0).setCellValue(e.getCollegeCode());
            rows.createCell(1).setCellValue(e.getCollegeName());
            rows.createCell(2).setCellValue(e.getStatus() == 0?"启用":"停用");
            rowNum++;
        };
        ExcelUtils.returnExport(workbook,response,fileName);
    }
}
