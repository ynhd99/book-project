package com.example.room.common.excel.task;

import com.example.room.common.excel.AbstractBaseExcelImportTask;
import com.example.room.common.excel.ExcelConstants;
import com.example.room.common.excel.ExcelImportMessage;
import com.example.room.common.excel.ExcelMetaData;
import com.example.room.common.excel.util.POIUtil;
import com.example.room.common.exception.SaleBusinessException;
import com.example.room.controller.UserController;
import com.example.room.dao.RoomDao;
import com.example.room.dao.RoomDetailDao;
import com.example.room.dao.StudentDao;
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
public class SaleRoomDetailExcelImportTask extends AbstractBaseExcelImportTask {
    private static Logger log = LoggerFactory.getLogger(SaleStudentExcelImportTask.class);
    @Autowired
    private RoomDetailDao roomDetailDao;
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private RoomDao roomDao;
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
        map.put(0, new ExcelMetaData("宿舍号", "roomCode", true, ExcelConstants.BASE_RGE.ROOMCODE, false));
        map.put(1, new ExcelMetaData("学号", "studentCode", true, ExcelConstants.BASE_RGE.CODE));
        map.put(2, new ExcelMetaData("入住日期", "checkDate", true, ExcelConstants.BASE_RGE.DATE));
        map.put(3, new ExcelMetaData("床号", "bedCount", true, ExcelConstants.BASE_RGE.NUMBER));
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
        List<RoomDetailInfo> correctData = POIUtil.cast(o);
        List<String> errorList = excelImportMessage.getErrorList();
        //校验学号在系统中是否已经存在获取到学生信息
        Map<String,StudentInfo> studentInfoMap = getStudentByCodes( correctData, "0", errorList);
        //校验宿舍号在系统中是否已经存在
        Map<String,RoomEntity> roleInfoMap = getRoomByCodes( correctData, "0", errorList);
        //有错误的时候直接返回错误信息
        if (AirUtils.hv(errorList)) {
            return;
        }
        //如果没有错误信息则及进行封装入库
        buildStudentData(correctData,studentInfoMap,roleInfoMap);
        //进行批次入库
        insertTODB(correctData);
    }
    /**
     * 根据客户编码判断客户编码是否已经存在
     *
     * @param col
     * @return
     */
    private Map<String, StudentInfo> getStudentByCodes(List<RoomDetailInfo> roomDetailInfos, String col, List<String> errorList) {
        Map<String, StudentInfo> custormMap = new HashMap<>();
        //对学号进行去空去重
        List<String> codeList = roomDetailInfos.stream().filter(e -> AirUtils.hv(e.getStudentCode())).map(e -> e.getStudentCode()).distinct().collect(Collectors.toList());
        if (AirUtils.hv(codeList)) {
            List<StudentInfo> studentInfos = studentDao.getDataByCodes(codeList);
            if (AirUtils.hv(studentInfos)) {
                studentInfos.forEach(e -> {
                    custormMap.put(e.getStudentCode(), e);
                });
                roomDetailInfos.forEach(e -> {
                    if (!AirUtils.hv(custormMap.get(e.getStudentCode()))) {
                        errorList.add("第" + e.getRow() + "行" + "第" + col + "列“学号”在系统中不存在");
                    }
                });
            }
        }
        return custormMap;
    }
    /**
     * 根据客户编码判断客户编码是否已经存在
     *
     * @param col
     * @return
     */
    private Map<String,RoomEntity> getRoomByCodes(List<RoomDetailInfo> roomEntityList, String col, List<String> errorList) {
        Map<String, RoomEntity> custormMap = new HashMap<>();
        //对学号进行去空去重
        List<String> codeList = roomEntityList.stream().filter(e -> AirUtils.hv(e.getRoomCode())).map(e -> e.getRoomCode()).distinct().collect(Collectors.toList());
        if (AirUtils.hv(codeList)) {
            List<RoomEntity> studentInfos = roomDao.getDataByCodes(codeList);
            if (AirUtils.hv(studentInfos)) {
                studentInfos.forEach(e -> {
                    custormMap.put(e.getRoomCode(), e);
                });
                roomEntityList.forEach(e -> {
                    if (AirUtils.hv(custormMap.get(e.getRoomCode()))) {
                        errorList.add("第" + e.getRow() + "行" + "第" + col + "列“宿舍号”在系统中不存在");
                    }
                });
            }
        }
        return custormMap;
    }

    /**
     * 封装学生信息
     *
     * @param roomDetailInfos
     * @param studentInfoMap
     */
    public void buildStudentData(List<RoomDetailInfo> roomDetailInfos, Map<String,StudentInfo> studentInfoMap,Map<String,RoomEntity> roleInfoMap){
        roomDetailInfos.forEach(e->{
            e.setId(UUIDGenerator.getUUID());
            e.setRoomId(roleInfoMap.get(e.getRoomCode()).getId());
            e.setStudentId(studentInfoMap.get(e.getStudentCode()).getId());
            e.setCreateTime(new Date());
            e.setCreateUser(userController.getUser());
            e.setUpdateTime(new Date());
            e.setUpdateUser(userController.getUser());
        });
    }

    /**
     * 批次入库
     *
     * @param roomDetailInfos
     */
    public void insertTODB(List<RoomDetailInfo> roomDetailInfos) {
        int batch = roomDetailInfos.size() / ExcelConstants.BATHC_SIZE;
        int lastIndex;
        //客户档案入库
        for (int i = 0; i <= batch; i++) {
            lastIndex = (i == batch) ? roomDetailInfos.size() : ExcelConstants.BATHC_SIZE * (i + 1);
            //判断是否有数据
            List<RoomDetailInfo> subList = roomDetailInfos.subList(ExcelConstants.BATHC_SIZE * i, lastIndex);
            if (AirUtils.hv(subList)) {
                int size = roomDetailDao.addRoomDetail(subList);
                if (subList.size() != size) {
                    throw new SaleBusinessException("宿舍详情入库失败");
                }
            }
        }
    }
}
