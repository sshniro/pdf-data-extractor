package com.data.extractor.model.beans.template.info.pattern;

import java.util.ArrayList;
import java.util.List;

public class TableDataBean {

    private String tableName;
    private String tableStartTag;
    private String tableEndTag;
    private List<ColumnDataBean> columnDataBeanList=new ArrayList<ColumnDataBean>();


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

    public List<ColumnDataBean> getColumnDataBeanList() {
        return columnDataBeanList;
    }

    public void setColumnDataBeanList(List<ColumnDataBean> columnDataBeanList) {
        this.columnDataBeanList = columnDataBeanList;
    }
}
