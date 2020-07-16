package com.togo.data.local;

import com.togo.data.model.InvokedUnit;
import org.apache.ibatis.session.SqlSession;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @Author taiyn
 * @Description default SQL runner
 * @Date 8:01 下午 2020/5/25
 **/
public class DefaultSQLRunner implements SQLRunner {

    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> run(InvokedUnit invokedUnit, SqlSession sqlSession, Map<String, Object> parameter) {

        Class<?> klass = invokedUnit.getKlass();
        Method method = invokedUnit.getMethod();

        Object testMapper = sqlSession.getMapper(klass);
        try {
            Object object = method.invoke(testMapper, parameter);
            if (object != null)
                return (List<Map<String, Object>>) object;
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }
}
