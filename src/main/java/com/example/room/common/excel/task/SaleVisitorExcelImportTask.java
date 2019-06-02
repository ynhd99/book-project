package com.example.room.common.excel.task;

import com.example.room.common.excel.AbstractBaseExcelImportTask;
import com.example.room.common.excel.ExcelConstants;
import com.example.room.common.excel.ExcelImportMessage;
import com.example.room.common.excel.ExcelMetaData;
import com.example.room.common.excel.util.POIUtil;
import com.example.room.common.exception.SaleBusinessException;
import com.example.room.controller.UserController;
import com.example.room.dao.VisitorDao;
import com.example.room.entity.VisitorInfo;
import com.example.room.utils.common.AirUtils;
import com.example.room.utils.common.DateUtils;
import com.example.room.utils.common.UUIDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class SaleVisitorExcelImportTask extends AbstractBaseExcelImportTask {
    private static Logger log = LoggerFactory.getLogger(SaleStudentExcelImportTask.class);
    @Autowired
    private VisitorDao visitorDao;
    @Autowired
    private UserController userController;
    @Override
    public Map<Integer, ExcelMetaData> initMetaData() {
        Map<Integer, ExcelMetaData> map = new LinkedHashMap<>();
        map.put(0, new ExcelMetaData("人员姓名", "visitorName", true, ExcelConstants.BASE_RGE.NAME, false));
        map.put(1, new ExcelMetaData("身份证号", "identityCode", true, ExcelConstants.BASE_RGE.IDENTITY));
        map.put(2, new ExcelMetaData("手机号", "phoneNumber", true, ExcelConstants.BASE_RGE.RELATION));
        map.put(3, new ExcelMetaData("接待人", "receptName", true, ExcelConstants.BASE_RGE.NAME));
        map.put(4, new ExcelMetaData("开始时间", "startDate", true, ExcelConstants.BASE_RGE.TIME));
        map.put(5, new ExcelMetaData("结束时间", "endDate", true, ExcelConstants.BASE_RGE.TIME));
        map.put(6, new ExcelMetaData("访问事由", "remark", true, ExcelConstants.BASE_RGE.REMARK));
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
        List<VisitorInfo> correctData = POIUtil.cast(o);
        List<String> errorList = excelImportMessage.getErrorList();
        //有错误的时候直接返回错误信息
        if (AirUtils.hv(errorList)) {
            return;
        }
        //如果没有错误信息则及进行封装入库
        buildVisitorData(correctData);
        //进行批次入库
        insertTODB(correctData);
    }
    /**
     * 封装学生信息
     *
     * @param healthInfos
     */
    public void buildVisitorData(List<VisitorInfo> healthInfos) {
        //封装客户基础信息
        healthInfos.forEach(e->{
            e.setId(UUIDGenerator.getUUID());
            e.setStartTime(DateUtils.string2Date(e.getStartDate(),DateUtils.FORMAT1));
            if(AirUtils.hv(e.getEndDate())){
                e.setEndTime(DateUtils.string2Date(e.getEndDate(),DateUtils.FORMAT1));
            }
            e.setCreateTime(new Date());
            e.setCreateUser(userController.getUser());
            e.setUpdateTime(new Date());
            e.setUpdateUser(userController.getUser());
        });
    }

    /**
     * 批次入库
     *
     * @param healthInfos
     */
    public void insertTODB(List<VisitorInfo> healthInfos) {
        int batch = healthInfos.size() / ExcelConstants.BATHC_SIZE;
        int lastIndex;
        //客户档案入库
        for (int i = 0; i <= batch; i++) {
            lastIndex = (i == batch) ? healthInfos.size() : ExcelConstants.BATHC_SIZE * (i + 1);
            //判断是否有数据
            List<VisitorInfo> subList = healthInfos.subList(ExcelConstants.BATHC_SIZE * i, lastIndex);
            if (AirUtils.hv(subList)) {
                int size = visitorDao.batchAddVisitor(subList);
                if (subList.size() != size) {
                    throw new SaleBusinessException("访问者信息入库失败");
                }
            }
        }
    }
}
