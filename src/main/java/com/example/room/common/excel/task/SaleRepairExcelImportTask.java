package com.example.room.common.excel.task;

import com.example.room.common.excel.AbstractBaseExcelImportTask;
import com.example.room.common.excel.ExcelConstants;
import com.example.room.common.excel.ExcelImportMessage;
import com.example.room.common.excel.ExcelMetaData;
import com.example.room.common.excel.data.ExcelBaseData;
import com.example.room.common.excel.util.POIUtil;
import com.example.room.common.exception.SaleBusinessException;
import com.example.room.controller.UserController;
import com.example.room.dao.RepairDao;
import com.example.room.dao.RoomDao;
import com.example.room.entity.GoodsInfo;
import com.example.room.entity.RepairInfo;
import com.example.room.entity.RoomEntity;
import com.example.room.service.ExcelCommonService;
import com.example.room.utils.common.AirUtils;
import com.example.room.utils.common.DateUtils;
import com.example.room.utils.common.UUIDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
@Service
public class SaleRepairExcelImportTask extends AbstractBaseExcelImportTask {
    private static Logger log = LoggerFactory.getLogger(SaleStudentExcelImportTask.class);
    @Autowired
    private RepairDao repairDao;
    @Autowired
    private ExcelCommonService excelCommonService;
    @Autowired
    private UserController userController;
    @Autowired
    private RoomDao roomDao;
    @Override
    public Map<Integer, ExcelMetaData> initMetaData() {
        Map<Integer, ExcelMetaData> map = new LinkedHashMap<>();
        map.put(0, new ExcelMetaData("宿舍号", "roomCode", true, ExcelConstants.BASE_RGE.ROOMCODE, false));
        map.put(1, new ExcelMetaData("物品名称", "goodsName", true, ExcelConstants.BASE_RGE.NAME));
        map.put(2, new ExcelMetaData("维修时间", "date", true, ExcelConstants.BASE_RGE.DATE));
        map.put(3, new ExcelMetaData("申请人", "repairPerson", true, ExcelConstants.BASE_RGE.NAME));
        map.put(4, new ExcelMetaData("维修备注", "remark", true, ExcelConstants.BASE_RGE.REMARK));
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
        List<RepairInfo> correctData = POIUtil.cast(o);
        List<String> errorList = excelImportMessage.getErrorList();
        //校验教职工在系统中是否已经存在
        Map<String,RoomEntity> roomEntities = getRoomByCodes( correctData, "0", errorList);
        List<ExcelBaseData> excelBaseData = new ArrayList<>();
        correctData.forEach(e->{
            ExcelBaseData excelBaseData1 = new ExcelBaseData();
            excelBaseData1.setGoodsName(e.getGoodsName());
            excelBaseData.add(excelBaseData1);
        });
        Map<String, GoodsInfo> goodsInfoMap = excelCommonService.checkGoods(excelBaseData,errorList);
        //有错误的时候直接返回错误信息
        if (AirUtils.hv(errorList)) {
            return;
        }
        //如果没有错误信息则及进行封装入库
        buildGoodsData(correctData,goodsInfoMap,roomEntities);
        //进行批次入库
        insertTODB(correctData);
    }

    /**
     * 根据宿舍编码判断客户编码是否已经存在
     *
     * @param col
     * @return
     */
    private Map<String, RoomEntity> getRoomByCodes(List<RepairInfo> roomEntityList, String col, List<String> errorList) {
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
            }else{
                errorList.add("宿舍号在系统中全不存在");
            }
        }
        return roomMap;
    }

    /**
     * 封装学生信息
     *
     * @param goodsInfos
     */
    public void buildGoodsData(List<RepairInfo> goodsInfos,Map<String, GoodsInfo> goodsInfoMap,Map<String,RoomEntity> roomEntities) {
        //封装客户基础信息
        goodsInfos.forEach(e->{
            e.setId(UUIDGenerator.getUUID());
            e.setRoomId(roomEntities.get(e.getRoomCode()).getId());
            e.setGoodsId(goodsInfoMap.get(e.getGoodsName()).getId());
            if(AirUtils.hv(e.getDate())){
                e.setRepairDate(DateUtils.string2Date(e.getDate(), DateUtils.FORMAT3));
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
     * @param collegeInfos
     */
    public void insertTODB(List<RepairInfo> collegeInfos) {
        int batch = collegeInfos.size() / ExcelConstants.BATHC_SIZE;
        int lastIndex;
        //客户档案入库
        for (int i = 0; i <= batch; i++) {
            lastIndex = (i == batch) ? collegeInfos.size() : ExcelConstants.BATHC_SIZE * (i + 1);
            //判断是否有数据
            List<RepairInfo> subList = collegeInfos.subList(ExcelConstants.BATHC_SIZE * i, lastIndex);
            if (AirUtils.hv(subList)) {
                int size = repairDao.batchAddRepair(subList);
                if (subList.size() != size) {
                    throw new SaleBusinessException("维修信息入库失败");
                }
            }
        }
    }
}
