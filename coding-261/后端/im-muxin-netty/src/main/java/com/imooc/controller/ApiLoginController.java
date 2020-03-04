package com.imooc.controller;


import com.aliyuncs.utils.StringUtils;
import com.imooc.mapper.SmsCodeMapper;
import com.imooc.pojo.SmsCode;
import com.imooc.pojo.SmsUser;
import com.imooc.pojo.Users;
import com.imooc.service.SmsService;
import com.imooc.service.SmsUserService;
import com.imooc.service.impl.SmsServiceImpl;
import com.imooc.utils.IMoocJSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("login")
public class ApiLoginController {
    @Autowired
    private SmsService smsService;

    @Autowired
    private SmsCodeMapper smsCodeMapper;

    /**
     * 登录时填写手机号获取验证码
     */
    @PostMapping("/sendLoginSms")
    public IMoocJSONResult sendLoginSms(@RequestParam String phone) {
        SmsCode smsCode = smsService.selectByPhone(phone);
        if (StringUtils.isEmpty(smsCode.getPhone())) {
            throw new RuntimeException("手机号码不存在");
        } else {
            smsService.sendLoginSms(phone);
            return IMoocJSONResult.ok("获取验证码成功");
        }
    }

    /**
     * 注册时获取验证码
     * @param phone
     * @return
     */
    @PostMapping("/registerSend")
    public IMoocJSONResult registerSend(@RequestParam String phone){
        SmsCode smsCode=smsService.selectByPhone(phone);
        if(smsCode==null){
            smsService.registerSend(phone);
            return IMoocJSONResult.ok("获取验证码成功");
        }else{
            return IMoocJSONResult.ok("用户名存在");
        }
}

    /**
     * 注册
     * @param phone
     * @param code
     * @param password
     * @return
     */
    @PostMapping("/register")
    public IMoocJSONResult register(@RequestParam String phone, @RequestParam String code, @RequestParam String password, @RequestBody(required = false) Users users){

        try {
            users.setNickname(phone);
            users.setFaceImage("");
            users.setFaceImageBig("");
            smsService.register(phone,password,users);
        } catch (Exception e) {
            e.printStackTrace();
        }
        smsService.verificationCode(phone, code);
            return IMoocJSONResult.ok("注册成功");

    }

    /**
     * 验证码登录
     * @param phone
     * @param code
     * @return
     */
    @PostMapping("/verylogin")
    public IMoocJSONResult login(@RequestParam String phone,@RequestParam String code) {
        SmsCode smsCode = new SmsCode();
        smsCode.setPhone(phone);
        smsCode = smsCodeMapper.selectOne(smsCode);
        Date expiredtime = smsCode.getExpiredtime();
        if (expiredtime.before(new Date())) {
            return IMoocJSONResult.errorMsg("验证码过期");
        } else {
            if (code.equals(smsCode.getCode())) {
                return IMoocJSONResult.ok("登录成功");
            } else {
                return IMoocJSONResult.errorMsg("登录失敗");
            }
        }
    }

    /**
     * 密码登录
     * @param phone
     * @param password
     * @return
     */
    @PostMapping("/passwordlogin")
    public IMoocJSONResult passwordlogin(@RequestParam String phone,@RequestParam String password){
        smsService.passwordlogin(phone,password);
        return  IMoocJSONResult.ok("登录成功");
    }

}
