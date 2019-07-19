package com.cz.bean;

public class LoginLog_Bean {

    private int id;
    private String account;
    private String operation_time; //登录时间
    private int login_type; //1是自动登录，2是手动，3是自动退出
    private String record;  //返回的参数


    public LoginLog_Bean(){}

    public LoginLog_Bean(String account,String operation_time,int login_type,String record){
        this.account=account;
        this.operation_time=operation_time;
        this.login_type=login_type;
        this.record=record;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getAccount() {
        return account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public String getOperation_time() {
        return operation_time;
    }
    public void setOperation_time(String operation_time) {
        this.operation_time = operation_time;
    }
    public int getLogin_type() {
        return login_type;
    }
    public void setLogin_type(int login_type) {
        this.login_type = login_type;
    }
    public String getRecord() {
        return record;
    }
    public void setRecord(String record) {
        this.record = record;
    }


    @Override
    public String toString() {
        return "LoginLog_Bean{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", operation_time='" + operation_time + '\'' +
                ", login_type=" + login_type +
                ", record='" + record + '\'' +
                '}';
    }
}
