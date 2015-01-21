package com.data.extractor.model.beans.template.info.pattern;

import com.data.extractor.controllers.*;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class TableDataBean {

    @Expose
    private String tableName;
    @Expose
    private String tableStartTag;
    @Expose
    private String tableEndTag;
    @Expose
    private List<ColumnDataBean> columnDataBeanList=new ArrayList<ColumnDataBean>();
    @Expose
    private List<HeaderDataBean> headerDataBeanList=new ArrayList<HeaderDataBean>();


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

    public List<HeaderDataBean> getHeaderDataBeanList() {
        return headerDataBeanList;
    }

    public void setHeaderDataBeanList(List<HeaderDataBean> headerDataBeanList) {
        this.headerDataBeanList = headerDataBeanList;
    }
}
