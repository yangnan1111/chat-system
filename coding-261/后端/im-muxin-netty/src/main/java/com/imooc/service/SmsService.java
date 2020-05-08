package com.imooc.service;

import com.imooc.pojo.SmsCode;
import com.imooc.pojo.Users;
import com.imooc.utils.IMoocJSONResult;


public interface SmsService {
 //   public void sendLoginSms(String phone) ;
    public void querySmsCode(String phone,String code);
    public IMoocJSONResult verificationCode(String phone, String code);
    public void register(String phone, String password,Users users,SmsCode smsCode);
    public String registerSend(String phone);
    public SmsCode select(String phone);
    public IMoocJSONResult passwordlogin(String phone,String password);
    public Users savephone(String phone);
    public Users updatephone(String phone);
    public void forgetrpass(String phone);
    public String Sendnew(String phone);

}
