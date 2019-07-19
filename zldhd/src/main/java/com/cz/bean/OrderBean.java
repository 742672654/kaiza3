package com.cz.bean;

public class OrderBean {

    private String id;
    private String uuid; //入场全景1，特写2；出场全景3，特写4
    private String account;
    private String in_time;
    private String out_time;
    private String carnumber;


    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getUuid() {
        return uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    public String getAccount() {
        return account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public String getIn_time() {
        return in_time;
    }
    public void setIn_time(String in_time) {
        this.in_time = in_time;
    }
    public String getOut_time() {
        return out_time;
    }
    public void setOut_time(String out_time) {
        this.out_time = out_time;
    }
    public String getCarnumber() {
        return carnumber;
    }
    public void setCarnumber(String carnumber) {
        this.carnumber = carnumber;
    }


    @Override
    public String toString() {
        return "OrderBean{"+
                "id='" + id + '\''+
                ", uuid='" + uuid + '\''+
                ", account='" + account + '\''+
                ", in_time='" + in_time + '\''+
                ", out_time='" + out_time + '\''+
                ", carnumber='" + carnumber + '\''+
                '}';
    }
}