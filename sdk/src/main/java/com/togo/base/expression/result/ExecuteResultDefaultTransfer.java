package com.togo.base.expression.result;


/**
 * @Author taiyn
 * @Description single result
 * @Date 10:31 下午 2020/4/21
 **/
public class ExecuteResultDefaultTransfer extends ExecuteResultTransfer {

    @SuppressWarnings("unchecked")
    @Override
    public <T> T transfer(Object object, Class<T> klass) {

        // 字符串特殊处理
        if (klass.equals(String.class)) {
            return (T) String.valueOf(object);
        }

        // 其他直接强转
        return (T) object;
    }
}
