package com.example.room.service;

import com.example.room.common.excel.ExcelImportMessage;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.multipart.MultipartFile;

public interface ExcelBaseService {
    ExcelImportMessage importStudent(MultipartFile file,Class tClass);
    ExcelImportMessage importTeacher(MultipartFile file,Class tClass);
    ExcelImportMessage importStaff(MultipartFile file,Class tClass);
    ExcelImportMessage importCollege(MultipartFile file,Class tClass);
    ExcelImportMessage importClass(MultipartFile file,Class tClass);
    ExcelImportMessage importRoom(MultipartFile file,Class tClass);
    ExcelImportMessage importRoomDetail(MultipartFile file,Class tClass);
}
