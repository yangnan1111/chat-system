package com.imooc.pojo;

import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "sms_code")
@Component
public class SmsCode {
    @Id
    private Long id;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 验证码
     */
    private String code;
    /**
     * 密码
     */
    private String password;
    /**
     * 过期时间
     */
    @Column(name = "expiredTime")
    private Date expiredtime;

    /**
     * 创建时间
     */
    @Column(name = "createTime")
    private Date createtime;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取手机号
     *
     * @return phone - 手机号
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置手机号
     *
     * @param phone 手机号
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取验证码
     *
     * @return code - 验证码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置验证码
     *
     * @param code 验证码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取过期时间
     *
     * @return expiredTime - 过期时间
     */
    public Date getExpiredtime() {
        return expiredtime;
    }

    /**
     * 设置过期时间
     *
     * @param expiredtime 过期时间
     */
    public void setExpiredtime(Date expiredtime) {
        this.expiredtime = expiredtime;
    }

    /**
     * 获取创建时间
     *
     * @return createTime - 创建时间
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * 设置创建时间
     *
     * @param createtime 创建时间
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}