package com.imooc.service.impl;

import com.aliyuncs.utils.StringUtils;
import com.imooc.mapper.SmsCodeMapper;
import com.imooc.mapper.UsersMapper;
import com.imooc.pojo.SmsCode;
import com.imooc.pojo.SmsUser;
import com.imooc.pojo.Users;
import com.imooc.service.SmsService;
import com.imooc.service.SmsUserService;
import com.imooc.utils.*;
import io.netty.util.internal.StringUtil;
import org.n3r.idworker.Sid;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

@Service
public class SmsServiceImpl implements SmsService {


    @Autowired
    private SmsService smsService;
    @Autowired
    private QRCodeUtils qrCodeUtils;
    @Autowired
    private FastDFSClient fastDFSClient;
    @Autowired
    private SmsCodeMapper smsCodeMapper;
    @Autowired
    private UsersMapper usersMapper;



    /**
     * 获取验证码
     *
     * @param phone
     */
//    @Override
//    public void sendLoginSms(String phone) {
////        首先查询该账号是否存在
//        String code = SmsServiceUtil.getMsgCode();
//
//            SmsServiceUtil.send(phone, code);
//            querySmsCode(phone,code);
//
//    }
    /**
     * @Description: 查询并更新数据库验证码信息
     * @Param: [phone, code]
     * @Author: yangn
     * @Date: 2019/12/4
     * @return: void
     */

    @Override
    public void querySmsCode(String phone, String code) {
        SmsCode smsCode=new SmsCode();
        smsCode.setPhone(phone);
        smsCode = smsCodeMapper.selectOne(smsCode);
        if (StringUtils.isEmpty(smsCode.getCode())){
            smsCode=new SmsCode();
            smsCode.setPhone(phone);
            smsCode.setCode(code);
            smsCode.setCreatetime(new Date());
            smsCode.setExpiredtime(SmsServiceUtil.getExpireTime());
            smsCodeMapper.insert(smsCode);
        }else {
            smsCode.setCode(code);
            smsCode.setExpiredtime(SmsServiceUtil.getExpireTime());
            smsCode.setCreatetime(new Date());
            //fixme
            smsCodeMapper.updateByPrimaryKey(smsCode);
        }
    }

    @Override
    public IMoocJSONResult verificationCode(String phone, String code) {
        SmsCode smsCode=new SmsCode();
        smsCode.setPhone(phone);
        smsCode = smsCodeMapper.selectOne(smsCode);
        Date expiredtime = smsCode.getExpiredtime();
        if (expiredtime.before(new Date())){
            if(code.equals(smsCode.getCode())){
                return IMoocJSONResult.ok("验证码正确");
            }
            else{
                return IMoocJSONResult.errorMsg("验证码错误");
            }
        }else{
            return IMoocJSONResult.errorMsg("验证码过期");
        }

    }

    @Override
    public void register(String phone,String password,Users users,SmsCode smsCode)  {

        String md5Str = null;
        try {
            md5Str = MD5Utils.getMD5Str(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
            smsCode.setPassword(md5Str);
            users.setPassword(md5Str);
            smsCodeMapper.updateByPrimaryKey(smsCode);
            usersMapper.updateByPrimaryKey(users);

    }


@Override
    public  String registerSend(String phone){
        String code = SmsServiceUtil.getMsgCode();
        SmsServiceUtil.send(phone, code);
        SmsCode smsCode=new SmsCode();
        smsCode.setPhone(phone);
        smsCode.setCode(code);
        smsCode.setCreatetime(new Date());
        smsCode.setExpiredtime(SmsServiceUtil.getExpireTime());
        smsCodeMapper.insert(smsCode);
        return code;
    }

    @Override
    public SmsCode select(String phone) {
        SmsCode smsCode=new SmsCode();
        smsCode.setPhone(phone);
        return smsCodeMapper.selectOne(smsCode);
    }

    @Override
    public IMoocJSONResult passwordlogin(String phone,String password) {
        SmsCode smsCode=new SmsCode();
        smsCode.setPhone(phone);
        smsCode=smsCodeMapper.selectOne(smsCode);
        String md5Str=null;
        try {
          md5Str = MD5Utils.getMD5Str(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(md5Str.equals(smsCode.getPassword())){
            System.out.println("登录成功");
            return IMoocJSONResult.ok();
        }
        else{
            return IMoocJSONResult.errorMsg("登录失败");
        }

    }


    @Override
    public Users savephone(String phone) {
        Users users=new Users();
        users.setPhone(phone);
        users.setNickname(phone);
        users.setFaceImageBig("https://c-ssl.duitang.com/uploads/item/201511/21/20151121171107_zMZcy.thumb.1000_0.jpeg");
        users.setAddress("北京市朝阳区");
        users.setAge("20");
        users.setProfession("工程师");
        users.setSignname("思前想后不如勇敢去做");
        users.setGender("male");
        users.setBackground("http://t9.baidu.com/it/u=3363001160,1163944807&fm=79&app=86&size=h300&n=0&g=4n&f=jpeg?sec=1587978923&t=5c02ff25f211dec7ae3bf7ef2443eba0");
        Sid sid=new Sid();
        String s = sid.nextShort();
        users.setId(s);
//        String path = System.getProperties().getProperty("user.home");
//        String erweiCodePath=path+s+"code.png";
//        qrCodeUtils.createQRCode(erweiCodePath, "muxin_qrcode:" + users.getPhone());
//        MultipartFile qrCodeFile = FileUtils.fileToMultipart(erweiCodePath);
//        String erweiCodeUrl="";
//        try {
//            erweiCodeUrl=fastDFSClient.uploadQRCode(qrCodeFile);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        users.setQrcode(erweiCodeUrl);
        usersMapper.insert(users);
        return users;
    }

    @Override
    public Users updatephone(String phone) {
        Users users2=new Users();
        Sid sid=new Sid();
        String s = sid.nextShort();
        users2.setPhone(phone);
        Users users1=usersMapper.selectOne(users2);
        users1.setId(s);
        users1.setNickname(phone);
        users1.setFaceImageBig("https://c-ssl.duitang.com/uploads/item/201511/21/20151121171107_zMZcy.thumb.1000_0.jpeg");
        users1.setAddress("北京市朝阳区");
        users1.setAge("20");
        users1.setGender("male");
        users1.setProfession("工程师");
        users1.setSignname("思前想后不如勇敢去做");
        users1.setBackground("http://t9.baidu.com/it/u=3363001160,1163944807&fm=79&app=86&size=h300&n=0&g=4n&f=jpeg?sec=1587978923&t=5c02ff25f211dec7ae3bf7ef2443eba0");
        usersMapper.updateByPrimaryKey(users1);
        return users1;

    }

    @Override
    public void forgetrpass(String phone) {
        String code = SmsServiceUtil.getMsgCode();
        SmsServiceUtil.send(phone, code);
        SmsCode smsCode=new SmsCode();
        smsCode.setPhone(phone);
        SmsCode smsCode1 = smsCodeMapper.selectOne(smsCode);
        smsCode1.setCode(code);
        smsCode1.setCreatetime(new Date());
        smsCode1.setExpiredtime(SmsServiceUtil.getExpireTime());
        smsCodeMapper.updateByPrimaryKey(smsCode1);
    }

    @Override
    public String Sendnew(String phone) {
        String code = SmsServiceUtil.getMsgCode();
        SmsServiceUtil.send(phone, code);
        SmsCode smsCode=new SmsCode();
        smsCode.setPhone(phone);
        SmsCode smsCode1 = smsCodeMapper.selectOne(smsCode);
        smsCode1.setCode(code);
        smsCode1.setCreatetime(new Date());
        smsCode1.setExpiredtime(SmsServiceUtil.getExpireTime());
        smsCodeMapper.updateByPrimaryKey(smsCode1);
        return code;
    }
}

