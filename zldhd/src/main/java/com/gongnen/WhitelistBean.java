package com.gongnen;

import java.util.Date;

public class WhitelistBean {

    private Boolean result;
    private String message;
    private String code;

    private String id;
    private String cph;
    private Date effective_time;
    private String  parking_id;
    private String remarks;


    public Boolean getResult() {
        return result;
    }
    public void setResult(Boolean result) {
        this.result = result;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getCph() {
        return cph;
    }
    public void setCph(String cph) {
        this.cph = cph;
    }
    public Date getEffective_time() {
        return effective_time;
    }
    public void setEffective_time(Date effective_time) {
        this.effective_time = effective_time;
    }
    public String getParking_id() {
        return parking_id;
    }
    public void setParking_id(String parking_id) {
        this.parking_id = parking_id;
    }
    public String getRemarks() {
        return remarks;
    }
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}
