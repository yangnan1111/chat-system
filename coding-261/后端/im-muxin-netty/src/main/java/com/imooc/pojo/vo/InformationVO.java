package com.imooc.pojo.vo;

import org.springframework.web.multipart.MultipartFile;

public class InformationVO {
    private String age;
    private String address;
    private String profession;
    private String signname;
    private String background;
    private String face_image_big;
    private String nickname;
    private String gender;


    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getSignname() {
        return signname;
    }

    public void setSignname(String signname) {
        this.signname = signname;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getFace_image_big() {
        return face_image_big;
    }

    public void setFace_image_big(String face_image_big) {
        this.face_image_big = face_image_big;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
