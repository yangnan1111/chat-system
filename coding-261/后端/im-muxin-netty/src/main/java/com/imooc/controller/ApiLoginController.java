package com.imooc.controller;


import com.aliyuncs.utils.StringUtils;
import com.imooc.mapper.SmsCodeMapper;
import com.imooc.mapper.UsersMapper;
import com.imooc.mapper1.UserMapper;
import com.imooc.pojo.FreeswitchUser;
import com.imooc.pojo.SmsCode;
import com.imooc.pojo.SmsUser;
import com.imooc.pojo.Users;
import com.imooc.service.SmsService;
import com.imooc.service.SmsUserService;
import com.imooc.service.UserService;
import com.imooc.service.impl.SmsServiceImpl;
import com.imooc.utils.IMoocJSONResult;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("login")
public class ApiLoginController {
    @Autowired
    private SmsService smsService;
    @Autowired
    private UserService userService;

    @Autowired
    private SmsCodeMapper smsCodeMapper;

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 注册1
     *
     * @param phone
     * @return
     */
    @PostMapping("/registerSend")
    public IMoocJSONResult registerSend(@RequestParam String phone) {
        Users users = userService.selectByPhone(phone);
        SmsCode smsCode = smsService.select(phone);
        if (users == null) {
            if (smsCode == null) {
                smsService.registerSend(phone);
                return IMoocJSONResult.ok("获取验证码成功");
            } else {
                smsService.Sendnew(phone);
                return IMoocJSONResult.ok("获取验证码成功");
            }
        } else {
            smsService.forgetrpass(phone);
            return IMoocJSONResult.ok("忘记密码");
        }
    }

    /**
     * 忘记密码的next
     */
    @PostMapping("/nextnext")
    public IMoocJSONResult nextnext(@RequestParam String phone, @RequestParam String code) {
        IMoocJSONResult iMoocJSONResult = smsService.verificationCode(phone, code);
        if (iMoocJSONResult.isOK()) {
            return IMoocJSONResult.ok("验证成功");
        } else {
            return IMoocJSONResult.errorMsg("验证失败");
        }
    }

    /**
     * 下一步
     */
    @PostMapping("/next")
    public IMoocJSONResult nextStep(@RequestParam String phone, @RequestParam String code) {
        IMoocJSONResult iMoocJSONResult = smsService.verificationCode(phone, code);
        if (iMoocJSONResult.isOK()) {
            Users users = userService.selectByPhone(phone);
            if (users == null) {
                smsService.savephone(phone);
                return IMoocJSONResult.ok("验证成功");
            } else {
                smsService.updatephone(phone);

                return IMoocJSONResult.ok("验证成功");
            }
        }
         else {
            return IMoocJSONResult.errorMsg("验证失败");
        }

    }

    /**
     * 注册
     *
     * @param password
     * @return
     */
    @PostMapping("/register")
    public IMoocJSONResult register(@RequestParam String phone, @RequestParam String password) {
        FreeswitchUser freeswitchUser = new FreeswitchUser();
        Users users = new Users();
        SmsCode smsCode = new SmsCode();
        smsCode.setPhone(phone);
        users.setPhone(phone);
        Sid sid = new Sid();
        freeswitchUser.setId(sid.nextShort());
        freeswitchUser.setPassword(password);
        freeswitchUser.setUser(phone);
        userMapper.insert(freeswitchUser);
        Users users1 = usersMapper.selectOne(users);
        SmsCode smsCode1 = smsCodeMapper.selectOne(smsCode);
        smsService.register(phone, password, users1, smsCode1);
        return IMoocJSONResult.ok("成功");

    }


    /**
     * 密码登录
     *
     * @param phone
     * @param password
     * @return
     */
    @PostMapping("/passwordlogin")
    public IMoocJSONResult passwordlogin(@RequestParam String phone, @RequestParam String password) {
        Users users=new Users();
        users.setPhone(phone);
        users=userService.selectByPhone(phone);
        String id=users.getId();
        if (users == null) {
            return IMoocJSONResult.errorMsg("请注册");
        } else {
            IMoocJSONResult iMoocJSONResult = smsService.passwordlogin(phone, password);
            if (iMoocJSONResult.isOK()) {
                return IMoocJSONResult.ok(id);
            } else {
                return IMoocJSONResult.errorMsg("登陆失败");
            }
        }
    }


}
