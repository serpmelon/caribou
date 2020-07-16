package com.togo.data.local;

import com.togo.data.model.InvokedUnit;
import com.togo.data.model.SQLModel;
import org.apache.ibatis.session.Configuration;

import java.util.List;

/**
 * @Author taiyn
 * @Description sql parser
 * @Date 3:45 下午 2020/5/25
 **/
interface SQLXmlParser {

    List<InvokedUnit> parse(List<SQLModel> sqlModelList, Configuration configuration);

    InvokedUnit parse(SQLModel model, Configuration configuration);
}
