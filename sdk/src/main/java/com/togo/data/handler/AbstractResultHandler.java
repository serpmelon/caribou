package com.togo.data.handler;

/**
 * @Author taiyn
 * @Description TODO
 * @Date 9:20 上午 2020/6/11
 **/
public abstract class AbstractResultHandler implements ResultHandler {

    protected String expression;
    protected AbstractResultHandler(String expression) {
        this.expression = expression;
    }

}
