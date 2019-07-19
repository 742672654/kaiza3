package com.cz.bean;

import java.io.Serializable;

//TODO 当前登录的用户
public class UserBean  implements Serializable {


    private String account;
    private String password;

    private String  logontime;//1556524114,
    private String  qr;//qr/c/d624A56249491934901,  //二维码code
    private String  role;        //角色信息   1、管理员  2、收费员  3、财务    4、月卡操作员
    private String  iscancel;   //是否在结算订单时去掉取消按钮,0:否，1是
    private String  notemsg;//,
    private String  mobile;//asdasd,
    private String  cname;//广汇通测试车场111,
    private String  nfc;//0,
    private String  token;//96905e22e26919018880016679ec0090,
    private String  authflag;//1,
    private String  etc;        //0:不支持，1:Ibeacon 2:通道照牌 3:手机照牌
    private String  swipe;      //扫牌设置  0去除   1保留
    private String  name;//管理员,
    private String  isshowepay;//1,
    private String  comid;//21733,
    private String  state;      //用户状态  0正常用户，1禁用，2审核中
    private String  info;//success


    private String uptime; //数据库字段

    public UserBean() {
    }

    public String getLogontime() {
        return logontime;
    }

    public void setLogontime(String logontime) {
        this.logontime = logontime;
    }

    public String getQr() {
        return qr;
    }

    public void setQr(String qr) {
        this.qr = qr;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getIscancel() {
        return iscancel;
    }

    public void setIscancel(String iscancel) {
        this.iscancel = iscancel;
    }

    public String getNotemsg() {
        return notemsg;
    }

    public void setNotemsg(String notemsg) {
        this.notemsg = notemsg;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getNfc() {
        return nfc;
    }

    public void setNfc(String nfc) {
        this.nfc = nfc;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAuthflag() {
        return authflag;
    }

    public void setAuthflag(String authflag) {
        this.authflag = authflag;
    }

    public String getEtc() {
        return etc;
    }

    public void setEtc(String etc) {
        this.etc = etc;
    }

    public String getSwipe() {
        return swipe;
    }

    public void setSwipe(String swipe) {
        this.swipe = swipe;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsshowepay() {
        return isshowepay;
    }

    public void setIsshowepay(String isshowepay) {
        this.isshowepay = isshowepay;
    }

    public String getComid() {
        return comid;
    }

    public void setComid(String comid) {
        this.comid = comid;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUptime() {
        return uptime;
    }

    public void setUptime(String uptime) {
        this.uptime = uptime;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", logontime='" + logontime + '\'' +
                ", qr='" + qr + '\'' +
                ", role='" + role + '\'' +
                ", iscancel='" + iscancel + '\'' +
                ", notemsg='" + notemsg + '\'' +
                ", mobile='" + mobile + '\'' +
                ", cname='" + cname + '\'' +
                ", nfc='" + nfc + '\'' +
                ", token='" + token + '\'' +
                ", authflag='" + authflag + '\'' +
                ", etc='" + etc + '\'' +
                ", swipe='" + swipe + '\'' +
                ", name='" + name + '\'' +
                ", isshowepay='" + isshowepay + '\'' +
                ", comid='" + comid + '\'' +
                ", state='" + state + '\'' +
                ", info='" + info + '\'' +
                ", uptime='" + uptime + '\'' +
                '}';
    }

}
