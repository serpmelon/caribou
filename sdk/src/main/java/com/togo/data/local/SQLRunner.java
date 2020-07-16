package com.togo.data.local;

import com.togo.data.model.InvokedUnit;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;

/**
 * @Author taiyn
 * @Description launch sql
 * @Date 5:04 下午 2020/5/25
 **/
interface SQLRunner {

    List<Map<String, Object>> run(InvokedUnit invokedUnit, SqlSession sqlSession, Map<String, Object> parameter);
}
