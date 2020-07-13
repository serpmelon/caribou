package com.togo.sql;

import com.togo.model.InvokedUnit;
import com.togo.model.SQLModel;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.session.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @Author taiyn
 * @Description default sql parser
 * @Date 5:57 下午 2020/5/25
 **/
class DefaultSQLXmlParser implements SQLXmlParser {

    private static Logger logger = LoggerFactory.getLogger(DefaultSQLXmlParser.class);

    private final String BASE_PACKAGE = SQLCraftFactory.INSTANCE.createSQLContext().getBasePackage();

    private final String XML_HEAD = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n";
    private final String METHOD_NAME = "commonQuery";
    private final String CLASS_NAME = "CommonMapper";

    private final SQLContext sqlContext = SQLCraftFactory.INSTANCE.createSQLContext();

    @Override
    public InvokedUnit parse(SQLModel sqlModel, Configuration configuration) {

        logger.info("parse query id: [{}]", sqlModel.getQueryId());

        XMLMapperInfo xmlMapperInfo = xmlMapper(Collections.singletonList(sqlModel));

        List<InvokedUnit> invokedUnitList = createMapper(xmlMapperInfo, configuration);
        return invokedUnitList.get(0);
    }

    @Override
    public List<InvokedUnit> parse(List<SQLModel> sqlModelList, Configuration configuration) {

        XMLMapperInfo xmlMapperInfo = xmlMapper(sqlModelList);

        return createMapper(xmlMapperInfo, configuration);
    }

    private List<InvokedUnit> createMapper(XMLMapperInfo xmlMapperInfo, Configuration configuration) {

        String mapper = xmlMapperInfo.mapper;
        InputStream inputStream = new ByteArrayInputStream(mapper.getBytes());

        // resource is a id to mapper.xml
        ErrorContext.instance().resource(xmlMapperInfo.className);
        XMLMapperBuilder mapperParser = new XMLMapperBuilder(inputStream, configuration, xmlMapperInfo.className, configuration.getSqlFragments());
        mapperParser.parse();
        // to bytes
        byte[] bytes = new byte[0];
        try {
            bytes = SQLMapperLoader.dump(xmlMapperInfo.className, new ArrayList<>(xmlMapperInfo.queryIdAndMethod.values()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // custom ClassLoader
        SQLMapperLoader cl = new SQLMapperLoader();
        Class<?> clazz = cl.defineClass(BASE_PACKAGE + "." + xmlMapperInfo.className, bytes);
        configuration.addMapper(clazz);

        List<InvokedUnit> invokedUnitList = new ArrayList<>();
        xmlMapperInfo.queryIdAndMethod.forEach((k, v) -> {

            try {
                Method method = clazz.getMethod(v, Map.class);
                invokedUnitList.add(new InvokedUnit(k, clazz, method));
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        });

        return invokedUnitList;
    }

    private XMLMapperInfo xmlMapper(List<SQLModel> sqlModelList) {

        String className = CLASS_NAME + "_" + sqlContext.incrementAndGetClassCounter();

        StringBuilder mapperBuilder = new StringBuilder(XML_HEAD + "<mapper namespace=\"" + BASE_PACKAGE + "." + className + "\">\n");

        Map<String, String> queryIdAndMethod = new HashMap<>();
        sqlModelList.forEach(item -> {

            int methodCounter = sqlContext.incrementAndGetMethodCounter();
            String methodName = METHOD_NAME + "_" + methodCounter;
            String mapper = "<select id=\"" + methodName + "\" resultType=\"hashmap\" >\n" +
                    item.getSqlText() + "</select> \n";
            mapperBuilder.append(mapper);
            queryIdAndMethod.put(item.getQueryId(), methodName);
        });

        mapperBuilder.append("</mapper>");
        return new XMLMapperInfo(className, queryIdAndMethod, mapperBuilder.toString());
    }

    class XMLMapperInfo {
        private String className;
        private Map<String, String> queryIdAndMethod;
        private String mapper;

        XMLMapperInfo(String className, Map<String, String> queryIdAndMethod, String mapper) {
            this.className = className;
            this.queryIdAndMethod = queryIdAndMethod;
            this.mapper = mapper;
        }
    }
}
