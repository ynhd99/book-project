package com.example.room.utils.common;

import com.example.room.utils.DoubleUtils;
import com.example.room.utils.Reflections;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 公共方法
 *
 * <pre>
 * 注意：
 * 1.hv()表示是否有值
 * 2.eq()表示相等
 * 3.pe()表示打印异常（应该设置一个开关打开异常打印方便程序部署）
 * 4.t*()表示转换成相应的类型
 * 5.Double精度的计算：(1) 价格  (2)价格、数量、金额、税额
 * 6.Double精度计算用的是四舍五入
 * </pre>
 */
public final class AirUtils {
    private AirUtils() {
        throw new AssertionError();
    }

    /**
     * 价格保留小数位
     */
    public static final int PRICE_SCALE = 2;

    /**
     * 数量和金额、税额保留小数位
     */
    public static final int QTY_AMOUNT_RATE_SCALE = 4;

    private static final Pattern PATTERN_NUMBER = Pattern.compile("\\d+$");        // 数字

    private static final Pattern PATTERN_LETTER = Pattern.compile("[a-zA-Z]+$");    // 英文字母

    private static final Pattern PATTERN_FLOAT = Pattern.compile("\\d+.\\d*$");     // 数字包括小数

    private static final Pattern PATTERN_CHINESE = Pattern.compile("[\\u4e00-\\u9fa5]+");   //汉子

    public static final String DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    /**
     * 是否有值 : hasValue
     *
     * @param rs
     * @return
     */
    public static boolean hv(String rs) {
        return rs != null && rs.length() > 0;
    }

    /**
     * 是否有值 : hasValue
     *
     * @param rs
     * @return
     */
    public static boolean hv(Integer rs) {
        return rs != null && rs != 0;
    }

    /**
     * 是否有值 : hasValue
     *
     * @param rs
     * @return
     * @add xiaofeng 2013-03-08
     */
    public static boolean hv(Double rs) {
        return rs != null && rs != 0d;
    }

    /**
     * 是否有值 : hasValue
     *
     * @param rs
     * @return
     * @add xiaofeng 2010-12-15
     */
    public static boolean hv(Date rs) {
        return rs != null;
    }

    /**
     * 是否有值 : hasValue
     *
     * @param rs
     * @return
     */
    public static boolean hv(Long rs) {
        return rs != null && rs != 0L;
    }

    /**
     * 是否有值 : hasValue
     *
     * @param str
     * @return
     */
    public static boolean hv(String[] str) {
        return str != null && str.length > 0;
    }

    /**
     * 是否有值 : hasValue
     *
     * <h1>注意：如果list的第一个元素是null那么返回false</h1>
     *
     * @param list
     * @return
     */
    public static boolean hv(List<?> list) {
        if (list != null && list.size() > 0) {
            if (hv(list.get(0))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否有值 : hasValue
     *
     * @param obj
     * @return
     */
    public static boolean hv(Object obj) {
        return obj != null;
    }

    /***
     * Collcetion了类型是否有值
     * @param coll
     * @return
     */
    public static boolean hv(Collection coll) {
        return coll != null && !coll.isEmpty();
    }

    /**
     * 是否有值 : hasValue
     *
     * @param obj
     * @return
     */
    public static boolean hv(Object[] obj) {
        return obj != null && obj.length > 0;
    }

    /**
     * 是否有值 : hasValue
     *
     * <h1>注意：该方法主要用于判断多个参数同时不为null时才用</h1> <h2>
     * 用法:Scm.hv(obj1,obj2,obj3,...,args)</h2>
     *
     * @param obj  参数1
     * @param args 参数列表
     * @return
     * @add xiaofeng 2010-12-03
     */
    public static boolean hv(Object obj, Object... args) {
        if (!hv(obj)) {
            return false;
        }
        for (Object arg : args) {
            if (!hv(arg)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 其中一个参数是否有值 : oneHasValue
     *
     * @param args 参数列表
     * @return
     * @add xiaofeng 2010-12-15
     */
    public static boolean oneHv(Object... args) {
        for (Object arg : args) {
            if (arg instanceof String) { // 如果类型是字符串特殊处理
                if (hv(AirUtils.ts(arg))) {
                    return true;
                }
            } else {
                if (hv(arg)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 是否相等 : equals
     *
     * <h1>注意：src,dest其中一个值不为null</h1> <h2>用法:Scm.eq(null,1); Scm.eq(1,2);
     * Scm.eq(2,null);等</h2>
     *
     * @param src  源字符串
     * @param dest 目标字符串
     * @return
     * @add xiaofeng 2010-12-03
     */
    public static boolean eq(Object src, Object dest) {
        if (src == null && dest == null) {
            return true;
        }
        return hv(src) ? src.equals(dest) : dest.equals(src);
    }

    /**
     * 转换成String : toString
     *
     * @param obj
     * @return
     */
    public static String ts(Object obj) {
        return hv(obj) ? String.valueOf(obj) : null;
    }

    /**
     * 转换成String : toString
     *
     * @param rs
     * @return
     * @add xiaofeng 2011-02-16
     */
    public static String ts(String rs) {
        return rs == null ? "" : rs;
    }

    /**
     * SQL拼接中单引号处理 : singleQuoteMark
     *
     * @param rs
     * @return
     * @add xiaofeng 2011-02-16
     */
    public static String sqm(String rs) {
        return replace(rs, "'", "''");
    }

    /**
     * 字符串替换
     * <p>
     * 注意：不需要判断rs == null
     *
     * @param rs          原字符串
     * @param target      需要替换的内容
     * @param replacement 替换成的内容
     * @return
     * @add xiaofeng 2011-02-16
     */
    public static String replace(String rs, CharSequence target, CharSequence replacement) {
        return rs == null ? "" : rs.replace(target, replacement);
    }

    /**
     * 转换成Integer : toInteger
     *
     * @param rs
     * @return
     */
    public static Integer ti(String rs) {
        return hv(rs) ? Integer.parseInt(rs) : null;
    }

    /**
     * 转换成有效的Integer类型
     *
     * @param rs
     * @return
     * @add xiaofeng 2010-12-9
     */
    public static Integer ti(Integer rs) {
        return hv(rs) ? rs : 0;
    }

    /**
     * 转换成Integer : toInteger
     *
     * @param rs
     * @return
     * @add xiaofeng 2011-1-19
     */
    public static Integer ti(Long rs) {
        return hv(rs) ? rs.intValue() : null;
    }

    /**
     * 转换成Double : toDouble
     *
     * @param rs
     * @return
     * @add xiaofeng 2010-12-03
     */
    public static Double td(String rs) {
        return hv(rs) ? Double.parseDouble(rs) : null;
    }

    /**
     * 转换成有效的Double类型 : toAmount
     *
     * @param rs
     * @return
     * @add xiaofeng 2010-12-9
     */
    public static Double ta(Double rs) {
        return hv(rs) ? rs : 0.00;
    }

    /**
     * 转换成有效的Double类型 : toAmount
     *
     * @param rs
     * @return
     * @add xiaofeng 2010-12-9
     */
    public static Double ta(Long rs) {
        return hv(rs) ? Double.parseDouble(AirUtils.ts(rs)) : 0.00;
    }

    /**
     * 转换成Long : toLong
     *
     * @param str
     * @return
     */
    public static Long tl(String str) {
        return hv(str) ? Long.parseLong(str) : null;
    }

    /**
     * 转换成Long : toLong
     *
     * @param i
     * @return
     */
    public static Long tl(Integer i, Long dec) {
        return hv(i) ? Long.valueOf(i) : dec;
    }

    /**
     * 转换成Long : toLong
     *
     * @param i
     * @return
     */
    public static Long tl(Integer i) {
        return hv(i) ? Long.valueOf(i) : null;
    }

    /**
     * 转换成有效的Long类型 : toAmount
     *
     * @param rs
     * @return
     * @add xiaofeng 2010-12-9
     */
    public static Long tl(Long rs) {
        return hv(rs) ? rs : 0;
    }

    /**
     * Exception输出 ：printStackTrace
     *
     * @param e
     * @return
     */
    public static void pe(Exception e) {
        e.printStackTrace();
    }

    /**
     * 日期格式 : formatDate
     *
     * @param date
     * @return
     */
    public static String fd(Date date) {
        return fd(date, "yyyy-MM-dd");
    }

    /**
     * 日期相加
     *
     * @param date
     * @param day
     * @return
     */
    public static Date addDate(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, day);
        return calendar.getTime();
    }

    /**
     * 日期格式 : formatDate
     *
     * @param date
     * @param args
     * @return
     */
    public static String fd(Date date, String args) {
        return hv(date) ? new SimpleDateFormat(args).format(date) : null;
    }

    /**
     * 日期格式 : formatDate
     *
     * @param date
     * @param args
     * @return
     */
    public static String fdA(Date date, String args) {
        // date.setHours(23);
        // date.setMinutes(59);
        // date.setSeconds(59);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        // date.setTime(999999);
        return hv(c.getTime()) ? new SimpleDateFormat(args).format(c.getTime()) : null;
    }

    /**
     * 得到一天中的最大时间
     *
     * @param date
     * @return
     */
    public static Date fdA(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        // date.setTime(999999);
        return c.getTime();
    }

    /**
     * 得到一天中的最大时间
     *
     * @param dateString
     * @return
     */
    public static Date maxDayTime(String dateString) {
        Calendar c = Calendar.getInstance();
        c.setTime(str2Date(dateString));
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return c.getTime();
    }

    /**
     * 得到一天中的最小时间
     *
     * @param dateString
     * @return
     */
    public static Date minDayTime(String dateString) {
        Calendar c = Calendar.getInstance();
        c.setTime(str2Date(dateString));
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    /**
     * 返回list.size() : listSize
     *
     * @param list
     * @return
     * @add xiaofeng 2010-12-03
     */
    public static Integer ls(List<Object> list) {
        return hv(list) ? list.size() : 0;
    }

    // Double精度 start

    /**
     * 价格相加 : priceAdd
     *
     * @param v1
     * @param v2
     * @return
     * @add xiaofeng 2010-12-10
     */
    public static Double padd(Double v1, Double v2) {
        return DoubleUtils.add(ta(v1), ta(v2), QTY_AMOUNT_RATE_SCALE);
    }

    /**
     * 价格相减 : priceSub
     *
     * @param v1
     * @param v2
     * @return
     * @add xiaofeng 2010-12-10
     */
    public static Double psub(Double v1, Double v2) {
        return DoubleUtils.sub(ta(v1), ta(v2), QTY_AMOUNT_RATE_SCALE);
    }

    /**
     * 价格相乘 : priceMul
     *
     * @param v1
     * @param v2
     * @return
     * @add xiaofeng 2010-12-10
     */
    public static Double pmui(Double v1, Double v2) {
        return DoubleUtils.mul(ta(v1), ta(v2), QTY_AMOUNT_RATE_SCALE);
    }

    /**
     * 价格相乘 : priceMul2
     *
     * @param v1
     * @param v2
     * @return
     * @add xiaofeng 2010-12-10
     */
    public static Double pmui2(Double v1, Double v2) {
        return DoubleUtils.mul(ta(v1), ta(v2), QTY_AMOUNT_RATE_SCALE);
    }

    /**
     * 价格相除 : priceDiv
     *
     * @param v1
     * @param v2
     * @return
     * @add xiaofeng 2010-12-10
     */
    public static Double pdiv(Double v1, Double v2) {
        return DoubleUtils.div(ta(v1), ta(v2), QTY_AMOUNT_RATE_SCALE);
    }

    /**
     * 价格精度 : priceRound
     *
     * @param v1
     * @return
     * @add xiaofeng 2010-12-10
     */
    public static Double pround(Double v1) {
        return DoubleUtils.round(ta(v1), QTY_AMOUNT_RATE_SCALE);
    }

    /**
     * 价格精度 : priceRound
     *
     * @param v1
     * @return
     * @add xiaofeng 2010-12-10
     */
    public static Double pround(Double v1, Integer scale) {
        return DoubleUtils.round(ta(v1), scale);
    }

    /**
     * 数量和金额、税额相加 : qtyAdd
     *
     * @param v1
     * @param v2
     * @return
     * @add xiaofeng 2010-12-10
     */
    public static Double qadd(Double v1, Double v2) {
        return DoubleUtils.add(ta(v1), ta(v2), QTY_AMOUNT_RATE_SCALE);
    }

    /**
     * 数量和金额、税额相减 : qtySub
     *
     * @param v1
     * @param v2
     * @return
     * @add xiaofeng 2010-12-10
     */
    public static Double qsub(Double v1, Double v2) {
        return DoubleUtils.sub(ta(v1), ta(v2), QTY_AMOUNT_RATE_SCALE);
    }

    /**
     * 数量和金额、税额相乘 : qtyMul
     *
     * @param v1
     * @param v2
     * @return
     * @add xiaofeng 2010-12-10
     */
    public static Double qmui(Double v1, Double v2) {
        return DoubleUtils.mul(ta(v1), ta(v2), QTY_AMOUNT_RATE_SCALE);
    }

    /**
     * 数量和金额、税额相除 : qtyDiv
     *
     * @param v1
     * @param v2
     * @return
     * @add xiaofeng 2010-12-10
     */
    public static Double qdiv(Double v1, Double v2) {
        return DoubleUtils.div(ta(v1), ta(v2), QTY_AMOUNT_RATE_SCALE);
    }

    /**
     * 数量和金额、税额精度 : qtyRound
     *
     * @param v1
     * @return
     * @add xiaofeng 2010-12-10
     */
    public static Double qround(Double v1) {
        return DoubleUtils.round(ta(v1), QTY_AMOUNT_RATE_SCALE);
    }

    /**
     * 转绝对值
     *
     * @param v1
     * @return
     */
    public static Double abs(Double v1) {
        return new BigDecimal(v1).abs().doubleValue();
    }

    /**
     * 凑整
     *
     * @param v1
     * @return
     */
    public static Double ceil(Double v1) {
        return Math.ceil(v1);
    }

    // Double精度 end

    /**
     * 某天星期几
     * <p>
     * 注意：1:星期日 2:星期一 3:星期二 4:星期三 5:星期四 6:星期五 7:星期六
     *
     * @param date
     * @return
     * @add xiaofeng 2010-12-25
     */
    public static Integer getDayOfWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 判断字符串中是否包含中文
     * <p>
     * 注意 : 如果str为null返回false
     *
     * @param str
     * @return
     * @add xiaofeng 2011-1-4
     */
    public static boolean isChinese(String str) {
        return hv(str) && PATTERN_CHINESE.matcher(str).find();
    }

    /**
     * 字符串首字母是否为数字
     * <p>
     * 注意 : 如果str为null返回false
     *
     * @param str
     * @return
     * @add xiaofeng 2011-1-4
     */
    public static boolean isNaN(String str) {
        return hv(str) && Character.isDigit(str.charAt(0));
    }

    /**
     * 字符串首字母是否为字母
     * <p>
     * 注意 : 如果str为null返回false
     *
     * @param str
     * @return
     * @add xiaofeng 2011-1-4
     */
    public static boolean isLetter(String str) {
        return hv(str) && Character.isLetter(str.charAt(0));
    }

    /**
     * 字符串转成日期
     *
     * @param str     日期字符串：例如：2011-11-11
     * @param pattern 日期格式 例如 ： yyyy-MM-dd
     * @return
     * @add xiaofeng 2011-2-18
     */
    public static Date str2Date(String str, String pattern) {
        if (!AirUtils.hv(str)) {
            return null;
        }

        SimpleDateFormat simpledateformat = new SimpleDateFormat(pattern);
        ParsePosition parseposition = new ParsePosition(0);
        Date date = simpledateformat.parse(str, parseposition);
        return date;
    }

    /**
     * 字符串转成日期
     * <p>
     * 注意：日期格式"yyyy-MM-dd"
     *
     * @param str 日期字符串：例如：2011-11-11
     * @return
     * @add xiaofeng 2011-2-18
     */
    public static Date str2Date(String str) {
        return str2Date(str, "yyyy-MM-dd");
    }

    /**
     * 截取
     *
     * @param str
     * @return
     * @author changhl
     * @date Mar 7, 2011 9:47:19 AM
     * @description:
     */
    public static String tn(String str) {
        if (AirUtils.hv(str)) {
            if (str.contains("|")) {
                return str.split("\\|")[1];
            } else {
                return str;
            }
        }
        return str;
    }

    /**
     * bool为true，返回exp1；为false，返回exp2
     *
     * @param bool 原字符串
     * @param exp1
     * @param exp2
     * @return
     * @add xiaofeng 2011-3-9
     */
    public static String nvl(boolean bool, String exp1, String exp2) {
        return bool ? exp1 : exp2;
    }

    /**
     * 得到月末最后一天
     *
     * @param date
     * @return
     * @author changhl
     * @date Mar 28, 2011 3:51:08 PM last day of month
     * @description:
     */
    public static Date ldom(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    /**
     * 得到月初第一天
     *
     * @param date
     * @return
     * @author changhl
     * @date Mar 28, 2011 3:51:08 PM first day of month
     * @description:
     */
    public static Date fdom(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    /**
     * 获取当前日期
     *
     * @return
     */
    public static String getCurrentDate() {
        return AirUtils.fd(new Date());
    }

    /**
     * 获取日期
     *
     * @param date
     * @return
     * @author changhl
     * @date Mar 28, 2011 3:51:08 PM first day of month
     * @description:
     */
    public static int getDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DATE);
    }

    /**
     * 是否数字
     *
     * @param code
     * @return
     */
    public static boolean isDigital(String code) {
        Matcher dMatcher = PATTERN_NUMBER.matcher(code);
        return dMatcher.matches();
    }

    public static boolean isDigital2(String code) {
        Matcher dMatcher = PATTERN_FLOAT.matcher(code);
        Matcher dMatcher1 = PATTERN_NUMBER.matcher(code);
        return dMatcher.matches() || dMatcher1.matches();
    }

    /**
     * 是否英文字母
     *
     * @param code
     * @return
     */
    public static boolean isCharacter(String code) {
        Matcher wMatcher = PATTERN_LETTER.matcher(code);
        return wMatcher.matches();
    }

    /**
     * 格式化金额
     *
     * @param val
     * @return
     */
    public static String formatMoney(Object val) {
        DecimalFormat dFormat = new DecimalFormat("#0.00");
        return dFormat.format(val);
    }

    public static Object getBeanValue(Object obj, String propertyName) {
        if (obj == null) {
            return null;
        }
        String property = propertyName;
        int index = propertyName.indexOf(".");
        if (index > -1) {
            property = propertyName.substring(0, index);
        }
        Object val = Reflections.invokeGetter(obj, property);
        if (index > -1) {
            return getBeanValue(val, propertyName.substring(index + 1));
        } else {
            return val;
        }
    }

    @SuppressWarnings("all")
    public static <E> List<E> getListPropertys(Collection<?> list, String propertyName) {
        List<E> resultList = new ArrayList<E>();
        for (Object obj : list) {
            if (obj == null) {
                continue;
            }
            Object val = AirUtils.getBeanValue(obj, propertyName);
            if (AirUtils.hv(val) && !"".equals(val.toString())) {
                resultList.add((E) val);
            }
        }
        return resultList;
    }

    public static String decodeURI(String code, String encoding) {
        try {
            return java.net.URLDecoder.decode(code, encoding);
        } catch (UnsupportedEncodingException e) {
            AirUtils.pe(e);
        }
        return "";
    }

    /**
     * 两个日期之间相差的天数
     *
     * @param start 较小的时间
     * @param end   较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date start, Date end) {
        start = AirUtils.str2Date(AirUtils.fd(start));
        end = AirUtils.str2Date(AirUtils.fd(end));
        Calendar cal = Calendar.getInstance();
        cal.setTime(start);
        long time1 = cal.getTimeInMillis();
        cal.setTime(end);
        long time2 = cal.getTimeInMillis();
        long betweenDays = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(betweenDays));
    }

    /**
     * 比较两个list的值是否相等
     *
     * @param obj1
     * @param obj2
     * @return true 表示相等,不计较排序
     */
    public static boolean eqListValue(List<? extends Object> obj1, List<? extends Object> obj2) {
        if (null == obj1 || null == obj2) {
            throw new RuntimeException("eqList 不能传入null 对象,请查看.");
        }
        return obj1.containsAll(obj2) && obj2.containsAll(obj1);
    }

    /**
     * 判断字符串是否为数字，小数
     *
     * @param str
     * @return
     */
    public static Boolean isDouble(String str, Integer scale) {
        try {
            // 判断是否为数字
            Double.valueOf(str);
            // 判断小数点后位数
            String[] s = str.split("\\.");
            if (s.length > 1 && s[1].length() > scale) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 判断一个数是否为整数 (如：8.00 是整数，8.01不是整数)
     *
     * @param bigDecimal
     * @return
     * @author zhutong 2108/7/16
     */
    public static int checkIsInteger(BigDecimal bigDecimal) {

        //将传入参数小数点后位数全部舍去，然后再与自身比较，如果相等则说明传入参数为整数
        BigDecimal floor = bigDecimal.setScale(0, BigDecimal.ROUND_DOWN);
        int compileResult = bigDecimal.compareTo(floor);
        if (compileResult == 0) {
            return 1;
        }
        return 0;
    }

    public static String[] list2StringArray(List<String> list) {
        if (null == list) {
            return null;
        }
        int size = list.size();
        if (0 == size) {
            return null;
        }

        String[] res = new String[size];
        res = list.toArray(res);
        return res;
    }

    /**
     * 指定日期范围，并将包含开始结束日期在内的日期按星期分组
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static Map<Integer, List<String>> groupDateRange(Date startDate, Date endDate) {
        if (startDate.after(endDate)) {
            return new HashMap<>();
        } else {
            Map<Integer, List<String>> weekHashMap = new HashMap<>();
            Calendar c = Calendar.getInstance();
            while (!startDate.after(endDate)) {
                c.setTime(startDate);
                if (weekHashMap.containsKey(c.get(Calendar.DAY_OF_WEEK))) {
                    weekHashMap.get(c.get(Calendar.DAY_OF_WEEK)).add(fd(startDate));
                } else {
                    List<String> dateList = new ArrayList<String>();
                    dateList.add(fd(startDate));
                    weekHashMap.put(c.get(Calendar.DAY_OF_WEEK), dateList);
                }
                startDate = addDate(startDate, 1);
            }
            return weekHashMap;
        }
    }

    /**
     * 判断两个字符串，如果src为null，dst为空串，返回true，或dst为null,src为空串返回true
     */
    public static boolean equalsIgnoreNull(String src, String dst) {
        if (StringUtils.isBlank(src)) {
            if (StringUtils.isBlank(dst)) {
                return true;
            } else {
                return false;
            }
        } else {
            if (StringUtils.isNotBlank(dst)) {
                return eq(src, dst);
            } else {
                return false;
            }
        }
    }


    /***
     * List 深度克隆
     * @param srcList
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> List<T> listDeepCopy(List<T> srcList) throws Exception {

        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(srcList);
        out.flush();
        byte[] objBytes = byteOut.toByteArray();
        out.close();

        ByteArrayInputStream byteIn = new ByteArrayInputStream(objBytes);
        ObjectInputStream inStream = new ObjectInputStream(byteIn);
        List<T> destList = (List<T>) inStream.readObject();
        inStream.close();
        return destList;

    }

}