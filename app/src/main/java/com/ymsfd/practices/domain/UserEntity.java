package com.ymsfd.practices.domain;

/**
 * Created by WoodenTea.
 * Date: 2016/6/30
 * Time: 15:16
 */
public class UserEntity {
    private final String firstName;
    private final String lastName;

    public UserEntity(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }
}
