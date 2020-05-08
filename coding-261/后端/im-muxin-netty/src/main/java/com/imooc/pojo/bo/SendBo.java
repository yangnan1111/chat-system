package com.imooc.pojo.bo;
//{"userId":"12","cron":"59 25 21 02 04 ? 2020","wav":"0","send_phone":"13516170970"}
public class SendBo {
    private String phone;
    private String cron;
    private String send_phone;
    private String id;

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

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getSend_phone() {
        return send_phone;
    }

    public void setSend_phone(String send_phone) {
        this.send_phone = send_phone;
    }
}
