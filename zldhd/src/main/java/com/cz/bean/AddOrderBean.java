package com.cz.bean;

import java.io.Serializable;

//TODO 上传订单回调参数
public class AddOrderBean implements Serializable {

    private String info;
    private String own;
    private String other;
    private String orderid;
    private String ismonthuser;
    private String preorderid;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getOwn() {
        return own;
    }

    public void setOwn(String own) {
        this.own = own;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getIsmonthuser() {
        return ismonthuser;
    }

    public void setIsmonthuser(String ismonthuser) {
        this.ismonthuser = ismonthuser;
    }

    public String getPreorderid() {
        return preorderid;
    }

    public void setPreorderid(String preorderid) {
        this.preorderid = preorderid;
    }

    @Override
    public String toString() {
        return "addOrderBean{" +
                "info='" + info + '\'' +
                ", own='" + own + '\'' +
                ", other='" + other + '\'' +
                ", orderid='" + orderid + '\'' +
                ", ismonthuser='" + ismonthuser + '\'' +
                ", preorderid='" + preorderid + '\'' +
                '}';
    }

}
