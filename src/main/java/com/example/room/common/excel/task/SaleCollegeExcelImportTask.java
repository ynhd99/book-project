package com.example.room.common.excel.task;

import com.example.room.common.excel.AbstractBaseExcelImportTask;
import com.example.room.common.excel.ExcelConstants;
import com.example.room.common.excel.ExcelImportMessage;
import com.example.room.common.excel.ExcelMetaData;
import com.example.room.common.excel.util.POIUtil;
import com.example.room.common.exception.SaleBusinessException;
import com.example.room.controller.UserController;
import com.example.room.dao.CollegeDao;
import com.example.room.entity.CollegeInfo;
import com.example.room.service.ExcelCommonService;
import com.example.room.utils.common.AirUtils;
import com.example.room.utils.common.UUIDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
@Service
public class SaleCollegeExcelImportTask extends AbstractBaseExcelImportTask {
    private static Logger log = LoggerFactory.getLogger(SaleStudentExcelImportTask.class);
    @Autowired
    private CollegeDao collegeDao;
    @Autowired
    private ExcelCommonService excelCommonService;
    @Autowired
    private UserController userController;
    @Override
    public Map<Integer, ExcelMetaData> initMetaData() {
        Map<Integer, ExcelMetaData> map = new LinkedHashMap<>();
        map.put(0, new ExcelMetaData("学院编码", "collegeCode", true, ExcelConstants.BASE_RGE.CODE, false));
        map.put(1, new ExcelMetaData("学院名称", "collegeName", true, ExcelConstants.BASE_RGE.NAME));
        return map;
    }
    /**
     * 执行业务
     *
     * @param excelImportMessage
     * @return
     */
    @Override
    public void businessHandleExcel(ExcelImportMessage excelImportMessage) {
        Object o = excelImportMessage.getCorrectList();
        List<CollegeInfo> correctData = POIUtil.cast(o);
        List<String> errorList = excelImportMessage.getErrorList();
        //校验教职工在系统中是否已经存在
        getCollegeByCodes( correctData, "0", errorList);
        //有错误的时候直接返回错误信息
        if (AirUtils.hv(errorList)) {
            return;
        }
        //如果没有错误信息则及进行封装入库
        buildStudentData(correctData);
        //进行批次入库
        insertTODB(correctData);
    }

    /**
     * 根据客户编码判断客户编码是否已经存在
     *
     * @param col
     * @return
     */
    private void getCollegeByCodes(List<CollegeInfo> excelTeacherData, String col, List<String> errorList) {
        Map<String, CollegeInfo> custormMap = new HashMap<>();
        //对学号进行去空去重
        List<String> codeList = excelTeacherData.stream().filter(e -> AirUtils.hv(e.getCollegeCode())).map(e -> e.getCollegeCode()).distinct().collect(Collectors.toList());
        if (AirUtils.hv(codeList)) {
            List<CollegeInfo> staffInfos = collegeDao.getDataByCodes(codeList);
            if (AirUtils.hv(staffInfos)) {
                staffInfos.forEach(e -> {
                    custormMap.put(e.getCollegeCode(), e);
                });
                excelTeacherData.forEach(e -> {
                    if (AirUtils.hv(custormMap.get(e.getCollegeCode()))) {
                        errorList.add("第" + e.getRow() + "行" + "第" + col + "列“学院编码”与系统已存在的编号重复");
                    }
                });
            }
        }
    }

    /**
     * 封装学生信息
     *
     * @param studentInfos
     */
    public void buildStudentData(List<CollegeInfo> studentInfos) {
        //封装客户基础信息
        studentInfos.forEach(e->{
            e.setId(UUIDGenerator.getUUID());
            e.setCreateTime(new Date());
            e.setCreateUser(userController.getUser());
            e.setUpdateTime(new Date());
            e.setUpdateUser(userController.getUser());
        });
    }

    /**
     * 批次入库
     *
     * @param collegeInfos
     */
    public void insertTODB(List<CollegeInfo> collegeInfos) {
        int batch = collegeInfos.size() / ExcelConstants.BATHC_SIZE;
        int lastIndex;
        //客户档案入库
        for (int i = 0; i <= batch; i++) {
            lastIndex = (i == batch) ? collegeInfos.size() : ExcelConstants.BATHC_SIZE * (i + 1);
            //判断是否有数据
            List<CollegeInfo> subList = collegeInfos.subList(ExcelConstants.BATHC_SIZE * i, lastIndex);
            if (AirUtils.hv(subList)) {
                int size = collegeDao.batchAddCollege(subList);
                if (subList.size() != size) {
                    throw new SaleBusinessException("学院信息入库失败");
                }
            }
        }
    }
}
