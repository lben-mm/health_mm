package com.itheima.domain;

import java.io.Serializable;
import java.util.Date;

public class OrderInfo implements Serializable {
    private String member; //体检人姓名
    private String setmeal; // 套餐名称
    private Date orderDate; // 体检日期
    private String orderType; //预约类型

    public OrderInfo() {
    }

    public OrderInfo(String member, String setmeal, Date orderDate, String orderType) {
        this.member = member;
        this.setmeal = setmeal;
        this.orderDate = orderDate;
        this.orderType = orderType;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public String getSetmeal() {
        return setmeal;
    }

    public void setSetmeal(String setmeal) {
        this.setmeal = setmeal;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
}
