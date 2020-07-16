package com.togo.assemble.launcher;

import org.apache.ibatis.session.SqlSessionFactory;

/**
 * @Author taiyn
 * @Description TODO
 * @Date 6:06 下午 2020/6/30
 **/
public class SQLProcessUnitRecipe implements ProcessUnitRecipe {

    private final SqlSessionFactory sqlSessionFactory;

    private final String queryId;

    public SQLProcessUnitRecipe(SqlSessionFactory sqlSessionFactory, String queryId) {
        this.sqlSessionFactory = sqlSessionFactory;
        this.queryId = queryId;
    }

    @Override
    public ProcessUnit cook() {

        return new SQLProcessUnit(sqlSessionFactory, queryId);
    }
}
