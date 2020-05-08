package com.imooc.pojo;

import javax.persistence.Table;

@Table(name="user")
public class FreeswitchUser {
    private String id;
    private String password;
    private String user;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
