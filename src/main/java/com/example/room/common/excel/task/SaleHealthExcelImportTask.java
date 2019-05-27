package com.example.room.common.excel.task;

import com.example.room.common.excel.AbstractBaseExcelImportTask;
import com.example.room.common.excel.ExcelConstants;
import com.example.room.common.excel.ExcelImportMessage;
import com.example.room.common.excel.ExcelMetaData;
import com.example.room.common.excel.util.POIUtil;
import com.example.room.common.exception.SaleBusinessException;
import com.example.room.controller.UserController;
import com.example.room.dao.HealthDao;
import com.example.room.dao.RoomDao;
import com.example.room.entity.HealthInfo;
import com.example.room.entity.RoomEntity;
import com.example.room.utils.common.AirUtils;
import com.example.room.utils.common.DateUtils;
import com.example.room.utils.common.UUIDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
@Service
public class SaleHealthExcelImportTask extends AbstractBaseExcelImportTask {
    private static Logger log = LoggerFactory.getLogger(SaleStudentExcelImportTask.class);
    @Autowired
    private RoomDao roomDao;
    @Autowired
    private HealthDao healthDao;
    @Autowired
    private UserController userController;
    @Override
    public Map<Integer, ExcelMetaData> initMetaData() {
        Map<Integer, ExcelMetaData> map = new LinkedHashMap<>();
        map.put(0, new ExcelMetaData("宿舍号", "roomCode", true, ExcelConstants.BASE_RGE.ROOMCODE, false));
        map.put(1, new ExcelMetaData("检查日期", "date", true, ExcelConstants.BASE_RGE.DATE));
        map.put(2, new ExcelMetaData("分数", "point", true, ExcelConstants.BASE_RGE.POINT));
        map.put(3, new ExcelMetaData("备注", "remark", true, ExcelConstants.BASE_RGE.REMARK));
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
        List<HealthInfo> correctData = POIUtil.cast(o);
        List<String> errorList = excelImportMessage.getErrorList();
        //校验学号在系统中是否已经存在
        Map<String, RoomEntity> roomMap = getRoomByCodes( correctData, "0", errorList);
        //有错误的时候直接返回错误信息
        if (AirUtils.hv(errorList)) {
            return;
        }
        //如果没有错误信息则及进行封装入库
        buildHealthData(correctData,roomMap);
        //进行批次入库
        insertTODB(correctData);
    }

    /**
     * 根据客户编码判断客户编码是否已经存在
     *
     * @param col
     * @return
     */
    private Map<String, RoomEntity> getRoomByCodes(List<HealthInfo> roomEntityList, String col, List<String> errorList) {
        Map<String, RoomEntity> roomMap = new HashMap<>();
        //对学号进行去空去重
        List<String> codeList = roomEntityList.stream().filter(e -> AirUtils.hv(e.getRoomCode())).map(e -> e.getRoomCode()).distinct().collect(Collectors.toList());
        if (AirUtils.hv(codeList)) {
            List<RoomEntity> studentInfos = roomDao.getDataByCodes(codeList);
            if (AirUtils.hv(studentInfos)) {
                studentInfos.forEach(e -> {
                    roomMap.put(e.getRoomCode(), e);
                });
                roomEntityList.forEach(e -> {
                    if (!AirUtils.hv(roomMap.get(e.getRoomCode()))) {
                        errorList.add("第" + e.getRow() + "行" + "第" + col + "列“宿舍号”在系统中不存在");
                    }
                });
            }
        }
        return roomMap;
    }

    /**
     * 封装学生信息
     *
     * @param healthInfos
     */
    public void buildHealthData(List<HealthInfo> healthInfos,Map<String, RoomEntity> map) {
        //封装客户基础信息
        healthInfos.forEach(e->{
            e.setId(UUIDGenerator.getUUID());
            e.setRoomId(map.get(e.getRoomCode()).getId());
            e.setCheckDate(DateUtils.string2Date(e.getDate(),DateUtils.FORMAT3));
            e.setCheckPoint(new BigDecimal(e.getPoint()));
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
    public void insertTODB(List<HealthInfo> healthInfos) {
        int batch = healthInfos.size() / ExcelConstants.BATHC_SIZE;
        int lastIndex;
        //客户档案入库
        for (int i = 0; i <= batch; i++) {
            lastIndex = (i == batch) ? healthInfos.size() : ExcelConstants.BATHC_SIZE * (i + 1);
            //判断是否有数据
            List<HealthInfo> subList = healthInfos.subList(ExcelConstants.BATHC_SIZE * i, lastIndex);
            if (AirUtils.hv(subList)) {
                int size = healthDao.batchAddHealth(subList);
                if (subList.size() != size) {
                    throw new SaleBusinessException("卫生检查信息入库失败");
                }
            }
        }
    }
}
