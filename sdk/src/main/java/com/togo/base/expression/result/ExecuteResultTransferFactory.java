package com.togo.base.expression.result;

import java.util.List;
import java.util.Map;

/**
 * @Author taiyn
 * @Description factory
 * @Date 9:48 下午 2020/4/21
 **/
public class ExecuteResultTransferFactory {

    private static ExecuteResultListTransfer listTransfer = new ExecuteResultListTransfer();
    private static ExecuteResultMapTransfer mapTransfer = new ExecuteResultMapTransfer();
    private static ExecuteResultDefaultTransfer defaultTransfer = new ExecuteResultDefaultTransfer();

    public static ExecuteResultTransfer select(Object object) {

        if (object instanceof List)
            return listTransfer;
        if (object instanceof Map)
            return mapTransfer;

        return defaultTransfer;

    }
}
