package com.example.room.common.excel.task;

import com.example.room.common.excel.AbstractBaseExcelImportTask;
import com.example.room.common.excel.ExcelConstants;
import com.example.room.common.excel.ExcelImportMessage;
import com.example.room.common.excel.ExcelMetaData;
import com.example.room.common.excel.data.ExcelBaseData;
import com.example.room.common.excel.util.POIUtil;
import com.example.room.common.exception.SaleBusinessException;
import com.example.room.controller.UserController;
import com.example.room.dao.TeacherDao;
import com.example.room.entity.*;
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
public class SaleTeacherExcelImportTask extends AbstractBaseExcelImportTask {
    private static Logger log = LoggerFactory.getLogger(SaleStudentExcelImportTask.class);
    @Autowired
    private TeacherDao teacherDao;
    @Autowired
    private ExcelCommonService excelCommonService;
    @Autowired
    private UserController userController;
    @Override
    public Map<Integer, ExcelMetaData> initMetaData() {
        Map<Integer, ExcelMetaData> map = new LinkedHashMap<>();
        map.put(0, new ExcelMetaData("教职工号", "teacherCode", true, ExcelConstants.BASE_RGE.CODE, false));
        map.put(1, new ExcelMetaData("姓名", "teacherName", true, ExcelConstants.BASE_RGE.NAME));
        map.put(2, new ExcelMetaData("学院", "collegeName", true, ExcelConstants.BASE_RGE.NAME));
        map.put(3, new ExcelMetaData("性别", "teacherSex", true, ExcelConstants.BASE_RGE.SEX));
        map.put(4, new ExcelMetaData("手机号", "teacherPhone", true, ExcelConstants.BASE_RGE.RELATION));
        map.put(5, new ExcelMetaData("角色", "roleName", true, ExcelConstants.BASE_RGE.NAME));
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
        List<TeacherInfo> correctData = POIUtil.cast(o);
        List<String> errorList = excelImportMessage.getErrorList();
        //校验学号在系统中是否已经存在
        getTeacherByCodes( correctData, "0", errorList);
        List<ExcelBaseData> collegeList = new ArrayList<>();
        List<ExcelBaseData> roleList = new ArrayList<>();
        correctData.forEach(e->{
            ExcelBaseData data = new ExcelBaseData();
            data.setCollegeName(e.getCollegeName());
            data.setRow(e.getRow());
            data.setRole(e.getRoleName());
            collegeList.add(data);
            roleList.add(data);
        });
        //校验学院名称是否和编码一致
        Map<String, CollegeInfo> collegeInfoMap = excelCommonService.checkCellege(collegeList,errorList);
        //校验角色是否存在
        Map<String, RoleInfo> roleInfoMap = excelCommonService.checkRole(roleList,errorList);

        //有错误的时候直接返回错误信息
        if (AirUtils.hv(errorList)) {
            return;
        }
        //如果没有错误信息则及进行封装入库
        buildStudentData(correctData,collegeInfoMap,roleInfoMap);
        //进行批次入库
        insertTODB(correctData);
    }

    /**
     * 根据客户编码判断客户编码是否已经存在
     *
     * @param col
     * @return
     */
    private void getTeacherByCodes(List<TeacherInfo> excelTeacherData, String col, List<String> errorList) {
        Map<String, TeacherInfo> custormMap = new HashMap<>();
        //对学号进行去空去重
        List<String> codeList = excelTeacherData.stream().filter(e -> AirUtils.hv(e.getTeacherCode())).map(e -> e.getTeacherCode()).distinct().collect(Collectors.toList());
        if (AirUtils.hv(codeList)) {
            List<TeacherInfo> studentInfos = teacherDao.getDataByCodes(codeList);
            if (AirUtils.hv(studentInfos)) {
                studentInfos.forEach(e -> {
                    custormMap.put(e.getTeacherCode(), e);
                });
                excelTeacherData.forEach(e -> {
                    if (AirUtils.hv(custormMap.get(e.getTeacherCode()))) {
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
    public void buildStudentData(List<TeacherInfo> studentInfos, Map<String,CollegeInfo> collegeInfoMap,Map<String,RoleInfo> roleInfoMap) {
        //封装客户基础信息
        studentInfos.forEach(e->{
            e.setId(UUIDGenerator.getUUID());
            e.setCollegeId(collegeInfoMap.get(e.getCollegeName()).getId());
            e.setRoleId(roleInfoMap.get(e.getRoleName()).getId());
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
    public void insertTODB(List<TeacherInfo> studentInfos) {
        int batch = studentInfos.size() / ExcelConstants.BATHC_SIZE;
        int lastIndex;
        //客户档案入库
        for (int i = 0; i <= batch; i++) {
            lastIndex = (i == batch) ? studentInfos.size() : ExcelConstants.BATHC_SIZE * (i + 1);
            //判断是否有数据
            List<TeacherInfo> subList = studentInfos.subList(ExcelConstants.BATHC_SIZE * i, lastIndex);
            if (AirUtils.hv(subList)) {
                int size = teacherDao.batchAddTeacher(subList);
                if (subList.size() != size) {
                    throw new SaleBusinessException("老师信息入库失败");
                }
            }
        }
    }
}
