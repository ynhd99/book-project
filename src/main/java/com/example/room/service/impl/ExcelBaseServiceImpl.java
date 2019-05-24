package com.example.room.service.impl;

import com.example.room.common.excel.ExcelImportMessage;
import com.example.room.common.excel.task.*;
import com.example.room.service.ExcelBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ExcelBaseServiceImpl implements ExcelBaseService {
    @Autowired
    private SaleStudentExcelImportTask saleStudentExcelImportTask;
    @Autowired
    private SaleTeacherExcelImportTask saleTeacherExcelImportTask;
    @Autowired
    private SaleStaffExcelImportTask saleStaffExcelImportTask;
    @Autowired
    private SaleCollegeExcelImportTask saleCollegeExcelImportTask;
    @Autowired
    private SaleClassExcelImportTask saleClassExcelImportTask;
    @Autowired
    private SaleRoomExcelImportTask saleRoomExcelImportTask;
    @Autowired
    private SaleRoomDetailExcelImportTask saleRoomDetailExcelImportTask;

    @Override
    public ExcelImportMessage importStudent(MultipartFile file, Class tClass) {
        ExcelImportMessage message = saleStudentExcelImportTask.execute(file, tClass);
        return message;
    }

    @Override
    public ExcelImportMessage importTeacher(MultipartFile file, Class tClass) {
        ExcelImportMessage message = saleTeacherExcelImportTask.execute(file,tClass);
        return message;
    }

    @Override
    public ExcelImportMessage importStaff(MultipartFile file, Class tClass) {
        ExcelImportMessage message = saleStaffExcelImportTask.execute(file, tClass);
        return message;
    }

    @Override
    public ExcelImportMessage importCollege(MultipartFile file, Class tClass) {
        ExcelImportMessage message = saleCollegeExcelImportTask.execute(file, tClass);
        return message;
    }

    @Override
    public ExcelImportMessage importClass(MultipartFile file, Class clzz) {
        ExcelImportMessage message = saleClassExcelImportTask.execute(file, clzz);
        return message;
    }

    @Override
    public ExcelImportMessage importRoom(MultipartFile file, Class tClass) {
        ExcelImportMessage message = saleRoomExcelImportTask.execute(file,tClass);
        return message;
    }

    @Override
    public ExcelImportMessage importRoomDetail(MultipartFile file, Class tClass) {
        ExcelImportMessage message = saleRoomDetailExcelImportTask.execute(file, tClass);
        return message;
    }
}
