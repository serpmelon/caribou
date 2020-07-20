package com.togo.api;

import com.togo.base.config.Config;
import com.togo.data.local.SQLSelector;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;
import java.util.Map;

/**
 * @Author taiyn
 * @Description common query api
 * @Date 10:16 下午 2020/7/14
 **/
public class Caribou {

    private SqlSessionFactory sqlSessionFactory;
    private SQLSelector selector;

    private static final String DEFAULT_DATASOURCE_ID = "default";

    private static boolean initialized = false;

    private Caribou(SqlSessionFactory sqlSessionFactory, String sourceId) {

    }

    private Caribou(SqlSessionFactory sqlSessionFactory) {

        this(sqlSessionFactory, DEFAULT_DATASOURCE_ID);
    }

    public static void init() {

        String path = "caribou.properties";
        Config config = Config.getInstance(path);
        System.out.println(config.getName());
    }

//    List<Map<String, Object>> run();
//
//    String add();
}
