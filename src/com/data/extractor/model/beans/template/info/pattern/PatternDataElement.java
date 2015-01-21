package com.data.extractor.model.beans.template.info.pattern;

import com.data.extractor.controllers.*;
import com.data.extractor.controllers.HeaderDataBean;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class PatternDataElement {

    @Expose
    String extractedId;
    @Expose
    private List<TableDataBean> tableDataBeanList = new ArrayList<TableDataBean>();
    @Expose
    private List<HeaderDataBean> headerDataBeanList = new ArrayList<HeaderDataBean>();


    public List<HeaderDataBean> getHeaderDataBeanList() {
        return headerDataBeanList;
    }

    public void setHeaderDataBeanList(List<HeaderDataBean> headerDataBeanList) {
        this.headerDataBeanList = headerDataBeanList;
    }
    public String getExtractedId() {
        return extractedId;
    }

    public void setExtractedId(String extractedId) {
        this.extractedId = extractedId;
    }

    public List<TableDataBean> getTableDataBeanList() {
        return tableDataBeanList;
    }

    public void setTableDataBeanList(List<TableDataBean> tableDataBeanList) {
        this.tableDataBeanList = tableDataBeanList;
    }
}
