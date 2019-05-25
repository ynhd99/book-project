package com.example.room.common.excel.task;

import com.example.room.common.excel.AbstractBaseExcelImportTask;
import com.example.room.common.excel.ExcelConstants;
import com.example.room.common.excel.ExcelImportMessage;
import com.example.room.common.excel.ExcelMetaData;
import com.example.room.common.excel.data.ExcelBaseData;
import com.example.room.common.excel.util.POIUtil;
import com.example.room.common.exception.SaleBusinessException;
import com.example.room.controller.UserController;
import com.example.room.dao.RoomDao;
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
public class SaleRoomExcelImportTask extends AbstractBaseExcelImportTask {
    private static Logger log = LoggerFactory.getLogger(SaleStudentExcelImportTask.class);
    @Autowired
    private RoomDao roomDao;
    @Autowired
    private ExcelCommonService excelCommonService;
    @Autowired
    private UserController userController;
    @Override
    public Map<Integer, ExcelMetaData> initMetaData() {
        Map<Integer, ExcelMetaData> map = new LinkedHashMap<>();
        map.put(0, new ExcelMetaData("宿舍号", "roomCode", true, ExcelConstants.BASE_RGE.CODE, false));
        map.put(1, new ExcelMetaData("类别", "cateName", true, ExcelConstants.BASE_RGE.NAME));
        map.put(2, new ExcelMetaData("楼号", "buildingName", true, ExcelConstants.BASE_RGE.NAME));
        map.put(3, new ExcelMetaData("容纳人数", "roomCount", true, ExcelConstants.BASE_RGE.NUMBER));
        map.put(4, new ExcelMetaData("现有人数", "currentCount", true, ExcelConstants.BASE_RGE.NUMBER));
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
        List<RoomEntity> correctData = POIUtil.cast(o);
        List<String> errorList = excelImportMessage.getErrorList();
        //校验学号在系统中是否已经存在
        getRoomByCodes( correctData, "0", errorList);
        List<ExcelBaseData> categoryList = new ArrayList<>();
        List<ExcelBaseData> buildingInfos = new ArrayList<>();
        correctData.forEach(e->{
            ExcelBaseData data = new ExcelBaseData();
            data.setCateName(e.getCateName());
            data.setRow(e.getRow());
            data.setBuilidngName(e.getBuildingName());
            categoryList.add(data);
            buildingInfos.add(data);
        });
        //校验类别名称是否和编码一致
        Map<String, RoomCategory> roomCategoryMap = excelCommonService.checkRoomCate(categoryList,errorList);
        //校验楼号是否存在
        Map<String, BuildingInfo> buildingInfoMap = excelCommonService.checkBuilding(buildingInfos,errorList);

        //有错误的时候直接返回错误信息
        if (AirUtils.hv(errorList)) {
            return;
        }
        //如果没有错误信息则及进行封装入库
        buildStudentData(correctData,roomCategoryMap,buildingInfoMap);
        //进行批次入库
        insertTODB(correctData);
    }

    /**
     * 根据客户编码判断客户编码是否已经存在
     *
     * @param col
     * @return
     */
    private void getRoomByCodes(List<RoomEntity> roomEntityList, String col, List<String> errorList) {
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
                        errorList.add("第" + e.getRow() + "行" + "第" + col + "列“学号”与系统已存在的编号重复");
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
    public void buildStudentData(List<RoomEntity> studentInfos,Map<String, RoomCategory> roomCategoryMap,Map<String, BuildingInfo> buildingInfoMap) {
        //封装客户基础信息
        studentInfos.forEach(e->{
            e.setId(UUIDGenerator.getUUID());
            e.setCateId(roomCategoryMap.get(e.getCateName()).getId());
            e.setBuildingId(buildingInfoMap.get(e.getBuildingName()).getId());
            e.setCreateTime(new Date());
            e.setCreateUser(userController.getUser());
            e.setUpdateTime(new Date());
            e.setUpdateUser(userController.getUser());
        });
    }

    /**
     * 批次入库
     *
     * @param roomEntityList
     */
    public void insertTODB(List<RoomEntity> roomEntityList) {
        int batch = roomEntityList.size() / ExcelConstants.BATHC_SIZE;
        int lastIndex;
        //客户档案入库
        for (int i = 0; i <= batch; i++) {
            lastIndex = (i == batch) ? roomEntityList.size() : ExcelConstants.BATHC_SIZE * (i + 1);
            //判断是否有数据
            List<RoomEntity> subList = roomEntityList.subList(ExcelConstants.BATHC_SIZE * i, lastIndex);
            if (AirUtils.hv(subList)) {
                int size = roomDao.batchAddRoom(subList);
                if (subList.size() != size) {
                    throw new SaleBusinessException("宿舍信息入库失败");
                }
            }
        }
    }
}
