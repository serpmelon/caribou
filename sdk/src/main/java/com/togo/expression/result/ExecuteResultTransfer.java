package com.togo.expression.result;

/**
 * @Author taiyn
 * @Description transfer result to underline object
 * @Date 9:46 下午 2020/4/21
 **/
public abstract class ExecuteResultTransfer {

    public abstract <T> T transfer(Object object, Class<T> klass);
}
