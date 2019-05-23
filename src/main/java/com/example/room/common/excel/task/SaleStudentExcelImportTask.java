package com.example.room.common.excel.task;

import com.example.room.common.excel.*;
import com.example.room.common.excel.data.ExcelBaseData;
import com.example.room.common.excel.util.POIUtil;
import com.example.room.common.exception.SaleBusinessException;
import com.example.room.controller.UserController;
import com.example.room.dao.StudentDao;
import com.example.room.entity.ClassInfo;
import com.example.room.entity.CollegeInfo;
import com.example.room.entity.StudentInfo;
import com.example.room.service.ExcelCommonService;
import com.example.room.utils.common.AirUtils;
import com.example.room.utils.common.UUIDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author : scl
 * @Project: scm-sale
 * @Package com.choice.scm.sale.excel
 * @Description: 导入学生信息
 * @date Date : 2019年03月19日 13:30
 */
@Service
public class SaleStudentExcelImportTask extends AbstractBaseExcelImportTask {
    private static Logger log = LoggerFactory.getLogger(SaleStudentExcelImportTask.class);
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private ExcelCommonService excelCommonService;
    @Autowired
    private UserController userController;

    /**
     * 封装对象
     *
     * @return
     */
    @Override
    public Map<Integer, ExcelMetaData> initMetaData() {
        Map<Integer, ExcelMetaData> map = new LinkedHashMap<>();
        map.put(0, new ExcelMetaData("学号", "studentCode", true, ExcelConstants.BASE_RGE.CODE, false));
        map.put(1, new ExcelMetaData("学生姓名", "studentName", true, ExcelConstants.BASE_RGE.NAME));
        map.put(2, new ExcelMetaData("性别", "studentSex", true, ExcelConstants.BASE_RGE.SEX));
        map.put(3, new ExcelMetaData("手机号", "studentPhone", true, ExcelConstants.BASE_RGE.RELATION));
        map.put(4, new ExcelMetaData("学院", "collegeName", true, ExcelConstants.BASE_RGE.NAME));
        map.put(5, new ExcelMetaData("班级", "className", true, ExcelConstants.BASE_RGE.NAME));
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
        List<StudentInfo> correctData = POIUtil.cast(o);
        List<String> errorList = excelImportMessage.getErrorList();
        //校验学号在系统中是否已经存在
        getStudentByCodes( correctData, "0", errorList);
        List<ExcelBaseData> collegeList = new ArrayList<>();
        List<ExcelBaseData> classList = new ArrayList<>();
        correctData.forEach(e->{
            ExcelBaseData data = new ExcelBaseData();
            data.setClassName(e.getClassName());
            data.setClassName(e.getCollegeName());
            data.setRow(e.getRow());
            collegeList.add(data);
            classList.add(data);
        });
        //校验学院名称是否和编码一致
        Map<String, CollegeInfo> collegeInfoMap = excelCommonService.checkCellege(collegeList,errorList);
        //校验班级名称是否和编码一致
        Map<String, ClassInfo> classInfoMap = excelCommonService.checkClass(classList,errorList);
        //有错误的时候直接返回错误信息
        if (AirUtils.hv(errorList)) {
            return;
        }
        //如果没有错误信息则及进行封装入库
         buildStudentData(correctData,collegeInfoMap,classInfoMap);
        //进行批次入库
        insertTODB(correctData);
    }

    /**
     * 根据客户编码判断客户编码是否已经存在
     *
     * @param col
     * @return
     */
    private void getStudentByCodes(List<StudentInfo> excelStudentData, String col, List<String> errorList) {
        Map<String, StudentInfo> custormMap = new HashMap<>();
        //对学号进行去空去重
        List<String> codeList = excelStudentData.stream().filter(e -> AirUtils.hv(e.getStudentCode())).map(e -> e.getStudentCode()).distinct().collect(Collectors.toList());
        if (AirUtils.hv(codeList)) {
            List<StudentInfo> studentInfos = studentDao.getDataByCodes(codeList);
            if (AirUtils.hv(studentInfos)) {
                studentInfos.forEach(e -> {
                    custormMap.put(e.getStudentCode(), e);
                });
                excelStudentData.forEach(e -> {
                    if (AirUtils.hv(custormMap.get(e.getStudentCode()))) {
                        errorList.add("第" + e.getRow() + "行" + "第" + col + "列“学号”与系统已存在的学号重复");
                    }
                });
            }
        }
    }

    /**
     * 封装学生信息
     *
     * @param studentInfos
     * @param collegeInfoMap
     */
    public void buildStudentData(List<StudentInfo> studentInfos, Map<String,CollegeInfo> collegeInfoMap, Map<String,ClassInfo> classInfoMap) {
        //封装客户基础信息
        studentInfos.forEach(e->{
            e.setId(UUIDGenerator.getUUID());
            e.setCollegeId(collegeInfoMap.get(e.getCollegeName()).getId());
            e.setClassId(classInfoMap.get(e.getClassName()).getId());
            e.setCreateTime(new Date());
            e.setCreateUser(userController.getUser());
            e.setUpdateTime(new Date());
            e.setUpdateUser(userController.getUser());
        });
    }

    /**
     * 批次入库
     *
     * @param studentInfos
     */
    public void insertTODB(List<StudentInfo> studentInfos) {
        int batch = studentInfos.size() / ExcelConstants.BATHC_SIZE;
        int lastIndex;
        //客户档案入库
        for (int i = 0; i <= batch; i++) {
            lastIndex = (i == batch) ? studentInfos.size() : ExcelConstants.BATHC_SIZE * (i + 1);
            //判断是否有数据
            List<StudentInfo> subList = studentInfos.subList(ExcelConstants.BATHC_SIZE * i, lastIndex);
            if (AirUtils.hv(subList)) {
                int size = studentDao.batchAddStudent(subList);
                if (subList.size() != size) {
                    throw new SaleBusinessException("客户档案入库失败");
                }
            }
        }
    }
}
