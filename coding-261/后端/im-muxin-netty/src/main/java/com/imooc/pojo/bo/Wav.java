package com.imooc.pojo.bo;

import org.springframework.web.multipart.MultipartFile;

public class Wav {

    private String phone;
    private MultipartFile  basewav;
    private String recordtime;

    public MultipartFile getBasewav() {
        return basewav;
    }

    public void setBasewav(MultipartFile basewav) {
        this.basewav = basewav;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRecordtime() {
        return recordtime;
    }

    public void setRecordtime(String recordtime) {
        this.recordtime = recordtime;
    }
//    public String getBasewav() {
//        return basewav;
//    }
//
//    public void setBasewav(String basewav) {
//        this.basewav = basewav;
//    }
}
