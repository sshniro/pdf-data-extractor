package com.data.extractor.model.beans.template.info.pattern;

import com.data.extractor.controllers.*;
import com.data.extractor.controllers.HeaderDataBean;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class PatternDataParser {

    @Expose
    String id;
    @Expose
    String extractedId;
    @Expose
    private String pdfFile;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPdfFile() {
        return pdfFile;
    }

    public void setPdfFile(String pdfFile) {
        this.pdfFile = pdfFile;
    }
}
