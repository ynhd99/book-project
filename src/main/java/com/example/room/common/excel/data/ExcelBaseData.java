package com.example.room.common.excel.data;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;

/**
 * @author : scl
 * @Project: scm-sale
 * @Package com.choice.scm.sale.excel.data
 * @Description: 销售价格单excel导入接受类
 * @date Date : 2019年03月21日 10:34
 */
@Data
public class ExcelBaseData{
   private String collegeName;
   private String className;
   private int row;
   private String role;
   private String cateName;
   private String builidngName;
   private String goodsName;
}
