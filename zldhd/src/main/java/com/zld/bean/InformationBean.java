package com.zld.bean;



public class InformationBean {

    private String ledip;     //LED屏IP
    private String ip;         //摄像头IP
    private String passtype; //0是入口，1是出口
    private String passname; //通道名
    private String port;         //端口号
    private String cusername; //账号
    private String cpassword;   //密码



    public String getLedip() {
        return ledip;
    }
    public void setLedip(String ledip) {
        this.ledip = ledip;
    }
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    /**
     * @return 0是入口，1是出口
     */
    public String getPasstype() {
        return passtype;
    }
    public void setPasstype(String passtype) {
        this.passtype = passtype;
    }
    public String getPassname() {
        return passname;
    }
    public void setPassname(String passname) {
        this.passname = passname;
    }
    public String getPort() {
        return port;
    }
    public void setPort(String port) {
        this.port = port;
    }
    public String getCusername() {
        return cusername;
    }
    public void setCusername(String cusername) {
        this.cusername = cusername;
    }
    public String getCpassword() {
        return cpassword;
    }
    public void setCpassword(String cpassword) {
        this.cpassword = cpassword;
    }

    @Override
    public String toString() {
        return "InformationBean{" +
                "ledip='" + ledip + '\'' +
                ", ip='" + ip + '\'' +
                ", passtype='" + passtype + '\'' +
                ", passname='" + passname + '\'' +
                ", port='" + port + '\'' +
                ", cusername='" + cusername + '\'' +
                ", cpassword='" + cpassword + '\'' +
                '}';
    }
}
