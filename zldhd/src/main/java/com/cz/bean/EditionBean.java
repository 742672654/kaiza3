package com.cz.bean;

public class EditionBean {

    private boolean existence;
    private String existence_time;

    private String edition;
    private String imei;
    private String toUpdateTime;


    public EditionBean() {}

    public EditionBean(boolean existence, String existence_time, String edition, String imei, String toUpdateTime) {
        this.existence = existence;
        this.existence_time = existence_time;
        this.edition = edition;
        this.imei = imei;
        this.toUpdateTime = toUpdateTime;
    }

    public boolean isExistence() {
        return existence;
    }
    public void setExistence(boolean existence) {
        this.existence = existence;
    }
    public String getExistence_time() {
        return existence_time;
    }
    public void setExistence_time(String existence_time) {
        this.existence_time = existence_time;
    }
    public String getToUpdateTime() {
        return toUpdateTime;
    }
    public void setToUpdateTime(String toUpdateTime) {
        this.toUpdateTime = toUpdateTime;
    }
    public String getEdition() {
        return edition;
    }
    public void setEdition(String edition) {
        this.edition = edition;
    }
    public String getImei() {
        return imei;
    }
    public void setImei(String imei) {
        this.imei = imei;
    }

    @Override
    public String toString() {
        return "EditionBean{" +
                "existence=" + existence +
                ", existence_time='" + existence_time + '\'' +
                ", edition='" + edition + '\'' +
                ", imei='" + imei + '\'' +
                ", toUpdateTime='" + toUpdateTime + '\'' +
                '}';
    }

}

