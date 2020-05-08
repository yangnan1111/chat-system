package com.imooc.pojo.bo;

import org.springframework.web.multipart.MultipartFile;

public class UsersBO {
    private String phone;
    private String nickname;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
}
