package com.data.extractor.model.beans.template.info.pattern;

import com.data.extractor.model.beans.template.info.regex.RegexDataElement;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class PatternDataElement {

    @Expose
    private List<ColumnDataElement> columnDataElements =new ArrayList<ColumnDataElement>();
    @Expose
    private RegexDataElement regexDataElements;
    @Expose
    private List<List<String>> rows = new ArrayList<List<String>>();

    public List<ColumnDataElement> getColumnDataElements() {
        return columnDataElements;
    }

    public void setColumnDataElements(List<ColumnDataElement> columnDataElements) {
        this.columnDataElements = columnDataElements;
    }

    public RegexDataElement getRegexDataElements() {
        return regexDataElements;
    }

    public void setRegexDataElements(RegexDataElement regexDataElements) {
        this.regexDataElements = regexDataElements;
    }

    public List<List<String>> getRows() {
        return rows;
    }

    public void setRows(List<List<String>> rows) {
        this.rows = rows;
    }
}
