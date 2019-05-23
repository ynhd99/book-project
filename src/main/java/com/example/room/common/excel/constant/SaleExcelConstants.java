package com.example.room.common.excel.constant;

/**
 * @author : scl
 * @Project: scm-sale
 * @Package com.choice.scm.sale.excel.constant
 * @Description: 销售管理excel导入用到的常量数据
 * @date Date : 2019年03月29日 10:17
 */
public class SaleExcelConstants {

    /**
     * 封装数据map 客户key
     */
    public static String CUSTORM_KEY = "custorm";
    /**
     * 封装数据map 物品key
     */
    public static String GOODS_KEY = "goods";
    /**
     * 封装数据map 机构key
     */
    public static String ORG_KEY = "org";

    /**
     * 销售客户档案常量数据
     */
    public class SaleCustormConstants {
        /**
         * 客户编码列
         */
        public  static final  String  CUSTORM_CODE_COLUMN = "1";
    }

    /**
     * 销售价格单常量数据
     */
    public class SalePriceConstants {
        /**
         * 客户编码列
         */
        public static final int CUSTORM_CODE_COLUMN = 4;
        /**
         * 机构编码列
         */
        public static final int ORG_CODE_COLUMN = 2;
        /**
         * 物品编码列
         */
        public static final int GOODS_CODE_COLUMN = 6;
        /**
         * 物品单位列
         */
        public static final int GOODS_UNIT_COLUMN = 0;
    }

    /**
     * 销售关系常量数据
     */
    public class SaleRelationConstants {
        /**
         * 客户编码列
         */
        public static final int CUSTORM_CODE_COLUMN = 4;
        /**
         * 机构编码列
         */
        public static final int ORG_CODE_COLUMN = 2;
        /**
         * 物品编码列
         */
        public static final int GOODS_CODE_COLUMN = 6;
        /**
         * 物品单位列
         */
        public static final int GOODS_UNIT_COLUMN = 0;
    }

    /**
     * 销售顶顶那常量数据
     */
    public class SaleOrderConstants {
        /**
         * 客户编码列
         */
        public static final int CUSTORM_CODE_COLUMN = 4;
        /**
         * 机构编码列
         */
        public static final int ORG_CODE_COLUMN = 2;
        /**
         * 物品编码列
         */
        public static final int GOODS_CODE_COLUMN = 7;
        /**
         * 物品单位列
         */
        public static final int GOODS_UNIT_COLUMN = 10;
    }
}
