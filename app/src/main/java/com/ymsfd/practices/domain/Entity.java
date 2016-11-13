package com.ymsfd.practices.domain;

public interface Entity<T> {
    boolean sameIdentityAs(T other);
}
