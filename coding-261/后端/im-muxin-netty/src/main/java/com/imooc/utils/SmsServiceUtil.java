package com.imooc.utils;
import cn.hutool.core.date.DateUtil;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Random;
@Component
public class SmsServiceUtil {

    public static void send(String phone,String code) {
//第二个参数为自己独有的accessKeyid，第三个参数为自己独有的accessKeySecret
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou",
                "LTAI4FtVFk4VU7HCGHLApWws", "bYSHVdnGDUbHy9dZW5TdS7Gx4HynQQ");
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();//组装请求对象
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);//设置post提交
        request.setDomain("dysmsapi.aliyuncs.com");//短信API产品域名（接口地址固定，无需修改）
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "聊天室系统");//短信签名
        request.putQueryParameter("TemplateCode", "SMS_182676867");
        request.putQueryParameter("TemplateParam", "{code:" + code+ "}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    public  static String getMsgCode() {
        int n = 7;
        StringBuilder code = new StringBuilder();
        Random ran = new Random();
        for (int i = 1; i < n; i++) {
            code.append(Integer.valueOf(ran.nextInt(10)).toString());
        }
        return code.toString();
    }

    public static Date getExpireTime() {
        return DateUtil.offsetSecond(new Date(), 30);
    }

}