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
    @Override
    public void sendLoginSms(String phone) {
//        首先查询该账号是否存在
        String code = SmsServiceUtil.getMsgCode();

            SmsServiceUtil.send(phone, code);
            querySmsCode(phone,code);

    }
    /**
     * @Description: 查询并更新数据库验证码信息
     * @Param: [phone, code]
     * @Author: duolai
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
    public void verificationCode(String phone, String code) {
        SmsCode smsCode=new SmsCode();
        smsCode.setPhone(phone);
        smsCode = smsCodeMapper.selectOne(smsCode);
        Date expiredtime = smsCode.getExpiredtime();
        if (expiredtime.before(new Date())){

        }
        if(code.equals(smsCode.getCode())){
            System.out.println("success");
        }
        else{
            System.out.println("fail");
        }
    }

    @Override
    public Users register(String phone,String password,Users users)  {
        SmsCode smsCode=new SmsCode();
        String md5Str = null;
        try {
            md5Str = MD5Utils.getMD5Str(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        users.setPhone(phone);
        users.setPassword(md5Str);
        Sid sid=new Sid();
        String s = sid.nextShort();
        users.setId(s);
        smsCode.setPhone(phone);
        String erweiCodePath="D://"+s+"code.png";
        qrCodeUtils.createQRCode(erweiCodePath, "muxin_qrcode:" + users.getPhone());
        MultipartFile qrCodeFile = FileUtils.fileToMultipart(erweiCodePath);
        String erweiCodeUrl="";
        try {
            erweiCodeUrl=fastDFSClient.uploadQRCode(qrCodeFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        users.setQrcode(erweiCodeUrl);

        smsCode=smsCodeMapper.selectOne(smsCode);
            smsCode.setPassword(md5Str);
            smsCodeMapper.updateByPrimaryKey(smsCode);
            usersMapper.insert(users);
            return users;
    }
    @Override
    public SmsCode selectByPhone(String phone) {
        SmsCode smsCode=new SmsCode();
        smsCode.setPhone(phone);
        return smsCodeMapper.selectOne(smsCode);
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
    public void passwordlogin(String phone,String password) {
        SmsCode smsCode=new SmsCode();
        smsCode.setPhone(phone);
        smsCode=smsCodeMapper.selectOne(smsCode);
        if(password.equals(smsCode.getPassword())){
            System.out.println("登录成功");
        }
    }
}

