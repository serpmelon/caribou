package com.togo.sql;


import com.togo.handler.GroovyScriptResultHandler;
import com.togo.handler.ResultHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author taiyn
 * @Description create all crafts about sql
 * @Date 5:08 下午 2020/5/25
 **/
public enum SQLCraftFactory {

    INSTANCE;

    private static SQLContext sqlContext = new SQLContext();
    private static SQLRunner sqlRunner = new DefaultSQLRunner();
    private static SQLXmlParser sqlXmlParser = new DefaultSQLXmlParser();
    private static Map<String, ResultHandler> resultHandlerMap = new ConcurrentHashMap<>();

    public SQLContext createSQLContext() {
        return sqlContext;
    }

    public SQLRunner createSQLRunner() {
        return sqlRunner;
    }

    public SQLXmlParser createSQLXmlParser(int version) {
        if (version == 1)
            return sqlXmlParser;

        throw new UnsupportedOperationException("unsupported parser version");
    }

    public ResultHandler createResultHandler(String expression) {

        return createResultHandler(expression, ResultHandlerType.GROOVY.type);
    }

    // just groovy now
    private ResultHandler createResultHandler(String expression, String handlerType) {

        ResultHandler resultHandler = resultHandlerMap.get(handlerType);
        if (resultHandler == null) {
            resultHandler = new GroovyScriptResultHandler(expression);
            resultHandlerMap.put(handlerType, resultHandler);
        }

        return resultHandler;
    }

    enum ResultHandlerType {
        GROOVY("groovy");

        public final String type;

        ResultHandlerType(String type) {
            this.type = type;
        }
    }
}
