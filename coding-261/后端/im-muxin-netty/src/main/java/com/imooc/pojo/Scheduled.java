package com.imooc.pojo;
//{"":"",}
public class Scheduled {
    private String cron_id;
    private String cron;
    private String send_phone;
    private String wav;
    private String phone;
    private String clazzname;
    private String groupname;


    public void setclazzname(String clazz_Name) {
        this.clazzname = clazz_Name;
    }
    public String getGroup() {
        return groupname;
    }

    public void setGroup(String groupName) {
        this.groupname = groupName;
    }


    public String getCron_id() {
        return cron_id;
    }

    public void setCron_id(String cron_id) {
        this.cron_id = cron_id;
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

    public String getWav() {
        return wav;
    }

    public void setWav(String wav) {
        this.wav = wav;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getClassName() {
        return clazzname;
    }
}
