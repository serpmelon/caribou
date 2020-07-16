package com.togo.base.expression.parameter;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author taiyn
 * @Description
 * @Date 6:33 下午 2020/4/22
 **/
public abstract class AbstractParameterMap implements ParameterMap {

    private Map<String, Object> map = new HashMap<>();

    @Override
    public Map<String, Object> parameter() {

        if (this.getClass().getDeclaredFields().length == 0)
            return Collections.emptyMap();

        Arrays.stream(this.getClass().getDeclaredFields()).forEach(item -> {
            try {
                item.setAccessible(true);
                map.put(item.getName(), item.get(this));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        return map;
    }
}
