package com.data.extractor.model.beans.template.info.pattern;

import com.data.extractor.model.beans.template.info.regex.RegexDataElement;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class PatternDataElement {

    @Expose
    private List<ColumnDataElement> columnDataElementList =new ArrayList<ColumnDataElement>();
    @Expose
    private List<RegexDataElement> regexDataElementList = new ArrayList<RegexDataElement>();

    public List<ColumnDataElement> getColumnDataElementList() {
        return columnDataElementList;
    }

    public void setColumnDataElementList(List<ColumnDataElement> columnDataElementList) {
        this.columnDataElementList = columnDataElementList;
    }

    public List<RegexDataElement> getRegexDataElementList() {
        return regexDataElementList;
    }

    public void setRegexDataElementList(List<RegexDataElement> regexDataElementList) {
        this.regexDataElementList = regexDataElementList;
    }
}
