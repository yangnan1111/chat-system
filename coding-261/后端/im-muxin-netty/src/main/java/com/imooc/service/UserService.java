package com.imooc.service;

import java.util.List;

import com.imooc.netty.ChatMsg;
import com.imooc.pojo.Users;
import com.imooc.pojo.vo.FriendRequestVO;
import com.imooc.pojo.vo.InformationVO;
import com.imooc.pojo.vo.MyFriendsVO;
import com.imooc.utils.IMoocJSONResult;

public interface UserService {

	/**
	 * @Description: 判断用户名是否存在
	 */
	public boolean queryUsernameIsExist(String username);
	
	/**
	 * @Description: 查询用户是否存在
	 */
	public Users queryUserForLogin(String username, String pwd);
	
	/**
	 * @Description: 用户注册
	 */
	public Users saveUser(Users user);
	
	/**
	 * @Description: 修改用户记录
	 */
	public IMoocJSONResult updateUserInfo(Users user);

	/**
	 *
	 * 修改年龄
	 */
	public IMoocJSONResult updateAge(Users user);

	/*
	修改性別
	 */
     public IMoocJSONResult updateGender(Users user);
	/**
	 * 修改地址
	 */
	public IMoocJSONResult updateAddress(Users users);
	/**
	 * 修改职业
	 */
	public IMoocJSONResult updateProfession(Users users);
	/**
	 * 修改个性签名
	 */
	public IMoocJSONResult updateSignname(Users users);
	/**
	 * 修改头像
	 */
	public IMoocJSONResult updateface(Users users);
	/**
	 * 修改背景图片
	 */
	public IMoocJSONResult updatebackground(Users users);
	/**
	 * @Description: 搜索朋友的前置条件
	 */
	public Integer preconditionSearchFriends(String myPhone, String friendPhone);
	
	/**
	 * @Description: 根据用户名查询用户对象
	 */
	public Users queryUserInfoByPhone(String username);
	
	/**
	 * @Description: 添加好友请求记录，保存到数据库
	 */
	public void sendFriendRequest(String myPhone, String friendUsername);
	
	/**
	 * @Description: 查询好友请求
	 */
	public List<FriendRequestVO> queryFriendRequestList(String acceptPhone);
	
	/**
	 * @Description: 删除好友请求记录
	 */
	public void deleteFriendRequest(String sendPhone, String acceptPhone);
	
	/**
	 * @Description: 通过好友请求
	 * 				1. 保存好友
	 * 				2. 逆向保存好友
	 * 				3. 删除好友请求记录
	 */
	public void passFriendRequest(String sendPhone, String acceptPhone);
	
	/**
	 * @Description: 查询好友列表
	 */
	public List<MyFriendsVO> queryMyFriends(String phone);
	
	/**
	 * @Description: 保存聊天消息到数据库
	 */
	public String saveMsg(ChatMsg chatMsg);
	
	/**
	 * @Description: 批量签收消息
	 */
	public void updateMsgSigned(List<String> msgIdList);
	
	/**
	 * @Description: 获取未签收消息列表
	 */
	public List<com.imooc.pojo.ChatMsg> getUnReadMsgList(String acceptPhone);

	public Users selectByPhone(String phone);





	//完善个人信息
	public InformationVO selectInfromation(String phone);


	//保存信息
	public void saveInfo(Users users);


	
}
