package com.data.extractor.model.beans.template.info.pattern;

public class TableDataBean {
    private String tableName;
    private String tableStartTag;
    private String tableEndTag;


    public String getTableEndTag() {
        return tableEndTag;
    }

    public void setTableEndTag(String tableEndTag) {
        this.tableEndTag = tableEndTag;
    }

    public String getTableStartTag() {
        return tableStartTag;
    }

    public void setTableStartTag(String tableStartTag) {
        this.tableStartTag = tableStartTag;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
