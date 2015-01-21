package com.data.extractor.model.beans.template.info.regex;

import com.data.extractor.model.beans.template.info.pattern.HeaderDataBean;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class RegexDataParser {

    @Expose
    private String id;
    @Expose
    private String pdfFile;
    @Expose
    private List<RegexDataElement> regexDataParserList = new ArrayList<RegexDataElement>();

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

    public List<RegexDataElement> getRegexDataParserList() {
        return regexDataParserList;
    }

    public void setRegexDataParserList(List<RegexDataElement> regexDataParserList) {
        this.regexDataParserList = regexDataParserList;
    }
}
