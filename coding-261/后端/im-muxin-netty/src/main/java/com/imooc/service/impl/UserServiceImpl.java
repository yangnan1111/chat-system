package com.imooc.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.imooc.pojo.vo.InformationVO;
import com.imooc.utils.*;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.imooc.enums.MsgActionEnum;
import com.imooc.enums.MsgSignFlagEnum;
import com.imooc.enums.SearchFriendsStatusEnum;
import com.imooc.mapper.ChatMsgMapper;
import com.imooc.mapper.FriendsRequestMapper;
import com.imooc.mapper.MyFriendsMapper;
import com.imooc.mapper.UsersMapper;
import com.imooc.mapper.UsersMapperCustom;
import com.imooc.netty.ChatMsg;
import com.imooc.netty.DataContent;
import com.imooc.netty.UserChannelRel;
import com.imooc.pojo.FriendsRequest;
import com.imooc.pojo.MyFriends;
import com.imooc.pojo.Users;
import com.imooc.pojo.vo.FriendRequestVO;
import com.imooc.pojo.vo.MyFriendsVO;
import com.imooc.service.UserService;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UsersMapper userMapper;
	
	@Autowired
	private UsersMapperCustom usersMapperCustom;
	
	@Autowired
	private MyFriendsMapper myFriendsMapper;
	
	@Autowired
	private FriendsRequestMapper friendsRequestMapper;
	
	@Autowired
	private ChatMsgMapper chatMsgMapper;
	
	@Autowired
	private Sid sid;
	
	@Autowired
	private QRCodeUtils qrCodeUtils;
	
	@Autowired
	private FastDFSClient fastDFSClient;

	@Autowired
	private UserService userService;

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public boolean queryUsernameIsExist(String phone) {
		
		Users user = new Users();
		user.setPhone(phone);
		
		Users result = userMapper.selectOne(user);
		
		return result != null ? true : false;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public Users queryUserForLogin(String username, String pwd) {
		
		Example userExample = new Example(Users.class);
		Criteria criteria = userExample.createCriteria();
		
		criteria.andEqualTo("username", username);
		criteria.andEqualTo("password", pwd);
		
		Users result = userMapper.selectOneByExample(userExample);
		
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public Users saveUser(Users user) {
		
		String userId = sid.nextShort();
		
		// 为每个用户生成一个唯一的二维码
		String qrCodePath = "C://user" + userId + "qrcode.png";
		// muxin_qrcode:[username]
		qrCodeUtils.createQRCode(qrCodePath, "muxin_qrcode:" + user.getPhone());
		MultipartFile qrCodeFile = FileUtils.fileToMultipart(qrCodePath);
		
		String qrCodeUrl = "";
		try {
			qrCodeUrl = fastDFSClient.uploadQRCode(qrCodeFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		user.setQrcode(qrCodeUrl);
		
		user.setId(userId);
		userMapper.insert(user);
		
		return user;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public IMoocJSONResult updateUserInfo(Users user) {
		Users users1=new Users();
		users1=userService.selectByPhone(user.getPhone());
		users1.setNickname(user.getNickname());
		userMapper.updateByPrimaryKey(users1);
		return IMoocJSONResult.ok("修改成功");
	}

	@Override
	public IMoocJSONResult updateAge(Users user) {
		Users users1=new Users();
		users1=userService.selectByPhone(user.getPhone());
		users1.setAge(user.getAge());
		userMapper.updateByPrimaryKey(users1);
		return IMoocJSONResult.ok("修改成功");
	}

	@Override
	public IMoocJSONResult updateGender(Users user) {
		Users users1=new Users();
		users1=userService.selectByPhone(user.getPhone());
		users1.setGender(user.getGender());
		userMapper.updateByPrimaryKey(users1);
		return IMoocJSONResult.ok("修改成功");
	}

	@Override
	public IMoocJSONResult updateAddress(Users users) {
		Users users1=new Users();
		users1=userService.selectByPhone(users.getPhone());
		users1.setAddress(users.getAddress());
		userMapper.updateByPrimaryKey(users1);
		return IMoocJSONResult.ok("修改成功");
	}

	@Override
	public IMoocJSONResult updateProfession(Users users) {
		Users users1=new Users();
		users1=userService.selectByPhone(users.getPhone());
		users1.setProfession(users.getProfession());
		userMapper.updateByPrimaryKey(users1);
		return IMoocJSONResult.ok("修改成功");
	}

	@Override
	public IMoocJSONResult updateSignname(Users users) {
		Users users1=new Users();
		users1=userService.selectByPhone(users.getPhone());
		users1.setSignname(users.getSignname());
		userMapper.updateByPrimaryKey(users1);
		return IMoocJSONResult.ok("修改成功");
	}

	@Override
	public IMoocJSONResult updateface(Users users) {
		Users users1=new Users();
		users1=userService.selectByPhone(users.getPhone());
		users1.setFaceImageBig(users.getFaceImageBig());
		userMapper.updateByPrimaryKey(users1);
		return IMoocJSONResult.ok("修改成功");
	}

	@Override
	public IMoocJSONResult updatebackground(Users users) {
		Users users1=new Users();
		users1=userService.selectByPhone(users.getPhone());
		users1.setBackground(users.getBackground());
		userMapper.updateByPrimaryKey(users1);
		return IMoocJSONResult.ok("修改成功");
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public Integer preconditionSearchFriends(String myPhone, String friendPhone) {

		Users user = queryUserInfoByPhone(friendPhone);
		
		// 1. 搜索的用户如果不存在，返回[无此用户]
		if (user == null) {
			return SearchFriendsStatusEnum.USER_NOT_EXIST.status;
		}
		
		// 2. 搜索账号是你自己，返回[不能添加自己]
		if (user.getPhone().equals(myPhone)) {
			return SearchFriendsStatusEnum.NOT_YOURSELF.status;
		}
		
		// 3. 搜索的朋友已经是你的好友，返回[该用户已经是你的好友]
		Example mfe = new Example(MyFriends.class);
		Criteria mfc = mfe.createCriteria();
		mfc.andEqualTo("myPhone", myPhone);
		mfc.andEqualTo("myFriendPhone", user.getPhone());
		MyFriends myFriendsRel = myFriendsMapper.selectOneByExample(mfe);
		if (myFriendsRel != null) {
			return SearchFriendsStatusEnum.ALREADY_FRIENDS.status;
		}
		
		return SearchFriendsStatusEnum.SUCCESS.status;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public Users queryUserInfoByPhone(String phone) {
		Example ue = new Example(Users.class);
		Criteria uc = ue.createCriteria();
		uc.andEqualTo("phone", phone);
		return userMapper.selectOneByExample(ue);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void sendFriendRequest(String myPhone, String friendPhone) {
		
		// 根据用户名把朋友信息查询出来
		Users friend = queryUserInfoByPhone(friendPhone);
		
		// 1. 查询发送好友请求记录表
		Example fre = new Example(FriendsRequest.class);
		Criteria frc = fre.createCriteria();
		frc.andEqualTo("sendPhone", myPhone);
		frc.andEqualTo("acceptPhone", friend.getPhone());
		FriendsRequest friendRequest = friendsRequestMapper.selectOneByExample(fre);
		if (friendRequest == null) {
			// 2. 如果不是你的好友，并且好友记录没有添加，则新增好友请求记录
			String requestId = sid.nextShort();
			FriendsRequest request = new FriendsRequest();
			request.setId(requestId);
			request.setsendPhone(myPhone);
			request.setacceptPhone(friend.getPhone());
			request.setRequestDateTime(new Date());
			friendsRequestMapper.insert(request);
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public List<FriendRequestVO> queryFriendRequestList(String acceptPhone) {
		return usersMapperCustom.queryFriendRequestList(acceptPhone);
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public InformationVO selectInfromation(String phone) {
		return usersMapperCustom.selectInfromation(phone);
	}

	@Override
	public void saveInfo(Users users) {
		String phone=users.getPhone();
		Users users1=userService.selectByPhone(phone);
		users1.setAge(users.getAge());
		users1.setAddress(users.getAddress());
		users1.setProfession(users.getProfession());
		users1.setSignname(users.getSignname());
		users1.setBackground(users.getBackground());
		userMapper.updateByPrimaryKey(users1);

	}


	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void deleteFriendRequest(String sendPhone, String acceptPhone) {
		Example fre = new Example(FriendsRequest.class);
		Criteria frc = fre.createCriteria();
		frc.andEqualTo("sendPhone", sendPhone);
		frc.andEqualTo("acceptPhone", acceptPhone);
		friendsRequestMapper.deleteByExample(fre);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void passFriendRequest(String sendPhone, String acceptPhone) {
		saveFriends(sendPhone, acceptPhone);
		saveFriends(acceptPhone, sendPhone);
		deleteFriendRequest(sendPhone, acceptPhone);
		
		Channel sendChannel = UserChannelRel.get(sendPhone);
		if (sendChannel != null) {
			// 使用websocket主动推送消息到请求发起者，更新他的通讯录列表为最新
			DataContent dataContent = new DataContent();
			dataContent.setAction(MsgActionEnum.PULL_FRIEND.type);
			
			sendChannel.writeAndFlush(
					new TextWebSocketFrame(
							JsonUtils.objectToJson(dataContent)));
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveFriends(String sendPhone, String acceptPhone) {
		MyFriends myFriends = new MyFriends();
		String recordId = sid.nextShort();
		myFriends.setId(recordId);
		myFriends.setmyFriendPhone(acceptPhone);
		myFriends.setMyPhone(sendPhone);
		myFriendsMapper.insert(myFriends);
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public List<MyFriendsVO> queryMyFriends(String phone) {
		List<MyFriendsVO> myFirends = usersMapperCustom.queryMyFriends(phone);
		return myFirends;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public String saveMsg(ChatMsg chatMsg) {
		
		com.imooc.pojo.ChatMsg msgDB = new com.imooc.pojo.ChatMsg();
		String msgId = sid.nextShort();
		msgDB.setId(msgId);
		msgDB.setacceptPhone(chatMsg.getReceiverId());
		msgDB.setsendPhone(chatMsg.getSenderId());
		msgDB.setCreateTime(new Date());
		msgDB.setSignFlag(MsgSignFlagEnum.unsign.type);
		msgDB.setMsg(chatMsg.getMsg());
		
		chatMsgMapper.insert(msgDB);
		
		return msgId;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void updateMsgSigned(List<String> msgIdList) {
		usersMapperCustom.batchUpdateMsgSigned(msgIdList);
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public List<com.imooc.pojo.ChatMsg> getUnReadMsgList(String acceptPhone) {
		
		Example chatExample = new Example(com.imooc.pojo.ChatMsg.class);
		Criteria chatCriteria = chatExample.createCriteria();
		chatCriteria.andEqualTo("signFlag", 0);
		chatCriteria.andEqualTo("acceptPhone", acceptPhone);
		
		List<com.imooc.pojo.ChatMsg> result = chatMsgMapper.selectByExample(chatExample);
		
		return result;
	}


@Override
	public Users selectByPhone(String phone) {
		Users users=new Users();
		users.setPhone(phone);
		return userMapper.selectOne(users);
	}
}
