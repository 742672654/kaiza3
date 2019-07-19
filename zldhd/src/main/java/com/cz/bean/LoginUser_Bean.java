package com.cz.bean;

public class LoginUser_Bean {

    private String account;
    private String password;
    private String ip;
    private String login_time;
    private String out_time;


    public LoginUser_Bean(){};

    public LoginUser_Bean(String account0, String password0, String ip0,String login_time0,String out_time0) {
        this.account = account0;
        this.password = password0;
        this.ip = ip0;
        this.login_time=login_time0;
        this.out_time=out_time0;
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
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public String getLogin_time() {
        return login_time;
    }
    public void setLogin_time(String login_time) {
        this.login_time = login_time;
    }
    public String getOut_time() {
        return out_time;
    }
    public void setOut_time(String out_time) {
        this.out_time = out_time;
    }



    @Override
    public String toString() {
        return "UserBean{" +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", ip='" + ip + '\'' +
                ", login_time='" + login_time + '\'' +
                ", out_time='" + out_time + '\'' +
                '}';
    }
}
