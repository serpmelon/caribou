package com.togo.api;

import java.util.List;
import java.util.Map;

/**
 * @Author taiyn
 * @Description 通用查询对外暴露接口
 * @Date 10:16 下午 2020/7/14
 **/
public interface Caribou {

    void init();

    List<Map<String, Object>> run();

    String add();


}