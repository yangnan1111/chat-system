package com.imooc.mapper;

import java.util.List;

import com.imooc.pojo.Users;
import com.imooc.pojo.vo.FriendRequestVO;
import com.imooc.pojo.vo.InformationVO;
import com.imooc.pojo.vo.MyFriendsVO;
import com.imooc.utils.MyMapper;

public interface UsersMapperCustom extends MyMapper<Users> {
	
	public List<FriendRequestVO> queryFriendRequestList(String acceptPhone);
	
	public List<MyFriendsVO> queryMyFriends(String phone);
	
	public void batchUpdateMsgSigned(List<String> msgIdList);

	public InformationVO selectInfromation(String phone);
	
}