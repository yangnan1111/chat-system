package com.imooc.pojo.vo;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class UsersVO {
    private String id;
    private String faceImagebig;
    private String address;
    private String nickname;
    private String gender;


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getFaceImagebig() {
		return faceImagebig;
	}

	public void setFaceImagebig(String faceImagebig) {
		this.faceImagebig = faceImagebig;
	}
}