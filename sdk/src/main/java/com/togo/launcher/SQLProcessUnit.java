package com.togo.launcher;

import com.togo.sql.DefaultSQLSelector;
import com.togo.sql.SQLSelector;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;
import java.util.Map;

/**
 * @Author taiyn
 * @Description the sql process unit
 * @Date 5:26 下午 2020/6/30
 **/
class SQLProcessUnit implements ProcessUnit {

    /**
     *
     */
    private final SqlSessionFactory sqlSessionFactory;

    private final SQLSelector sqlSelector;

    private final String queryId;

    SQLProcessUnit(SqlSessionFactory sqlSessionFactory, String queryId) {
        this.sqlSessionFactory = sqlSessionFactory;
        this.sqlSelector = new DefaultSQLSelector(sqlSessionFactory);
        this.queryId = queryId;
    }

    @Override
    public ProcessUnitResult execute(Map<String, Object> parameter) {

        List<Map<String, Object>> mapList = sqlSelector.run(queryId, parameter);

        return buildResult(mapList);
    }

    private ProcessUnitResult buildResult(List<Map<String, Object>> mapList) {
        return new SQLProcessUnitResult(mapList);
    }

    @Override
    public String id() {
        return queryId;
    }

    @Override
    public int type() {
        return 0;
    }

    private class SQLProcessUnitResult implements ProcessUnitResult {

        private List<Map<String, Object>> result;

        private SQLProcessUnitResult(List<Map<String, Object>> result) {
            this.result = result;
        }

        @Override
        public List<Map<String, Object>> result() {
            return result;
        }
    }
}
