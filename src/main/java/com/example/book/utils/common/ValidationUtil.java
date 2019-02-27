package com.example.book.utils.common;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public class ValidationUtil {

    /**
     * 正则表达式：验证密码 （6到16个字符，大小写字母和数字构成，不能有其他字符）
     */
    private static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}$";

    /**
     * 短信验证码 6位数字
     */
    private static final String REGEX_SMS_AUTH_CODE = "^[0-9]{6}$";

    /**
     * 正则表达式：验证手机号
     */
    private static final String REGEX_MOBILE = "^[1][3,4,5,7,8][0-9]{9}$";

    /**
     * 验证座机号
     */
    private static final String REGEX_PHONE = "^[0][0-9]{9,11}$";

    /**
     * 正则表达式：验证邮箱
     */
    private static final String REGEX_EMAIL = "^([a-z0-9A-Z_-]+[-|\\.]?)+[a-z0-9A-Z_-]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 正则表达式：验证身份证
     */
    private static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";

    /**
     * 正则表达式：验证UUID
     */
    private static final String REGEX_UUID = "^[a-z0-9]{8}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{12}$";

    /**
     * 判断手机号是否合法
     *
     * @param mobilePhoneNumber 手机号
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isValidMobilePhone(String mobilePhoneNumber) {
        if (StringUtils.isBlank(mobilePhoneNumber)) {
            return false;
        }
        return Pattern.matches(REGEX_MOBILE, mobilePhoneNumber);
    }

    /**
     * 验证座机号
     *
     * @param phone
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isValidPhone(String phone) {
        if (StringUtils.isBlank(phone)) {
            return false;
        }
        return Pattern.matches(REGEX_PHONE, phone);
    }

    /**
     * 校验密码
     * （6到16个字符，大小写字母和数字构成，不能有其他字符）
     *
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isValidPassword(String password) {
        if (StringUtils.isBlank(password)) {
            return false;
        }
        return Pattern.matches(REGEX_PASSWORD, password);
    }

    /**
     * 校验手机验证码
     * （6位数字）
     *
     * @param sMSAuthCode
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isValidSMSAuthCode(String sMSAuthCode) {
        if (StringUtils.isBlank(sMSAuthCode)) {
            return false;
        }
        return Pattern.matches(REGEX_SMS_AUTH_CODE, sMSAuthCode);
    }


    /**
     * 校验邮箱
     *
     * @param email
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isValidEmail(String email) {
        if (StringUtils.isBlank(email)) {
            return false;
        }
        return Pattern.matches(REGEX_EMAIL, email);
    }

    /**
     * 校验省份证号
     *
     * @param idNumber
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isValidIDNumber(String idNumber) {
        if (StringUtils.isBlank(idNumber)) {
            return false;
        }
        return Pattern.matches(REGEX_ID_CARD, idNumber);
    }

    /**
     * 字符串长度校验
     *
     * @param chars       字符串
     * @param smallLength 长度 >= smallLength    smallLength 必须大于0
     * @param bigLength   长度 >= bigLength   bigLength 必须大于 smallLength
     * @return 如果长度符合预期则返回true，否则返回false
     */
    public static boolean isRightLength(String chars, long smallLength, long bigLength) {
        if (smallLength > bigLength || smallLength < 0) {
            return false;
        }
        long stringLength = StringUtils.length(chars);
        return smallLength <= stringLength && stringLength <= bigLength;
    }

    /**
     * UUID检测
     */
    public static boolean isUUID(String uuid) {
        if (StringUtils.isBlank(uuid)) {
            return false;
        }
        return Pattern.matches(REGEX_UUID, uuid);
    }

}