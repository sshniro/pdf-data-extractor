package com.data.extractor.model.beans.template.info.regex;

import com.data.extractor.model.beans.template.info.pattern.HeaderDataBean;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class RegexDataParser {

    @Expose
    private String id;
    @Expose
    private String dataType;
    @Expose
    private String pdfFile;
    @Expose
    private List<RegexDataElement> regexDataElementList = new ArrayList<RegexDataElement>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getPdfFile() {
        return pdfFile;
    }

    public void setPdfFile(String pdfFile) {
        this.pdfFile = pdfFile;
    }

    public List<RegexDataElement> getRegexDataElementList() {
        return regexDataElementList;
    }

    public void setRegexDataElementList(List<RegexDataElement> regexDataElementList) {
        this.regexDataElementList = regexDataElementList;
    }
}
