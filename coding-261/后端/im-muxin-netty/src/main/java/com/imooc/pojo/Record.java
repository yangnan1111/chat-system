package com.imooc.pojo;

import java.util.Date;

public class Record {
    private String id;
    private String phone;
    private String basewav;
    private Date time;
    private String recordtime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBasewav() {
        return basewav;
    }

    public void setBasewav(String basewav) {
        this.basewav = basewav;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getRecordtime() {
        return recordtime;
    }

    public void setRecordtime(String recordtime) {
        this.recordtime = recordtime;
    }
}
