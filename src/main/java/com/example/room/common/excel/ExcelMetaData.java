package com.example.room.common.excel;

import lombok.Data;

/**
 * @author : scl
 * @Project: scm-sale
 * @Package com.choice.scm.sale.excel
 * @Description: excel导入元数据实体
 * @date Date : 2019年03月14日 14:52
 */
@Data
public class ExcelMetaData {
    private String title;
    private String name;
    private boolean required;
    private int minlength;
    private int maxlength;
    private String rgExpression;
    private boolean repeated;

    public ExcelMetaData() {
    }

    public ExcelMetaData(String title, String name, boolean required, String rgExpression) {
        this.title = title;
        this.name = name;
        this.required = required;
        this.rgExpression = rgExpression;
        this.repeated = true;
    }

    public ExcelMetaData(String title, String name, boolean required, String rgExpression, boolean repeated) {
        this.title = title;
        this.name = name;
        this.required = required;
        this.rgExpression = rgExpression;
        this.repeated = repeated;
    }
}
