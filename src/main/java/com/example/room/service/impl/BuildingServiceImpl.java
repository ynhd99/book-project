package com.example.room.service.impl;

import com.example.room.common.exception.SaleBusinessException;
import com.example.room.controller.UserController;
import com.example.room.dao.BuildingDao;
import com.example.room.entity.BuildingInfo;
import com.example.room.entity.ClassInfo;
import com.example.room.service.BuildingService;
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
 * @date 2019/3/18
 */
@Service
public class BuildingServiceImpl implements BuildingService {
    @Autowired
    private BuildingDao buildingDao;
    @Autowired
    private UserController userController;

    /**
     * 新增宿舍楼信息
     *
     * @param buildingInfo
     * @return
     */
    @Override
    public int addBuilding(BuildingInfo buildingInfo) {
        //判断编码是否已经存在
        if (AirUtils.hv(buildingDao.getDataByCode(buildingInfo.getBuildingCode()))) {
            throw new SaleBusinessException("宿舍楼编码已经存在");
        }
        //封装参数
        buildingInfo.setId(UUIDGenerator.getUUID());
        buildingInfo.setCreateTime(new Date());
        buildingInfo.setCreateUser(userController.getUser());
        buildingInfo.setUpdateTime(new Date());
        buildingInfo.setUpdateUser(userController.getUser());
        //新增宿舍楼信息
        return buildingDao.addBuilding(buildingInfo);
    }

    /**
     * 分页查询宿舍楼信息
     *
     * @param buildingInfo
     * @return
     */
    @Override
    public PageInfo<BuildingInfo> getBuildingForPage(BuildingInfo buildingInfo) {
        PageHelper.startPage(buildingInfo.getPage(), buildingInfo.getSize());
        PageInfo pageInfo = new PageInfo(buildingDao.getBuildingForPage(buildingInfo));
        return pageInfo;
    }

    /**
     * 更新宿舍楼信息
     *
     * @param buildingInfo
     * @return
     */
    @Override
    public int updateBuilding(BuildingInfo buildingInfo) {
        //封装参数
        buildingInfo.setUpdateTime(new Date());
        buildingInfo.setUpdateUser(userController.getUser());
        return buildingDao.updateBuilding(buildingInfo);
    }

    /**
     * 删除宿舍楼信息
     *
     * @param buildingInfo
     * @return
     */
    @Override
    public int deleteBuilding(BuildingInfo buildingInfo) {
        //封装参数
        buildingInfo.setUpdateTime(new Date());
        buildingInfo.setUpdateUser(userController.getUser());
        //执行删除
        return buildingDao.deleteBuilding(buildingInfo);
    }

    /**
     * 导出宿舍楼信息
     * @param response
     */
    @Override
    public void exportBuilding(HttpServletResponse response) {
        BuildingInfo buildingInfo = new BuildingInfo();
        //获取到学生列表
        List<BuildingInfo> buildingInfos = buildingDao.getBuildingForPage(buildingInfo);
        String header[] = {"宿舍楼号","宿舍楼名称","宿管人员","状态"};
        String title = "宿舍楼信息表";
        String fileName = "宿舍楼信息表";
        int rowNum = 1;
        HSSFWorkbook workbook = ExcelUtils.exportExcel(title,header);
        HSSFSheet sheet = workbook.getSheet(title);
        for (BuildingInfo e : buildingInfos) {
            HSSFRow rows = sheet.createRow(rowNum);
            rows.createCell(0).setCellValue(e.getBuildingCode());
            rows.createCell(1).setCellValue(e.getBuildingName());
            rows.createCell(2).setCellValue(e.getStaffName());
            rows.createCell(3).setCellValue(e.getStatus() == 0?"启用":"停用");
            rowNum++;
        };
        ExcelUtils.returnExport(workbook,response,fileName);
    }
}
