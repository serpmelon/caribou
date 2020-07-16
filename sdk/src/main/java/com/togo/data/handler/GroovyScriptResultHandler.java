package com.togo.data.handler;

import com.togo.base.expression.ExpressionShell;
import groovy.lang.Script;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author taiyn
 * @Description groovy script
 * @Date 6:45 下午 2020/6/10
 **/
public class GroovyScriptResultHandler extends AbstractResultHandler implements ResultHandler {

    private Script script;

    public GroovyScriptResultHandler(String expression) {
        super(expression);

        this.script = ExpressionShell.parse(expression);
    }

    @Override
    public void handle(List<Map<String, Object>> result) {

        if (result == null || result.size() == 0)
            return;

        ExpressionShell.execute(script, result);
    }

    public static void main(String[] args) throws InterruptedException {

        String expression = "for (val in parameter) {\n" +
                "            println(val.name.test())\n" +
                "        }";
        Map<String, Object> map = new HashMap<>();
        GroovyTest test = new GroovyTest();
        map.put("name", test);
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(map);

        while (true) {
            System.out.println("1" + map);
            ExpressionShell.execute(ExpressionShell.parse(expression), list);
            System.out.println("2" + map);
            Thread.sleep(10);
        }
    }

    static class GroovyTest{

        public String test() {

            return "wahaha";
        }
    }
}
