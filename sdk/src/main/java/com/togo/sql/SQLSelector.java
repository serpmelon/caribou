package com.togo.sql;


import com.togo.model.SQLModel;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;
import java.util.Map;

/**
 * @Author taiyn
 * @Description sql select service
 * @Date 5:21 下午 2020/5/25
 **/
public interface SQLSelector {

    void addAllSQL(List<SQLModel> sqlModelList);

    void addSQL(SQLModel sqlModel);

    /**
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @Author taiyn
     * @Description run sql by query id
     * @Date 8:21 上午 2020/5/26
     * @Param [queryId]
     **/
    List<Map<String, Object>> run(String queryId, Map<String, Object> parameter);

    SqlSessionFactory getSqlSessionFactory();

    Configuration getConfiguration();

    /**
     * @Author taiyn
     * @Description return true if the queryId exists in sqlContext
     *
     * @Date 9:19 上午 2020/5/27
     * @Param [queryId]
     * @return boolean
     **/
    boolean exists(String queryId);

    String getDataSourceName();
}
