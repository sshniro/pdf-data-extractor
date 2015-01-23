package com.data.extractor.model.beans.template.info.regex;

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
    private List<RegexDataElement> regexDataElements = new ArrayList<RegexDataElement>();

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

    public List<RegexDataElement> getRegexDataElements() {
        return regexDataElements;
    }

    public void setRegexDataElements(List<RegexDataElement> regexDataElements) {
        this.regexDataElements = regexDataElements;
    }
}
