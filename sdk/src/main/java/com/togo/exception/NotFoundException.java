package com.togo.exception;

/**
 * @Author taiyn
 * @Description not found exception
 * @Date 8:06 下午 2020/5/25
 **/
public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }
}
