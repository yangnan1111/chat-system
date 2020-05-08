package com.imooc.pojo.vo;

public class SendRecord {
    private String send_phone;
    private String recordtime;
    private String time;
    private String cron;

    public String getSend_phone() {
        return send_phone;
    }

    public void setSend_phone(String send_phone) {
        this.send_phone = send_phone;
    }

    public String getRecordtime() {
        return recordtime;
    }

    public void setRecordtime(String recordtime) {
        this.recordtime = recordtime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }
}
