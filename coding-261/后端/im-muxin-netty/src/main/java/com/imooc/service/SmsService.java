package com.imooc.service;

import com.imooc.pojo.SmsCode;
import com.imooc.pojo.Users;


public interface SmsService {
    public void sendLoginSms(String phone) ;
    public void querySmsCode(String phone,String code);
    public void verificationCode(String phone,String code);
    public Users register(String phone, String password, Users users);
    public SmsCode selectByPhone(String phone);
    public String registerSend(String phone);
    public void passwordlogin(String phone,String password);

}
