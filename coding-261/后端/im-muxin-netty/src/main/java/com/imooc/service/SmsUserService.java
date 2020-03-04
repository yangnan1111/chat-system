package com.imooc.service;

import com.imooc.pojo.SmsUser;

public interface SmsUserService {
    public SmsUser selectByPhone(String phone);
}
