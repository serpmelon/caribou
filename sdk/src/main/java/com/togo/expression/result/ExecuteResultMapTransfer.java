package com.togo.expression.result;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Map;

/**
 * @Author taiyn
 * @Description map result
 * @Date 9:49 下午 2020/4/21
 **/
public class ExecuteResultMapTransfer extends ExecuteResultTransfer {

    @SuppressWarnings("unchecked")
    @Override
    public <T> T transfer(Object object, Class<T> klass) {

        Map<Object, Object> map = (Map<Object, Object>) object;
        try {
            T t = klass.getDeclaredConstructor().newInstance();
            Field[] fields = klass.getDeclaredFields();
            for (Field field : fields) {
                int mod = field.getModifiers();
                if(Modifier.isStatic(mod) || Modifier.isFinal(mod)){
                    continue;
                }

                field.setAccessible(true);
                field.set(t, map.get(field.getName()));
            }

            return t;
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }

        throw new RuntimeException();
    }
}
