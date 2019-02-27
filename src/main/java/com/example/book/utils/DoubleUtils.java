package com.example.book.utils;
import com.example.book.utils.common.AirUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Double精度计算
 */
public class DoubleUtils {

    /**
     * 提供精确的加法运算。
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static Double add(Double v1, Double v2, Integer scale) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return round(b1.add(b2).doubleValue(), scale);
    }

    public static Double add(Double v1, Double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    public static BigDecimal add(BigDecimal v1, BigDecimal v2, Integer scale) {
        if (!AirUtils.hv(v1)) {
            v1 = BigDecimal.ZERO;
        }
        if (!AirUtils.hv(v2)) {
            v2 = BigDecimal.ZERO;
        }
        return round(v1.add(v2), scale);
    }


    /**
     * 提供精确的减法运算。
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static Double sub(Double v1, Double v2, Integer scale) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return round(b1.subtract(b2).doubleValue(), scale);
    }

    public static Double sub(Double v1, Double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    public static BigDecimal sub(BigDecimal v1, BigDecimal v2, Integer scale) {
        return round(v1.subtract(v2), scale);
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static Double mul(Double v1, Double v2, Integer scale) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return round(b1.multiply(b2).doubleValue(), scale);
    }

    public static BigDecimal mul(BigDecimal v1, BigDecimal v2, Integer scale) {
        return round(v1.multiply(v2), scale);
    }

    public static Double mul(Double v1, Double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }


    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static Double div(Double v1, Double v2, Integer scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive Integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b2.doubleValue() == 0 ? 0.00 : b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static BigDecimal div(BigDecimal v1, BigDecimal v2, Integer scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive Integer or zero");
        }
        return v2.doubleValue() == 0 ? v2 : v1.divide(v2, scale, BigDecimal.ROUND_HALF_UP);
    }

    public static Double div(Double v1, Double v2) {

        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));

        return b2.doubleValue() == 0 ? 0.00 : b1.divide(b2, 6, BigDecimal.ROUND_HALF_UP).doubleValue();

    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static Double round(Double v, Integer scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive Integer or zero");
        }
        BigDecimal rs = new BigDecimal(Double.toString(v));
        return rs.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static BigDecimal round(BigDecimal v, Integer scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive Integer or zero");
        }
        return v.setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static BigDecimal retBigdecimal(Double v, Integer scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive Integer or zero");
        }
        BigDecimal rs = new BigDecimal(Double.toString(v));
        rs = rs.setScale(scale, BigDecimal.ROUND_HALF_UP);
        return rs;
    }

    /**
     * 计算：含税单价
     * 由于用户和供应商对账、财务对账相对于单价更关心总金额，采购过程中用户录入含税总金额，结合税率计算出不含税总金额，根据采购数量倒挤出不含税单价，这样可以保证最后的金额是准确的，单价会有差异。公式如下:
     * 1、含税金额  ÷  数量  ≈  含税单价
     */
    public static Double calculateUnitPrice(Double goodsAmt, Double unitGoodsQty) {
        BigDecimal goodsAmtB = BigDecimal.valueOf(goodsAmt);
        BigDecimal unitGoodsQtyB = BigDecimal.valueOf(unitGoodsQty);
        return goodsAmtB.divide(unitGoodsQtyB, 4, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * 计算：不含税金额
     * 由于用户和供应商对账、财务对账相对于单价更关心总金额，采购过程中用户录入含税总金额，结合税率计算出不含税总金额，根据采购数量倒挤出不含税单价，这样可以保证最后的金额是准确的，单价会有差异。公式如下:
     * 2、含税金额  ÷  (1+税率)  ≈  不含税金额
     */
    public static Double calculateGoodsAmtNotax(Double goodsAmt, Double taxRatio) {
        BigDecimal goodsAmtB = BigDecimal.valueOf(goodsAmt);
        BigDecimal taxRatioB = BigDecimal.valueOf(null == taxRatio ? 0 : taxRatio);
        return goodsAmtB.divide((BigDecimal.ONE.add(taxRatioB)), 4, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * 计算：不含税单价
     * 由于用户和供应商对账、财务对账相对于单价更关心总金额，采购过程中用户录入含税总金额，结合税率计算出不含税总金额，根据采购数量倒挤出不含税单价，这样可以保证最后的金额是准确的，单价会有差异。公式如下:
     * 3、不含税金额  ÷  数量  ≈  不含税单价
     * 说明：数据计算中间过程不要四舍五入，最终可以保留四舍五入的结果
     */
    public static Double calculateUnitPriceNotax(Double goodsAmt, Double unitGoodsQty, Double taxRatio) {
        BigDecimal goodsAmtB = BigDecimal.valueOf(goodsAmt);
        BigDecimal unitGoodsQtyB = BigDecimal.valueOf(unitGoodsQty);
        BigDecimal taxRatioB = BigDecimal.valueOf(null == taxRatio ? 0 : taxRatio);
        return goodsAmtB.divide((BigDecimal.ONE.add(taxRatioB)), 10, RoundingMode.HALF_UP).divide(unitGoodsQtyB, 4, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * 计算转换金额为2位小数，四舍五入
     *
     * @param goodsAmt
     * @return
     */
    public static Double calculateConvertGoodsAmt(Double goodsAmt) {
        BigDecimal goodsAmtB = BigDecimal.valueOf(goodsAmt);
        return goodsAmtB.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

  /*
    测试calculateUnitPriceNotax方法和calculateGoodsAmtNotax方法中的税率是空的情况下，进行判断
    public static void main(String[] args) {
        Goods  g = new Goods();
        g.setTaxRatio(2d);
      Double d =   DoubleUtils.calculateUnitPriceNotax(10d,2d,new Goods().getTaxRatio() );
      System.out.println(d);
    }*/
}
/*

class Goods {
    private Double taxRatio;

    public Double getTaxRatio() {
        return taxRatio;
    }

    public void setTaxRatio(Double taxRatio) {
        this.taxRatio = taxRatio;
    }
}*/
