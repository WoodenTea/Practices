package com.ymsfd.practices.domain;

import org.greenrobot.greendao.annotation.Generated;

@org.greenrobot.greendao.annotation.Entity
public class User {
    private String username;
    private int gender;

    @Generated(hash = 1155248703)
    public User(String username, int gender) {
        this.username = username;
        this.gender = gender;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getGender() {
        return this.gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }
}
