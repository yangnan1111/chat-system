//package com.imooc.service.impl;
//
//import com.imooc.mapper.SmsUserMapper;
//import com.imooc.pojo.SmsUser;
//import com.imooc.service.SmsUserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class SmsUserServiceImpl implements SmsUserService {
//
//    @Autowired
//    private SmsUserMapper smsUserMapper;
//    /**
//     * 查询账号是否存在
//     *
//     * @param phone
//     * @return
//     */
//    @Override
//    public SmsUser selectByPhone(String phone) {
//        SmsUser smsUser=new SmsUser();
//        smsUser.setPhone(phone);
//        return smsUserMapper.selectOne(smsUser);
//    }
//
//}
