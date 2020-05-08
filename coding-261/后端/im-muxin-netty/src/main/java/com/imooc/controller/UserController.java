package com.imooc.controller;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.hutool.core.io.resource.ClassPathResource;
import com.imooc.pojo.Record;
import com.imooc.pojo.SmsCode;
import com.imooc.pojo.bo.*;
import com.imooc.pojo.vo.InformationVO;
import com.imooc.service.RecordService;
import com.imooc.service.SmsService;
import com.imooc.service.SmsUserService;
import com.imooc.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.imooc.enums.OperatorFriendRequestTypeEnum;
import com.imooc.enums.SearchFriendsStatusEnum;
import com.imooc.pojo.Users;
import com.imooc.pojo.vo.MyFriendsVO;
import com.imooc.pojo.vo.UsersVO;
import com.imooc.service.UserService;
import sun.net.www.content.audio.wav;

@RestController
@RequestMapping("u")
public class UserController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private SmsService smsservice;
	@Autowired
	private RecordService recordService;


	@Autowired
	private FastDFSClient fastDFSClient;

	/**
	 * 上传录音文件返回url
	 */
	@PostMapping("/WavUrl")
	public IMoocJSONResult WavUrl(Wav wav) throws Exception {
//		System.out.println("-------------------------------->"+wav.getBasewav().getContentType()+","+wav.getBasewav().getOriginalFilename()+","+wav.getBasewav().getSize());
		String url = null;
		Sid sid = new Sid();
		String s = sid.nextShort();
		String path = "/usr/data/";
		System.out.println(path);
		String userFacePath = path + s + "wavface64.wav";
		//String userFacePath = "D:\\" + s + "wavface64.wav";
		File file1 = new File(userFacePath);
		wav.getBasewav().transferTo(file1);
		MultipartFile faceFile = FileUtils.fileToMultipart(userFacePath);
		String url1 = fastDFSClient.uploadFile(faceFile);
		url="http://123.56.146.238:88/yang/"+url1;
		Record record=new Record();
		Sid sid1=new Sid();
		String m=sid1.nextShort();
		record.setId(m);
		record.setPhone(wav.getPhone());
		record.setBasewav(url);
		record.setRecordtime(wav.getRecordtime());
		record.setTime(new Date());
		recordService.wav(record);
		return IMoocJSONResult.ok(m);

	}
	/**
	 * 上传图片文件返回url
	 */
	@PostMapping("/ImageUrl")
	public IMoocJSONResult ImageUrl(@RequestParam MultipartFile file) throws Exception {
		System.out.println("-------------------------------->"+file.getContentType()+","+file.getOriginalFilename()+","+file.getSize());

		String originalFilename = file.getOriginalFilename();
		String path = "/usr/data/";
		Sid sid=new Sid();
		String s=sid.nextShort();
		String url=null;
		String userFacePath = path + s + "userface64"+"."+originalFilename.substring(originalFilename.lastIndexOf(".")+1);
		//String userFacePath = "D:\\" + s + "wavface64.png";
		File newFile=new File(userFacePath);
//		FileUtils.base64ToFile(userFacePath, base64Data);
		file.transferTo(newFile);
		// 上传文件到fastdfs
		MultipartFile faceFile = FileUtils.fileToMultipart(userFacePath);
		String url1 = fastDFSClient.uploadBase64(faceFile);
		url="http://123.56.146.238:88/yang/"+url1;
		return IMoocJSONResult.ok(url);
	}

	/**
	 * @Description: 上传用户头像
	 */
	@PostMapping("/uploadFaceBase64")
	public IMoocJSONResult uploadFaceBase64(@RequestParam String url,@RequestParam String phone) throws Exception {
		
//		// 获取前端传过来的base64字符串, 然后转换为文件对象再上传
////		String base64Data = userBO.getFaceData();
////		MultipartFile faceData = userBO.getFaceData();
//		String originalFilename = file.getOriginalFilename();
//		String path = System.getProperties().getProperty("user.home");
//
//		String userFacePath = path + phone + "userface64"+"."+originalFilename.substring(originalFilename.lastIndexOf(".")+1);
//		File newFile=new File(userFacePath);
////		FileUtils.base64ToFile(userFacePath, base64Data);
//		file.transferTo(newFile);
//		// 上传文件到fastdfs
//		MultipartFile faceFile = FileUtils.fileToMultipart(userFacePath);
//		String url = fastDFSClient.uploadBase64(faceFile);
//		System.out.println(url);

		Users user = new Users();
//		user.setFaceImage(thumpImgUrl);
		user.setPhone(phone);
		user.setFaceImageBig(url);
		userService.updateface(user);


		
		return IMoocJSONResult.ok(url);
	}

	/**
	 * 更换背景图片
	 */
	@PostMapping("/setBackground")
	public IMoocJSONResult setBackground(@RequestParam String  url,@RequestParam String phone) throws Exception {

		// 获取前端传过来的base64字符串, 然后转换为文件对象再上传
//		String base64Data = userBO.getFaceData();
//		MultipartFile faceData = userBO.getFaceData();
//		String originalFilename = file.getOriginalFilename();
//		String path = System.getProperties().getProperty("user.home");
//
//		String userFacePath = path + phone + "background"+"."+originalFilename.substring(originalFilename.lastIndexOf(".")+1);
//		File newFile=new File(userFacePath);
////		FileUtils.base64ToFile(userFacePath, base64Data);
//		file.transferTo(newFile);
//		// 上传文件到fastdfs
//		MultipartFile faceFile = FileUtils.fileToMultipart(userFacePath);
//		String url = fastDFSClient.uploadBase64(faceFile);
//		System.out.println(url);
//		"dhawuidhwaiuh3u89u98432.png"
//		"dhawuidhwaiuh3u89u98432_80x80.png"
		// 获取缩略图的url
//		String thump = "_80x80.";
//		String arr[] = url.split("\\.");
//		String thumpImgUrl = arr[0] + thump + arr[1];

		Users user = new Users();
//		user.setFaceImage(thumpImgUrl);
		user.setPhone(phone);
		user.setBackground(url);
		userService.updatebackground(user);




		return IMoocJSONResult.ok(url);
	}
	/**
	 * @Description: 设置用户昵称
	 */
	@PostMapping("/setNickname")
	public IMoocJSONResult setNickname( UsersBO userBO) throws Exception {
		
		Users user = new Users();
		user.setPhone(userBO.getPhone());
		user.setNickname(userBO.getNickname());
        userService.updateUserInfo(user);
		return IMoocJSONResult.ok("更改成功");
	}

	/**
	 * 修改年龄
	 * @param ageBo
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/setAge")
	public IMoocJSONResult setAge(AgeBo ageBo) throws Exception{
		Users user = new Users();
		user.setPhone(ageBo.getPhone());
		user.setAge(ageBo.getAge());
		userService.updateAge(user);
		return IMoocJSONResult.ok("更改成功");
	}

	/**
	 * 修改性別
	 * @param genderBo
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/setGender")
	public IMoocJSONResult setGender(GenderBo genderBo) throws Exception{
		Users user = new Users();
		user.setPhone(genderBo.getPhone());
		user.setGender(genderBo.getGender());
		userService.updateGender(user);
		return IMoocJSONResult.ok("更改成功");
	}
	/**
	 * 修改地址
	 * @param addressBo
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/setAddress")
	public IMoocJSONResult setAddress(AddressBo addressBo) throws Exception{
		Users user = new Users();
		user.setPhone(addressBo.getPhone());
		user.setAddress(addressBo.getAddress());
		userService.updateAddress(user);
		return IMoocJSONResult.ok("更改成功");
	}
	/**
	 * 修改职业
	 */
	@PostMapping("/setProfession")
	public IMoocJSONResult setProfession(ProfessionBo professionBo)throws Exception{
		Users user = new Users();
		user.setPhone(professionBo.getPhone());
		user.setProfession(professionBo.getProfession());
		userService.updateProfession(user);
		return IMoocJSONResult.ok("更改成功");
	}
	/**
	 * 修改个性签名
	 */
	@PostMapping("/setSignname")
	public IMoocJSONResult setsignname(SignnameBo signnameBo)throws Exception{
		Users user = new Users();
		user.setPhone(signnameBo.getPhone());
		user.setSignname(signnameBo.getSignname());
		userService.updateSignname(user);
		return IMoocJSONResult.ok("更改成功");
	}
	/**
	 * @Description: 搜索好友接口, 根据账号做匹配查询而不是模糊查询
	 */
	@PostMapping("/search")
	public IMoocJSONResult searchUser(String myPhone, String friendPhone)
			throws Exception {
		
		// 0. 判断 myPhone friendPhone 不能为空
		if (StringUtils.isBlank(myPhone) 
				|| StringUtils.isBlank(friendPhone)) {
			return IMoocJSONResult.errorMsg("");
		}
		
		// 前置条件 - 1. 搜索的用户如果不存在，返回[无此用户]phone
		// 前置条件 - 2. 搜索账号是你自己，返回[不能添加自己]
		// 前置条件 - 3. 搜索的朋友已经是你的好友，返回[该用户已经是你的好友]
		Integer status = userService.preconditionSearchFriends(myPhone, friendPhone);
		if (status.equals( SearchFriendsStatusEnum.SUCCESS.status)) {
			Users user = userService.queryUserInfoByPhone(friendPhone);
			UsersVO userVO = new UsersVO();
			userVO.setNickname(user.getNickname());
			userVO.setAddress(user.getAddress());
			userVO.setGender(user.getGender());
			userVO.setFaceImagebig(user.getFaceImageBig());
			userVO.setId(user.getId());

			return IMoocJSONResult.ok(userVO);
		} else {
			String errorMsg = SearchFriendsStatusEnum.getMsgByKey(status);
			return IMoocJSONResult.errorMsg(errorMsg);
		}
	}
	
	
	/**
	 * @Description: 发送添加好友的请求
	 */
	@PostMapping("/addFriendRequest")
	public IMoocJSONResult addFriendRequest(String myPhone, String friendPhone)
			throws Exception {
		
		// 0. 判断 myPhone friendPhone 不能为空
		if (StringUtils.isBlank(myPhone) 
				|| StringUtils.isBlank(friendPhone)) {
			return IMoocJSONResult.errorMsg("");
		}
		
		// 前置条件 - 1. 搜索的用户如果不存在，返回[无此用户]
		// 前置条件 - 2. 搜索账号是你自己，返回[不能添加自己]
		// 前置条件 - 3. 搜索的朋友已经是你的好友，返回[该用户已经是你的好友]
		Integer status = userService.preconditionSearchFriends(myPhone, friendPhone);
		if (status.equals(SearchFriendsStatusEnum.SUCCESS.status)){
			userService.sendFriendRequest(myPhone, friendPhone);
		} else {
			String errorMsg = SearchFriendsStatusEnum.getMsgByKey(status);
			return IMoocJSONResult.errorMsg(errorMsg);
		}
		
		return IMoocJSONResult.ok();
	}
	
	/**
	 * @Description: 查看添加好友的请求
	 */
	@PostMapping("/queryFriendRequests")
	public IMoocJSONResult queryFriendRequests(String phone) {
		
		// 0. 判断不能为空
		if (StringUtils.isBlank(phone)) {
			return IMoocJSONResult.errorMsg("");
		}
		
		// 1. 查询用户接受到的朋友申请
		return IMoocJSONResult.ok(userService.queryFriendRequestList(phone));
	}
	
	
	/**
	 * @Description: 接受方通过或者忽略朋友请求
	 */
	@PostMapping("/operFriendRequest")
	public IMoocJSONResult operFriendRequest(String acceptPhone, String sendPhone,
												Integer operType) {
		
		// 0. acceptPhone sendPhone operType 判断不能为空
		if (StringUtils.isBlank(acceptPhone)
				|| StringUtils.isBlank(sendPhone)
				|| operType == null) {
			return IMoocJSONResult.errorMsg("");
		}
		
		// 1. 如果operType 没有对应的枚举值，则直接抛出空错误信息
		if (StringUtils.isBlank(OperatorFriendRequestTypeEnum.getMsgByType(operType))) {
			return IMoocJSONResult.errorMsg("");
		}
		
		if (operType.equals(OperatorFriendRequestTypeEnum.IGNORE.type)) {
			// 2. 判断如果忽略好友请求，则直接删除好友请求的数据库表记录
			userService.deleteFriendRequest(sendPhone, acceptPhone);
		} else if (operType.equals(OperatorFriendRequestTypeEnum.PASS.type)) {
			// 3. 判断如果是通过好友请求，则互相增加好友记录到数据库对应的表
			//	   然后删除好友请求的数据库表记录
			userService.passFriendRequest(sendPhone, acceptPhone);
		}
		
		// 4. 数据库查询好友列表
		List<MyFriendsVO> myFirends = userService.queryMyFriends(acceptPhone);
		
		return IMoocJSONResult.ok(myFirends);
	}
	
	/**
	 * @Description: 查询我的好友列表
	 */
	@PostMapping("/myFriends")
	public IMoocJSONResult myFriends(String phone) {
		// 0. phone 判断不能为空
		if (StringUtils.isBlank(phone)) {
			return IMoocJSONResult.errorMsg("");
		}
		
		// 1. 数据库查询好友列表
		List<MyFriendsVO> myFirends = userService.queryMyFriends(phone);
		
		return IMoocJSONResult.ok(myFirends);
	}
	
	/**
	 * 
	 * @Description: 用户手机端获取未签收的消息列表
	 */
	@PostMapping("/getUnReadMsgList")
	public IMoocJSONResult getUnReadMsgList(String acceptPhone) {
		// 0. phone 判断不能为空
		if (StringUtils.isBlank(acceptPhone)) {
			return IMoocJSONResult.errorMsg("");
		}
		
		// 查询列表
		List<com.imooc.pojo.ChatMsg> unreadMsgList = userService.getUnReadMsgList(acceptPhone);
		
		return IMoocJSONResult.ok(unreadMsgList);
	}

	/**
	 * 我的
	 */
	@PostMapping("/mine")
	public IMoocJSONResult mine(String phone) throws IOException {
		InformationVO in=userService.selectInfromation(phone);
//		UsersVO usersVO=new UsersVO();
//		usersVO.setAge(in.getAge());
//		usersVO.setAddress(in.getAddress());
//        usersVO.setNickname(in.getNickname());
//        usersVO.setProfession(in.getProfession());
//        usersVO.setSignname(in.getSignname());
//		ClassPathResource classPathResource=new ClassPathResource(in.getBackground());
//		File file = classPathResource.getFile();
//		ClassPathResource classPathResource1=new ClassPathResource(in.getFace_image_big());
//		File file1 = classPathResource1.getFile();
//
//		FileInputStream inputStream = new FileInputStream(file);
//		MultipartFile multipartFile = new MockMultipartFile("copy"+file.getName(),file.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), inputStream);
//		FileInputStream fileInputStream = new FileInputStream(file1);
//		MockMultipartFile mockMultipartFile = new MockMultipartFile("copy"+file.getName(),file1.getName(),  ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
//		usersVO.setBackground(multipartFile);
//		usersVO.setFaceImagebig(mockMultipartFile);
		return IMoocJSONResult.ok(in);
	}

//	@PostMapping("/saveinfo")
//	public IMoocJSONResult saveinfo(InformationBO informationBO){
//		Users users=new Users();
//		MultipartFile file=informationBO.getBackground();
//		String originalFilename = file.getOriginalFilename();
//		String path = System.getProperties().getProperty("user.home");
//		String userFacePath = path + informationBO.getPhone() + "background"+"."+originalFilename.substring(originalFilename.lastIndexOf(".")+1);
//		File newFile=new File(userFacePath);
//		try {
//			file.transferTo(newFile);
//			MultipartFile faceFile = FileUtils.fileToMultipart(userFacePath);
//			String url = fastDFSClient.uploadBase64(faceFile);
//			users.setSignname(informationBO.getSignname());
//			users.setProfession(informationBO.getProfession());
//			users.setAge(informationBO.getAge());
//			users.setAddress(informationBO.getAddress());
//			users.setPhone(informationBO.getPhone());
//			users.setBackground(url);
//			userService.saveInfo(users);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		List<InformationVO> in=new ArrayList<>();
//		in=userService.selectInfromation(informationBO.getPhone());
//		if(!(in.get(0).getAge().equals(""))& !(in.get(0).getAddress().equals(""))& !(in.get(0).getProfession().equals(""))
//		& !(in.get(0).getSignname().equals(""))& !(in.get(0).getBackground().equals(""))){
//			return IMoocJSONResult.ok("保存成功");
//		}
//		else{
//			return IMoocJSONResult.errorMsg("保存失败");
//		}
//
//	}
}
