package com.example.room.entity.dto.bas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yangna
 * @date 2018/11/23
 */
public class Page<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 当前页第页码
     */
    private int page = 1;
    /**
     * 每页的记录数
     */
    private int limit = 10;

    /**
     * 内容
     */
    private List<T> data = new ArrayList<T>();

    /**
     * 总记录数
     */
    private int totalCount;

    /**
     * 总页数
     */
    private int totalPage;

    /**
     * 排序属性
     */
    private String orderProperty;

    /**
     * 排序方向
     */
    private Order.Direction orderDirection;

    /**
     * 筛选
     */
    private List<Filter> filters = new ArrayList<Filter>();

    /**
     * 排序
     */
    private List<Order> orders = new ArrayList<Order>();

    private String remark;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
        this.totalPage = totalCount % limit == 0 ? totalCount / limit : totalCount / limit + 1;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public String getOrderProperty() {
        return orderProperty;
    }

    public void setOrderProperty(String orderProperty) {
        this.orderProperty = orderProperty;
    }

    public Order.Direction getOrderDirection() {
        return orderDirection;
    }

    public void setOrderDirection(Order.Direction orderDirection) {
        this.orderDirection = orderDirection;
    }

    public List<Filter> getFilters() {
        return filters;
    }

    public void setFilters(List<Filter> filters) {
        this.filters = filters;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
