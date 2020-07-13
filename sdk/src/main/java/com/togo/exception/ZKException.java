package com.togo.exception;

/**
 * @Author taiyn
 * @Description zk exception
 * @Date 10:58 上午 2020/5/28
 **/
public class ZKException extends RuntimeException {
    public ZKException() {
        super();
    }

    public ZKException(String message) {
        super(message);
    }

    public ZKException(String message, Throwable cause) {
        super(message, cause);
    }

    public ZKException(Throwable cause) {
        super(cause);
    }
}
