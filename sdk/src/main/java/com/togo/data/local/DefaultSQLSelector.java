package com.togo.data.local;


import com.togo.base.exception.NotFoundException;
import com.togo.data.handler.ResultHandler;
import com.togo.data.model.InvokedUnit;
import com.togo.data.model.SQLModel;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author taiyn
 * @Description sql select service
 * @Date 5:21 下午 2020/5/25
 **/
public class DefaultSQLSelector implements SQLSelector {

    private static Logger logger = LoggerFactory.getLogger(DefaultSQLSelector.class);

    private SqlSessionFactory sqlSessionFactory;
    private SQLContext sqlContext;
    private SQLRunner sqlRunner;
    private Configuration configuration;

    private SQLCraftFactory sqlCraftFactory;

    /**
     * the data source name
     */
    private String dataSourceName;

    public DefaultSQLSelector(SqlSessionFactory sqlSessionFactory) {

        this(sqlSessionFactory, "default");
    }

    public DefaultSQLSelector(SqlSessionFactory sqlSessionFactory, String dataSourceName) {

        this.sqlSessionFactory = sqlSessionFactory;
        this.sqlCraftFactory = SQLCraftFactory.INSTANCE;
        this.sqlContext = sqlCraftFactory.createSQLContext();
        this.sqlRunner = sqlCraftFactory.createSQLRunner();
        this.configuration = sqlSessionFactory.getConfiguration();
        this.dataSourceName = dataSourceName;
    }

    @Override
    public void addAllSQL(List<SQLModel> sqlModelList) {

        Map<Integer, List<SQLModel>> map = sqlModelList.stream().collect(Collectors.groupingBy(SQLModel::getVersion));

        map.forEach((k, v) -> {
            SQLXmlParser sqlXmlParser = sqlCraftFactory.createSQLXmlParser(k);
            List<InvokedUnit> invokedUnitList = sqlXmlParser.parse(v, configuration);
            sqlContext.addAll(invokedUnitList);
            v.forEach(sqlModel -> sqlContext.addResultHandlerRuleShell(sqlModel.getQueryId(), sqlModel.getResultShell()));
        });
    }

    @Override
    public void addSQL(SQLModel sqlModel) {

        logger.info("add sql, id: [{}]", sqlModel.getQueryId());
        SQLXmlParser sqlXmlParser = sqlCraftFactory.createSQLXmlParser(sqlModel.getVersion());
        InvokedUnit invokedUnit = sqlXmlParser.parse(sqlModel, configuration);
        sqlContext.add(invokedUnit);
        sqlContext.addResultHandlerRuleShell(sqlModel.getQueryId(), sqlModel.getResultShell());
    }

    /**
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @Author taiyn
     * @Description run sql by query id
     * @Date 8:21 上午 2020/5/26
     * @Param [queryId]
     **/
    @Override
    public List<Map<String, Object>> run(String queryId, Map<String, Object> parameter) {

        Optional<InvokedUnit> optionalInvokedUnit = sqlContext.getInvokedUnit(queryId);
        if (!optionalInvokedUnit.isPresent())
            throw new NotFoundException("found nothing about queryId: " + queryId);

        InvokedUnit invokedUnit = optionalInvokedUnit.get();
        // TODO taiyn 2020/5/25 这里通过注解获取到的session和open获取到的不同在哪?
        // TODO taiyn 2020/5/26 SqlSession 连接池?
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {

            List<Map<String, Object>> result = sqlRunner.run(invokedUnit, sqlSession, parameter);
            Optional<String> resultHandlerRuleShell = sqlContext.getResultHandlerRuleShell(queryId);
            if (resultHandlerRuleShell.isPresent()) {

                ResultHandler resultHandler = sqlCraftFactory.createResultHandler(resultHandlerRuleShell.get());
                resultHandler.handle(result);
            }

            return result;
        }
    }

    @Override
    public SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

    @Override
    public boolean exists(String queryId) {
        return sqlContext.getInvokedUnit(queryId).isPresent();
    }

    @Override
    public String getDataSourceName() {
        return dataSourceName;
    }
}
