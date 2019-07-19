package com.zld.bean;

// 所有车辆类型
public class CarType {
private String value_no;
private String value_name;

    public String getValue_no() {
        return value_no;
    }

    public void setValue_no(String value_no) {
        this.value_no = value_no;
    }

    public String getValue_name() {
        return value_name;
    }

    public void setValue_name(String value_name) {
        this.value_name = value_name;
    }

    @Override
    public String toString() {
        return "CarType{" +
                "value_no='" + value_no + '\'' +
                ", value_name='" + value_name + '\'' +
                '}';
    }
}
