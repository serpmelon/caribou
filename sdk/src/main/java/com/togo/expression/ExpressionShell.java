package com.togo.expression;

import com.togo.expression.parameter.ParameterMap;
import com.togo.expression.result.ExecuteResult;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import org.codehaus.groovy.control.CompilerConfiguration;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;

/**
 * @Author taiyn
 * @Description expression shell util which used to execute custom expression
 * @Date 5:04 下午 2020/4/15
 **/
public class ExpressionShell {

    private static final GroovyShell shell;

    static {

        CompilerConfiguration cfg = new CompilerConfiguration();
        cfg.setScriptBaseClass(BaseScript.class.getName());

        shell = new GroovyShell(cfg);
    }

    public static Object execute(Script script, Object map) {

        Binding binding = new Binding();
        binding.setVariable("parameter", map);
        script.setBinding(binding);
        return script.run();
    }

    public static <T> ExecuteResult<T> execute(String expression, ParameterMap parameterMap, Class<T> klass) {

        Script script = shell.parse(expression);
        return execute(script, parameterMap, klass);
    }

    public static <T> ExecuteResult<T> execute(Script script, ParameterMap parameterMap, Class<T> klass) {

        return execute(script, parameterMap.parameter(), klass);
    }

    public static <T> ExecuteResult<T> execute(Script script, Map<String, Object> map, Class<T> klass) {

        Binding binding = new Binding(map);
        script.setBinding(binding);
        return ExecuteResult.build(script.run(), klass);
    }

    public static Script parse(String expression) {
        return shell.parse(expression);
    }

    /**
     * @Author taiyn
     * @Description base script that {@link ExpressionShell} will use.
     * You can insert custom method like {@code nvl}, and use within the expression, which contains the method.
     * @Date 5:30 下午 2020/4/15
     * @Param
     * @return
     **/
    public static class BaseScript extends Script {

        @Override
        public Object run() {
            //show usage
            Method[] methods = BaseScript.class.getDeclaredMethods();
            StringBuilder sb = new StringBuilder();
            for (Method method : methods) {
                sb.append(method);
            }

            return sb.substring(0, sb.length() - 1);
        }

        public static Object nvl(Object str, Object val) {
            return str == null || "".equals(str) ? val : str;
        }

        public static Object _in(Object one, Object... objects) {
            if (objects == null)
                return true;
            if (objects.length == 1)
                return false;

            for (Object object : objects) {
                if (one.equals(object))
                    return true;
            }

            return false;
        }
    }
}
