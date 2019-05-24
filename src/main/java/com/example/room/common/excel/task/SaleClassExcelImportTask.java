package com.example.room.common.excel.task;

import com.example.room.common.excel.AbstractBaseExcelImportTask;
import com.example.room.common.excel.ExcelConstants;
import com.example.room.common.excel.ExcelImportMessage;
import com.example.room.common.excel.ExcelMetaData;
import com.example.room.common.excel.data.ExcelBaseData;
import com.example.room.common.excel.util.POIUtil;
import com.example.room.common.exception.SaleBusinessException;
import com.example.room.controller.UserController;
import com.example.room.dao.ClassDao;
import com.example.room.entity.ClassInfo;
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
public class SaleClassExcelImportTask extends AbstractBaseExcelImportTask {
    private static Logger log = LoggerFactory.getLogger(SaleStudentExcelImportTask.class);
    @Autowired
    private ClassDao classDao;
    @Autowired
    private ExcelCommonService excelCommonService;
    @Autowired
    private UserController userController;
    @Override
    public Map<Integer, ExcelMetaData> initMetaData() {
        Map<Integer, ExcelMetaData> map = new LinkedHashMap<>();
        map.put(0, new ExcelMetaData("班级编码", "classCode", true, ExcelConstants.BASE_RGE.CODE, false));
        map.put(1, new ExcelMetaData("班级名称", "className", true, ExcelConstants.BASE_RGE.NAME));
        map.put(2, new ExcelMetaData("所属学院", "collegeName", true, ExcelConstants.BASE_RGE.NAME));
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
        List<ClassInfo> correctData = POIUtil.cast(o);
        List<String> errorList = excelImportMessage.getErrorList();
        //校验教职工在系统中是否已经存在
        getClassByCodes( correctData, "0", errorList);
        List<ExcelBaseData> collegeList = new ArrayList<>();
        correctData.forEach(e->{
            ExcelBaseData data = new ExcelBaseData();
            data.setCollegeName(e.getCollegeName());
            data.setRow(e.getRow());
            collegeList.add(data);
        });
        //校验学院名称是否和编码一致
        Map<String, CollegeInfo> collegeInfoMap = excelCommonService.checkCellege(collegeList,errorList);
        //有错误的时候直接返回错误信息
        if (AirUtils.hv(errorList)) {
            return;
        }
        //如果没有错误信息则及进行封装入库
        buildStudentData(correctData,collegeInfoMap);
        //进行批次入库
        insertTODB(correctData);
    }

    /**
     * 根据客户编码判断客户编码是否已经存在
     *
     * @param col
     * @return
     */
    private void getClassByCodes(List<ClassInfo> classInfoList, String col, List<String> errorList) {
        Map<String, ClassInfo> custormMap = new HashMap<>();
        //对学号进行去空去重
        List<String> codeList = classInfoList.stream().filter(e -> AirUtils.hv(e.getClassCode())).map(e -> e.getClassCode()).distinct().collect(Collectors.toList());
        if (AirUtils.hv(codeList)) {
            List<ClassInfo> staffInfos = classDao.getDataByCodes(codeList);
            if (AirUtils.hv(staffInfos)) {
                staffInfos.forEach(e -> {
                    custormMap.put(e.getClassCode(), e);
                });
                classInfoList.forEach(e -> {
                    if (AirUtils.hv(custormMap.get(e.getClassCode()))) {
                        errorList.add("第" + e.getRow() + "行" + "第" + col + "列“学号”与系统已存在的编号重复");
                    }
                });
            }
        }
    }

    /**
     * 封装学生信息
     *
     * @param classInfoList
     */
    public void buildStudentData(List<ClassInfo> classInfoList, Map<String, CollegeInfo> collegeInfoMap) {
        //封装客户基础信息
        classInfoList.forEach(e->{
            e.setId(UUIDGenerator.getUUID());
            e.setCreateTime(new Date());
            e.setCreateUser(userController.getUser());
            e.setCollegeId(collegeInfoMap.get(e.getCollegeName()).getId());
            e.setUpdateTime(new Date());
            e.setUpdateUser(userController.getUser());
        });
    }

    /**
     * 批次入库
     *
     * @param classInfoList
     */
    public void insertTODB(List<ClassInfo> classInfoList) {
        int batch = classInfoList.size() / ExcelConstants.BATHC_SIZE;
        int lastIndex;
        //客户档案入库
        for (int i = 0; i <= batch; i++) {
            lastIndex = (i == batch) ? classInfoList.size() : ExcelConstants.BATHC_SIZE * (i + 1);
            //判断是否有数据
            List<ClassInfo> subList = classInfoList.subList(ExcelConstants.BATHC_SIZE * i, lastIndex);
            if (AirUtils.hv(subList)) {
                int size = classDao.batchAddClass(subList);
                if (subList.size() != size) {
                    throw new SaleBusinessException("班级信息入库失败");
                }
            }
        }
    }
}
