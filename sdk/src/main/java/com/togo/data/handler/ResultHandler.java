package com.togo.data.handler;

import java.util.List;
import java.util.Map;

/**
 * @Author taiyn
 * @Description the result of sql or api maybe need to be handled, such as contract_state -> valid contract or invalid contract
 * @Date 6:58 下午 2020/6/10
 **/
public interface ResultHandler {

    void handle(List<Map<String, Object>> result);
}
