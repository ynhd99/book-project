package com.example.room.common.excel;

import lombok.Data;

import java.util.List;

/**
 * excel返回实体类
 */
@Data
public class ExcelImportMessage {
    /**
     * 错误信息
     */
    private List<String> errorList;
    /**
     * 正确list
     */
    private List<Object> correctList;
    /**
     * 成功失败状态,false 导入失败, true 导入成功
     */
    private boolean uploadStatus;
    /**
     * 导入的批量信息,如成功导入20行
     */
    private int importMsg;

}
