package com.imooc.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "friends_request")
public class FriendsRequest {
    @Id
    private String id;

    @Column(name = "send_phone")
    private String sendPhone;

    @Column(name = "accept_phone")
    private String acceptPhone;

    /**
     * 发送请求的事件
     */
    @Column(name = "request_date_time")
    private Date requestDateTime;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return send_user_id
     */
    public String getsendPhone() {
        return sendPhone;
    }

    /**
     * @param sendPhone
     */
    public void setsendPhone(String sendPhone) {
        this.sendPhone = sendPhone;
    }

    /**
     * @return accept_user_id
     */
    public String getacceptPhone() {
        return acceptPhone;
    }

    /**
     * @param acceptPhone
     */
    public void setacceptPhone(String acceptPhone) {
        this.acceptPhone = acceptPhone;
    }

    /**
     * 获取发送请求的事件
     *
     * @return request_date_time - 发送请求的事件
     */
    public Date getRequestDateTime() {
        return requestDateTime;
    }

    /**
     * 设置发送请求的事件
     *
     * @param requestDateTime 发送请求的事件
     */
    public void setRequestDateTime(Date requestDateTime) {
        this.requestDateTime = requestDateTime;
    }
}