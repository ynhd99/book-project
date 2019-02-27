package com.example.book.utils.common;

import org.apache.commons.text.StringEscapeUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * 字符串工具类, 继承org.apache.commons.lang3.StringUtils类
 *
 * @author 志
 */
public final class StringUtils extends org.apache.commons.lang3.StringUtils {
    private StringUtils() {
        throw new AssertionError();
    }

    private static final Pattern PATTERN_INTEGER = Pattern.compile("[0-9]*");

    private static final Pattern PATTERN_MOBILE = Pattern.compile("^[1][3,4,5,8][0-9]{9}$");


    private static final Pattern PATTERN_PHONE_QUHAO = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");  // 验证带区号的

    private static final Pattern PATTERN_PHONE_NO_QUHAO = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");         // 验证没有区号的

    public static String lowerFirst(String str) {
        if (StringUtils.isBlank(str)) {
            return "";
        } else {
            return str.substring(0, 1).toLowerCase() + str.substring(1);
        }
    }

    public static String upperFirst(String str) {
        if (StringUtils.isBlank(str)) {
            return "";
        } else {
            return str.substring(0, 1).toUpperCase() + str.substring(1);
        }
    }

    /**
     * 替换掉HTML标签方法
     */
    public static String replaceHtml(String html) {
        if (isBlank(html)) {
            return "";
        }
        String regEx = "<.+?>";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(html);
        String s = m.replaceAll("");
        return s;
    }

    /**
     * 缩略字符串（不区分中英文字符）
     *
     * @param str    目标字符串
     * @param length 截取长度
     * @return
     */
    public static String abbr(String str, int length) {
        if (str == null) {
            return "";
        }
        try {
            StringBuilder sb = new StringBuilder();
            int currentLength = 0;
            for (char c : replaceHtml(StringEscapeUtils.unescapeHtml4(str)).toCharArray()) {
                currentLength += String.valueOf(c).getBytes("GBK").length;
                if (currentLength <= length - 3) {
                    sb.append(c);
                } else {
                    sb.append("...");
                    break;
                }
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 缩略字符串（替换html）
     *
     * @param str    目标字符串
     * @param length 截取长度
     * @return
     */
    public static String rabbr(String str, int length) {
        return abbr(replaceHtml(str), length);
    }

    /**
     * 转换为Double类型
     */
    public static Double toDouble(Object val) {
        if (val == null) {
            return 0D;
        }
        try {
            return Double.valueOf(trim(val.toString()));
        } catch (Exception e) {
            return 0D;
        }
    }

    /**
     * 转换为Float类型
     */
    public static Float toFloat(Object val) {
        return toDouble(val).floatValue();
    }

    /**
     * 转换为Long类型
     */
    public static Long toLong(Object val) {
        return toDouble(val).longValue();
    }

    /**
     * 转换为Integer类型
     */
    public static Integer toInteger(Object val) {
        return toLong(val).intValue();
    }

    /**
     * 获得用户远程地址
     */
    public static String getRemoteAddr(HttpServletRequest request) {
        String remoteAddr = request.getHeader("X-Real-IP");
        if (isNotBlank(remoteAddr)) {
            remoteAddr = request.getHeader("X-Forwarded-For");
        } else if (isNotBlank(remoteAddr)) {
            remoteAddr = request.getHeader("Proxy-Client-IP");
        } else if (isNotBlank(remoteAddr)) {
            remoteAddr = request.getHeader("WL-Proxy-Client-IP");
        }
        return remoteAddr != null ? remoteAddr : request.getRemoteAddr();
    }

    /**
     * 判断是否是数字
     *
     * @param str
     * @return
     */
    public static boolean isInteger(String str) {
        return PATTERN_INTEGER.matcher(str).matches();
    }

    /**
     * @param num
     * @return
     */
    public static int ranmodInt(int num) {
        Random random = new Random();
        int x = random.nextInt(899999);
        x = x + 100000;
        return x;
    }

    /**
     * 过滤特殊字符
     *
     * @param str
     * @return
     * @throws PatternSyntaxException
     */
    public static String stringFilter(String str) throws PatternSyntaxException {
        // 只允许字母和数字
        // String   regEx  =  "[^a-zA-Z0-9]";
        // 清除掉所有特殊字符
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    /**
     * 过滤Excel不允许的特殊字符,以供poi使用 特殊字符有如下'/','\\','?','*',']','[',':','"',"\","<","></",">"
     *
     * @param str
     * @return
     * @throws PatternSyntaxException
     */
    public static String stringFilterForPOI(String str) throws PatternSyntaxException {
        // 只允许字母和数字
        // String   regEx  =  "[^a-zA-Z0-9]";
        // 清除掉所有特殊字符
        String regEx = "[\\[\\]/\\\\?*:<>\"\\|]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    /**
     * 手机号验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isMobile(String str) {
        Matcher m = null;
        boolean b = false;

        m = PATTERN_MOBILE.matcher(str);
        b = m.matches();
        return b;
    }

    /**
     * 电话号码验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isPhone(String str) {
        Matcher m = null;
        boolean b = false;
        if (str.length() > 9) {
            m = PATTERN_PHONE_QUHAO.matcher(str);
            b = m.matches();
        } else {
            m = PATTERN_PHONE_NO_QUHAO.matcher(str);
            b = m.matches();
        }
        return b;
    }

    /**
     * 统计数字或者字符出现的次数
     *
     * @param str
     * @return
     */
    public static TreeMap<Character, Integer> pross(String str) {
        char[] charArray = str.toCharArray();

        TreeMap<Character, Integer> tm = new TreeMap<Character, Integer>();

        for (int x = 0; x < charArray.length; x++) {
            if (!tm.containsKey(charArray[x])) {
                tm.put(charArray[x], 1);
            } else {
                int count = tm.get(charArray[x]) + 1;
                tm.put(charArray[x], count);
            }
        }
        return tm;
    }

    /**
     * 判断出现的字符重复的次数
     *
     * @param c
     */
    public static boolean checkRepeatCharCount(int c, String str) {
        boolean flag = false;
        TreeMap<Character, Integer> res = pross(str);
        Iterator titer = res.entrySet().iterator();
        while (titer.hasNext()) {
            Map.Entry ent = (Map.Entry) titer.next();
            String keyt = ent.getKey().toString();
            String valuet = ent.getValue().toString();
            if (Integer.parseInt(valuet) > c) {
                return true;
            }
        }
        return flag;
    }

}
