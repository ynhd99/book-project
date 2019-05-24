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

        public static String AMOUNT = "^(?!0(?:\\.0*)?$)\\d{0,9}(?:\\.\\d+)?$";
        /**
         * 联系电话
         */
        public static String PHONE = "^0?(13[0-9]|15[012356789]|17[013678]|18[0-9]|14[57])[0-9]{8}$";
        /**
         * 性别
         */
        public static String SEX = "^(\\u7537|\\u5973)$";
        /**
         * 机构类型
         */
        public static String ORGTYPE = "^(\\u914d\\u9001\\u4e2d\\u5fc3|\\u95e8\\u5e97)$";
        /**
         * 联系人
         */
        public static String RELATION = "^0?(13[0-9]|15[012356789]|17[013678]|18[0-9]|14[57])[0-9]{8}$";
        /**
         * 日期
         */
        public static String DATE = "^((?:2)\\d\\d\\d)-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$";
        public static String CODE = "^[0-9]{2,10}$";
        public static String AUTOCODE = "^[A-z0-9-]{1,50}$";
        public static String NAME = "^[\\u4e00-\\u9fa5A-z0-9]{2,20}$";
        public static String OGNAME = "^[\\s\\S]{1,20}$";
        public static String TAX = "^0\\.([0-9]){1,2}$";
    }

    public static String ROW_NAME = "row";
    public static String SPLIT_SYMBOL = "_";
    public static String DATE_FORMAT_YYYYMMDD = "yyyy\\-mm\\-dd;@";
    public static int BATHC_SIZE = 200;
    public static final int AMOUNT_PRECISION = 2;
}
