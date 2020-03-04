package com.imooc.pojo;

import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "sms_user")
@Component
public class SmsUser implements Serializable {
    /**
     * 主键
     */
    @Id
    private Long id;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 最近一次登录时间
     */
    @Column(name = "loginTime")
    private Date logintime;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取手机号码
     *
     * @return phone - 手机号码
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置手机号码
     *
     * @param phone 手机号码
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取最近一次登录时间
     *
     * @return loginTime - 最近一次登录时间
     */
    public Date getLogintime() {
        return logintime;
    }

    /**
     * 设置最近一次登录时间
     *
     * @param logintime 最近一次登录时间
     */
    public void setLogintime(Date logintime) {
        this.logintime = logintime;
    }
}