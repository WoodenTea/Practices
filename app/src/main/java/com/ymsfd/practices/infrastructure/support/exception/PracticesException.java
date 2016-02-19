package com.ymsfd.practices.infrastructure.support.exception;

/**
 * Created by ymsfdDev.
 * User: ymsfd
 * Date: 5/9/15
 * Time: 00:40
 */
public class PracticesException extends Exception {
    private int statusCode = -1;

    public PracticesException(String msg) {
        super(msg);
    }

    public PracticesException(Exception cause) {
        super(cause);
    }

    public PracticesException(String msg, int statusCode) {
        super(msg);
        this.statusCode = statusCode;
    }

    public PracticesException(String msg, Exception cause) {
        super(msg, cause);
    }

    public PracticesException(String msg, Exception cause, int statusCode) {
        super(msg, cause);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return this.statusCode;
    }
}
