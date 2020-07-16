package com.togo.data.model;


/**
 * @Author taiyn
 * @Description sql model
 * @Date 5:28 下午 2020/5/25
 **/
public class SQLModel {

    private String sqlText;
    private String queryId;
    private String resultShell;
    private int version;

    public static SQLModelBuilder builder() {
        return new SQLModelBuilder();
    }

    private SQLModel(String sqlText, String queryId, String resultShell, int version) {
        this.sqlText = sqlText;
        this.queryId = queryId;
        this.resultShell = resultShell;
        this.version = version;
    }

    public String getSqlText() {
        return sqlText;
    }

    public String getQueryId() {
        return queryId;
    }

    public int getVersion() {
        return version;
    }

    public String getResultShell() {
        return resultShell;
    }

    public static class SQLModelBuilder {

        private String sqlText;
        private String queryId;
        private String resultShell;
        private int version;

        public SQLModelBuilder sql(String sqlText) {
            this.sqlText = sqlText;
            return this;
        }

        public SQLModelBuilder queryId(String queryId) {
            this.queryId = queryId;
            return this;
        }

        public SQLModelBuilder resultShell(String resultShell) {
            this.resultShell = resultShell;
            return this;
        }

        public SQLModelBuilder version(int version) {
            this.version = version;
            return this;
        }

        public SQLModel build() {
            return new SQLModel(sqlText, queryId, resultShell, version);
        }
    }
}
