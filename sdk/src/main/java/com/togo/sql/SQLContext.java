package com.togo.sql;



import com.togo.model.InvokedUnit;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author taiyn
 * @Description sql context, save sql and method
 * @Date 5:04 下午 2020/5/25
 **/
public class SQLContext {

    /**
     * sql execution unit collection
     */
    private Map<String, InvokedUnit> invokedUnitMap = new ConcurrentHashMap<>();
    /**
     *
     */
    private Map<String, String> resultHandlerRuleShellMap = new ConcurrentHashMap<>();
    /**
     * query_1, query_2
     */
    private AtomicInteger methodCounter = new AtomicInteger(0);
    /**
     * CommonMapper_1, CommonMapper_2
     */
    private AtomicInteger classCounter = new AtomicInteger(0);

    private String BASE_PACKAGE = "com.ziroom.trade.rent.inquire.repository";

    void add(InvokedUnit unit) {

        invokedUnitMap.put(unit.getQueryId(), unit);
    }

    void addAll(List<InvokedUnit> unitList) {

        unitList.forEach(item -> invokedUnitMap.put(item.getQueryId(), item));
    }

    Optional<InvokedUnit> getInvokedUnit(String queryId) {
        return Optional.ofNullable(invokedUnitMap.get(queryId));
    }

    void addResultHandlerRuleShell(String queryId, String expression) {
        if (Objects.nonNull(expression))
            resultHandlerRuleShellMap.put(queryId, expression);
    }

    Optional<String> getResultHandlerRuleShell(String queryId) {
        return Optional.ofNullable(resultHandlerRuleShellMap.get(queryId));
    }

    int incrementAndGetMethodCounter() {
        return methodCounter.incrementAndGet();
    }

    int incrementAndGetClassCounter() {
        return classCounter.incrementAndGet();
    }

    String getBasePackage() {
        return BASE_PACKAGE;
    }
}
