package com.example.room.service;

import com.example.room.common.excel.data.ExcelBaseData;
import com.example.room.entity.ClassInfo;
import com.example.room.entity.CollegeInfo;

import java.util.List;
import java.util.Map;

public interface ExcelCommonService {
    /**
     * 校验学院名称和编码是否一致
     * @param baseDataList
     * @param errorList
     */
    public Map<String,CollegeInfo> checkCellege(List<ExcelBaseData> baseDataList, List<String> errorList);

    /**
     * 校验班级名称是否和编辑是否一致
     * @param baseDataList
     * @param errorList
     */
    public Map<String, ClassInfo> checkClass(List<ExcelBaseData> baseDataList, List<String> errorList);
}
