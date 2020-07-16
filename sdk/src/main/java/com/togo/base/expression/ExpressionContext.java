package com.togo.base.expression;

import groovy.lang.Script;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author taiyn
 * @Description expression context, there are all expressions.
 * @Date 10:58 上午 2020/4/21
 **/
public enum ExpressionContext {

    INSTANCE;

    private Map<String, String> expressionMap = new ConcurrentHashMap<>();
    private Map<String, Script> scriptMap = new ConcurrentHashMap<>();

    public void add(String key, String expression) {

        expressionMap.put(key, expression);
        scriptMap.put(key, ExpressionShell.parse(expression));
    }

    public String getExpression(String key) {

        return expressionMap.get(key);
    }

    public Script getScript(String key) {

        return scriptMap.get(key);
    }
}
