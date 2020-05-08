package com.imooc.pojo;

import javax.persistence.*;

@Table(name = "my_friends")
public class MyFriends {
    @Id
    private String id;

    /**
     * 用户id
     */
    @Column(name = "my_phone")
    private String myPhone;

    /**
     * 用户的好友id
     */
    @Column(name = "my_friend_phone")
    private String myFriendPhone;

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
     * 获取用户id
     *
     * @return my_user_id - 用户id
     */


    /**
     * 获取用户的好友id
     *
     * @return my_friend_user_id - 用户的好友id
     */
    public String getmyFriendPhone() {
        return myFriendPhone;
    }

    /**
     * 设置用户的好友id
     *
     * @param myFriendPhone 用户的好友id
     */
    public void setmyFriendPhone(String myFriendPhone) {
        this.myFriendPhone = myFriendPhone;
    }

    public String getMyPhone() {
        return myPhone;
    }

    public void setMyPhone(String myPhone) {
        this.myPhone = myPhone;
    }
}