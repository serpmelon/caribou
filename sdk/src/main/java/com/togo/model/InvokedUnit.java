package com.togo.model;

import java.lang.reflect.Method;

/**
 * @Author taiyn
 * @Description the unit can be invoked
 * @Date 6:07 下午 2020/5/25
 **/
public class InvokedUnit {

    private String queryId;
    private Class<?> klass;
    private Method method;

    public InvokedUnit(String queryId, Class<?> klass, Method method) {
        this.queryId = queryId;
        this.klass = klass;
        this.method = method;
    }

    public String getQueryId() {
        return queryId;
    }

    public void setQueryId(String queryId) {
        this.queryId = queryId;
    }

    public Class<?> getKlass() {
        return klass;
    }

    public void setKlass(Class<?> klass) {
        this.klass = klass;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return "InvokedUnit{" +
                "queryId='" + queryId + '\'' +
                ", klass=" + klass +
                ", method=" + method +
                '}';
    }
}
