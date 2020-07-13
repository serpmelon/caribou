package com.togo.sql;

import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author taiyn
 * @Description sql parser
 * @Date 3:45 下午 2020/5/25
 **/
public class SQLXmlParserBk {

//    private SqlSessionFactory sqlSessionFactory;
//    private SqlSession sqlSession;
//    private TTradeCommonQueryMapper queryMapper;
//
//    public void init() throws Exception {
//
//        List<TTradeCommonQuery> tradeCommonQueryList = queryMapper.listAll();
//
//        String mapper = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
//                "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n" +
//                "<mapper namespace=\"com.ziroom.trade.rent.inquire.repository.TestMapper\">\n" +
//                "<select id=\"commonQuery\" resultType=\"hashmap\" >\n" +
//                "        select * \n" +
//                "        from app_state_change_log\n" +
//                "        where id = #{id}\n" +
//                "    </select>" +
//                "\n" +
//                "\n" +
//                "</mapper>";
//        InputStream inputStream = new ByteArrayInputStream(mapper.getBytes());
//        Configuration configuration = sqlSessionFactory.getConfiguration();
//
//        ErrorContext.instance().resource("resource");
//        XMLMapperBuilder mapperParser = new XMLMapperBuilder(inputStream, configuration, "resource", configuration.getSqlFragments());
//        mapperParser.parse();
//        // 生成二进制字节码
//        byte[] bytes = SQLMapperLoader.dump("TestMapper", Collections.singletonList("commonQuery"));
//
//        // 使用自定义的ClassLoader
//        SQLMapperLoader cl = new SQLMapperLoader();
//        Class<?> clazz = cl.defineClass("com.ziroom.trade.rent.inquire.repository.TestMapper", bytes);
//        configuration.addMapper(clazz);
//
//        Method query = clazz.getMethod("commonQuery", Map.class);
//        Object testMapper = sqlSession.getMapper(clazz);
//        Map<String, Object> parameter = new HashMap<>();
//        parameter.put("id", "1034");
//        Object result = query.invoke(testMapper, parameter);
//
//        System.out.println("dyn : " + result);
//    }
}
