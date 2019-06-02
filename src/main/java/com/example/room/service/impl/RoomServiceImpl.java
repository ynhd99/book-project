package com.example.room.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.room.common.advice.validatorGroup.UpdateStatus;
import com.example.room.common.exception.SaleBusinessException;
import com.example.room.controller.UserController;
import com.example.room.dao.RoomDao;
import com.example.room.dao.RoomDetailDao;
import com.example.room.dao.StudentDao;
import com.example.room.entity.RoomDetailInfo;
import com.example.room.entity.RoomEntity;
import com.example.room.entity.StudentInfo;
import com.example.room.service.RoomService;
import com.example.room.utils.UUIDUtils;
import com.example.room.utils.common.AirUtils;
import com.example.room.utils.common.ExcelUtils;
import com.example.room.utils.common.UUIDGenerator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yangna
 * @date 2019/2/21
 */
@Service
public class RoomServiceImpl implements RoomService {
    private static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private RoomDao roomDao;
    @Autowired
    private UserController userController;
    @Autowired
    private RoomDetailDao roomDetailDao;
    @Autowired
    private StudentDao studentDao;

    /**
     * 新增宿舍信息
     *
     * @param roomEntity
     * @return
     */
    @Override
    public int addRoom(@Validated RoomEntity roomEntity) {
        //判断仓库编码是否已经存在
        RoomEntity deport = roomDao.getRoomByCode(roomEntity.getRoomCode());
        if (AirUtils.hv(deport)) {
            throw new SaleBusinessException("宿舍号已经存在,请重新输入");
        }
        roomEntity.setId(UUIDGenerator.getUUID());
        roomEntity.setCreateTime(new Date());
        roomEntity.setUpdateTime(new Date());
        roomEntity.setCreateUser(userController.getUser());
        roomEntity.setUpdateUser(userController.getUser());
        log.info("新增宿舍信息，请求参数为:{}", JSONObject.toJSONString(roomEntity));
        return roomDao.addRoom(roomEntity);
    }

    /**
     * 分页查询宿舍档案信息
     *
     * @param roomEntity
     * @return
     */
    @Override
    public PageInfo<RoomEntity> findDataForPage(RoomEntity roomEntity) {
        //分页查询
        PageHelper.startPage(roomEntity.getPage(), roomEntity.getSize());
        List<RoomEntity> roomEntityList = roomDao.findDataForPage(roomEntity);
        List<String> ids = roomEntityList.stream().map(e -> e.getId()).collect(Collectors.toList());
        if (AirUtils.hv(ids)) {
            List<RoomDetailInfo> roomDetailInfos = roomDetailDao.getRoomDetailList(ids);
            buildDetailList(roomEntityList, roomDetailInfos);
        }
        roomEntityList.forEach(e -> {
            e.setCurrentCount(e.getRoomDetailInfoList().size());
        });
        PageInfo<RoomEntity> pageInfo = new PageInfo<>(roomEntityList);
        return pageInfo;
    }

    /**
     * 删除宿舍
     *
     * @param roomEntity
     * @return
     */
    @Override
    public int deleteRoom(RoomEntity roomEntity) {
        //封装参数
        roomEntity.setUpdateTime(new Date());
        roomEntity.setCreateUser(userController.getUser());
//        List<RoomDetailInfo> roomDetailInfos = roomDetailDao.getDetailById(roomEntity.getId());
//        List<String> detailList = roomDetailInfos.stream().map(e -> e.getId()).collect(Collectors.toList());
//        roomDetailDao.deleteRoomDetails(detailList);
//        List<String> studentList = roomDetailInfos.stream().map(e -> e.getStudentId()).collect(Collectors.toList());
//        studentDao.deleteSettleFlags(studentList);
        return roomDao.deleteRoom(roomEntity);
    }

    /**
     * 修改宿舍信息
     *
     * @param roomEntity
     * @return
     */
    @Override
    public int updateRoom(RoomEntity roomEntity) {
        //封装参数
        roomEntity.setUpdateTime(new Date());
        roomEntity.setCreateUser(userController.getUser());
        if (AirUtils.hv(roomEntity.getStatus()) && roomEntity.getStatus() == 1) {
            List<RoomDetailInfo> roomDetailInfos = roomDetailDao.getDetailById(roomEntity.getId());
            List<String> detailList = roomDetailInfos.stream().map(e -> e.getId()).collect(Collectors.toList());
            List<String> studentList = roomDetailInfos.stream().map(e -> e.getStudentId()).collect(Collectors.toList());
            if (AirUtils.hv(detailList) && AirUtils.hv(studentList)) {
                studentDao.deleteSettleFlags(studentList);
                roomDetailDao.deleteRoomDetails(detailList,new Date());
            }
        }
        return roomDao.updateRoom(roomEntity);
    }

    /**
     * 获取宿舍列表
     *
     * @param roomEntity
     * @return
     */
    @Override
    public List<RoomEntity> findRoomList(RoomEntity roomEntity) {
        return roomDao.findRoomList(roomEntity);
    }

    /**
     * 导出宿舍档案
     * @param response
     */
    @Override
    public void exportRoom(HttpServletResponse response) {
        RoomEntity roomEntity = new RoomEntity();
        RoomDetailInfo roomDetailInfo = new RoomDetailInfo();
        //获取到学生列表
        List<RoomEntity> roomEntities = roomDao.findDataForPage(roomEntity);
        List<RoomDetailInfo> roomDetailInfos = roomDetailDao.getRoomDetailForPage(roomDetailInfo);
        String header1[] = {"宿舍号","类别","楼号","容纳人数","现有人数","状态"};
        String header2[] = {"宿舍号","学号","学生姓名","学院","班级","性别","手机号","入住日期","退宿日期","床号"};
        String title1 = "宿舍档案表";
        String title2 = "住宿情况表";
        String fileName = "宿舍分配情况表";
        int rowNum1 = 1;
        int rowNum2 = 1;
        HSSFWorkbook workbook = ExcelUtils.exportExcel(title1,header1);
        HSSFSheet sheet1 = workbook.getSheet(title1);
        HSSFSheet sheet2 = workbook.createSheet(title2);
        HSSFRow row = sheet2.createRow(0);
        CellStyle cellStyle = ExcelUtils.getCellStyle(workbook);
        CellStyle headStyle = ExcelUtils.getHeaderStyle(workbook);
        //设置表头数据
        for (int i = 0;i<header2.length;i++){
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(header2[i]);
            cell.setCellValue(text);
            cell.setCellStyle(headStyle);
        }
        for (RoomEntity e : roomEntities) {
            HSSFRow rows = sheet1.createRow(rowNum1);
            ExcelUtils.addCell(rows, 0,e.getRoomCode(), cellStyle,sheet1);
            ExcelUtils.addCell(rows, 1,e.getCateParentName()+"-"+e.getCateName(), cellStyle,sheet1);
            ExcelUtils.addCell(rows, 2,e.getBuildingName(), cellStyle,sheet1);
            ExcelUtils.addCell(rows, 3,e.getRoomCount(), cellStyle,sheet1);
            ExcelUtils.addCell(rows, 4,e.getCurrentCount() == null?0:e.getCurrentCount(), cellStyle,sheet1);
            ExcelUtils.addCell(rows, 5,e.getStatus() == 0?"启用":"停用", cellStyle,sheet1);
            rowNum1++;
        };
        for (RoomDetailInfo e : roomDetailInfos) {
            HSSFRow rows = sheet2.createRow(rowNum2);
            ExcelUtils.addCell(rows, 0,e.getRoomCode(), cellStyle,sheet2);
            ExcelUtils.addCell(rows, 1,e.getStudentCode(), cellStyle,sheet2);
            ExcelUtils.addCell(rows, 2,e.getStudentName(), cellStyle,sheet2);
            ExcelUtils.addCell(rows, 3,e.getCollegeName(), cellStyle,sheet2);
            ExcelUtils.addCell(rows, 4,e.getClassName(), cellStyle,sheet2);
            ExcelUtils.addCell(rows, 5,e.getStudentSex(), cellStyle,sheet2);
            ExcelUtils.addCell(rows, 6,e.getStudentPhone(), cellStyle,sheet2);
            ExcelUtils.addCell(rows, 7,e.getCheckDate(), cellStyle,sheet2);
            if(AirUtils.hv(e.getDeleteDate())) {
                ExcelUtils.addCell(rows, 8,e.getDeleteDate(), cellStyle,sheet2);
            }
            ExcelUtils.addCell(rows, 9,e.getBedCount(), cellStyle,sheet2);
            rowNum2++;
        };
        ExcelUtils.returnExport(workbook,response,fileName);
    }

    /**
     * 封装参数
     *
     * @param roomEntities
     * @param roomDetailInfos
     */
    private void buildDetailList(List<RoomEntity> roomEntities, List<RoomDetailInfo> roomDetailInfos) {
        roomEntities.forEach(e -> {
            List<RoomDetailInfo> roomDetailInfoList = new ArrayList<>();
            roomDetailInfos.forEach(item -> {
                if (item.getRoomId().equals(e.getId())) {
                    roomDetailInfoList.add(item);
                }
            });
            e.setRoomDetailInfoList(roomDetailInfoList);
        });

    }
}
