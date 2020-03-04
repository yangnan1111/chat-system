package com.imooc.pojo.vo;

/**
 * @Description: 好友请求发送方的信息
 */
public class FriendRequestVO {
	
    private String sendUserId;
    private String sendPhone;
    private String sendFaceImage;
    private String sendNickname;
    
	public String getSendUserId() {
		return sendUserId;
	}
	public void setSendUserId(String sendUserId) {
		this.sendUserId = sendUserId;
	}
	public String getSendPhone() { return sendPhone; }
	public void setSendPhone(String sendPhone) { this.sendPhone = sendPhone; }
	public String getSendFaceImage() {
		return sendFaceImage;
	}
	public void setSendFaceImage(String sendFaceImage) {
		this.sendFaceImage = sendFaceImage;
	}
	public String getSendNickname() {
		return sendNickname;
	}
	public void setSendNickname(String sendNickname) {
		this.sendNickname = sendNickname;
	}
}