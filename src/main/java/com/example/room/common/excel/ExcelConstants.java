package com.example.room.common.excel;

/**
 * @author : scl
 * @Project: scm-sale
 * @Package com.choice.scm.sale.common.constant
 * @Description: excel导入常量类
 * @date Date : 2019年03月14日 13:50
 */
public class ExcelConstants {

    public static class FILE_TYPE {
        public static String XLS = "xls";
        public static String XLSX = "xlsx";
    }

    /**
     * 基本的正则表达式
     */
    public static class BASE_RGE {
        /**
         * 数量
         */
        public static String NUMBER =  "^[0-9]{0,2}$";
        /**
         * 性别
         */
        public static String SEX = "^(\\u7537|\\u5973)$";
        /**
         * 联系人
         */
        public static String RELATION = "^0?(13[0-9]|15[012356789]|17[013678]|18[0-9]|14[57])[0-9]{8}$";
        /**
         * 日期
         */
        public static String DATE = "^(\\d{4})(-)(\\d{2})(-)(\\d{2})$";
        public static String CODE = "^[0-9]{2,10}$";
        public static String ROOMCODE = "^[0-9A-z]{2,10}$";
        public static String NAME = "^[\\u4e00-\\u9fa5A-z0-9]{2,20}$";
    }

    public static String ROW_NAME = "row";
    public static String DATE_FORMAT_YYYYMMDD = "yyyy\\-mm\\-dd;@";
    public static int BATHC_SIZE = 200;
}
