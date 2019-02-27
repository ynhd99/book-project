package com.example.book.entity.dto.bas;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yangna
 * @date 2018/11/23
 */
public class Filter implements Serializable {

    private static final long serialVersionUID = 4117838177540807350L;

    public enum Operator {
        /**
         * 等于
         */
        EQ,

        /**
         * 不等于
         */
        NE,

        /**
         * 大于
         */
        GT,

        /**
         * 小于
         */
        LT,

        /**
         * 大于等于
         */
        GE,

        /**
         * 小于等于
         */
        LE,

        /**
         * 相似
         */
        LIKE,

        /**
         * 包含
         */
        IN,

        /**
         * 为Null
         */
        IS_NULL,

        /**
         * 不为Null
         */
        IS_NOT_NULL
    }

    /**
     * 默认是否忽略大小写
     */
    private static final boolean DEFAULT_IGNORE_CASE = false;

    /**
     * 属性
     */
    private String fieldName;

    /**
     * 运算符
     */
    private Operator operator;

    /**
     * 值
     */
    private Object value;

    /**
     * 是否忽略大小写
     */
    private Boolean ignoreCase = DEFAULT_IGNORE_CASE;

    public Filter(String fieldName, Operator operator, Object value) {
        this.fieldName = fieldName;
        this.value = value;
        this.operator = operator;
    }

    /**
     * 初始化一个新创建的Filter对象
     *
     * @param property   属性
     * @param operator   运算符
     * @param value      值
     * @param ignoreCase 忽略大小写
     */
    public Filter(String fieldName, Operator operator, Object value, boolean ignoreCase) {
        this.fieldName = fieldName;
        this.operator = operator;
        this.value = value;
        this.ignoreCase = ignoreCase;
    }

    /**
     * searchParams中key的格式为OPERATOR_FIELDNAME
     */
    public static Map<String, Filter> parse(Map<String, Object> searchParams) {
        Map<String, Filter> filters = new HashMap<String, Filter>();

        for (Map.Entry<String, Object> entry : searchParams.entrySet()) {
            // 过滤掉空值
            String key = entry.getKey();
            Object value = entry.getValue();
            if (StringUtils.isBlank((String) value)) {
                continue;
            }

            // 拆分operator与filedAttribute
            String[] names = StringUtils.split(key, "_");
            if (names.length != 2) {
                throw new IllegalArgumentException(key + " is not a valid search filter name");
            }
            String filedName = names[1];
            Operator operator = Operator.valueOf(names[0]);

            // 创建searchFilter
            Filter filter = new Filter(filedName, operator, value);
            filters.put(key, filter);
        }

        return filters;
    }

    /**
     * 返回等于筛选
     *
     * @param property 属性
     * @param value    值
     * @return 等于筛选
     */
    public static Filter eq(String property, Object value) {
        return new Filter(property, Operator.EQ, value);
    }

    /**
     * 返回等于筛选
     *
     * @param property   属性
     * @param value      值
     * @param ignoreCase 忽略大小写
     * @return 等于筛选
     */
    public static Filter eq(String property, Object value, boolean ignoreCase) {
        return new Filter(property, Operator.EQ, value, ignoreCase);
    }

    /**
     * 返回不等于筛选
     *
     * @param property 属性
     * @param value    值
     * @return 不等于筛选
     */
    public static Filter ne(String property, Object value) {
        return new Filter(property, Operator.NE, value);
    }

    /**
     * 返回不等于筛选
     *
     * @param property   属性
     * @param value      值
     * @param ignoreCase 忽略大小写
     * @return 不等于筛选
     */
    public static Filter ne(String property, Object value, boolean ignoreCase) {
        return new Filter(property, Operator.NE, value, ignoreCase);
    }

    /**
     * 返回大于筛选
     *
     * @param property 属性
     * @param value    值
     * @return 大于筛选
     */
    public static Filter gt(String property, Object value) {
        return new Filter(property, Operator.GT, value);
    }

    /**
     * 返回小于筛选
     *
     * @param property 属性
     * @param value    值
     * @return 小于筛选
     */
    public static Filter lt(String property, Object value) {
        return new Filter(property, Operator.LT, value);
    }

    /**
     * 返回大于等于筛选
     *
     * @param property 属性
     * @param value    值
     * @return 大于等于筛选
     */
    public static Filter ge(String property, Object value) {
        return new Filter(property, Operator.GE, value);
    }

    /**
     * 返回小于等于筛选
     *
     * @param property 属性
     * @param value    值
     * @return 小于等于筛选
     */
    public static Filter le(String property, Object value) {
        return new Filter(property, Operator.LE, value);
    }

    /**
     * 返回相似筛选
     *
     * @param property 属性
     * @param value    值
     * @return 相似筛选
     */
    public static Filter like(String property, Object value) {
        return new Filter(property, Operator.LIKE, value);
    }

    /**
     * 返回包含筛选
     *
     * @param property 属性
     * @param value    值
     * @return 包含筛选
     */
    public static Filter in(String property, Object value) {
        return new Filter(property, Operator.IN, value);
    }

    /**
     * 返回为Null筛选
     *
     * @param property 属性
     * @return 为Null筛选
     */
    public static Filter isNull(String property) {
        return new Filter(property, Operator.IS_NULL, null);
    }

    /**
     * 返回不为Null筛选
     *
     * @param property 属性
     * @return 不为Null筛选
     */
    public static Filter isNotNull(String property) {
        return new Filter(property, Operator.IS_NOT_NULL, null);
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Boolean getIgnoreCase() {
        return ignoreCase;
    }

    public void setIgnoreCase(Boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }
}

