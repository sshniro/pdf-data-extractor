package com.data.extractor.model.beans.template.info.pattern;

import com.data.extractor.model.beans.template.info.regex.RegexDataElement;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class PatternDataElement {

    @Expose
    private List<ColumnDataElement> columnDataElements =new ArrayList<ColumnDataElement>();
    @Expose
    private RegexDataElement regexDataElement;

    public List<ColumnDataElement> getColumnDataElements() {
        return columnDataElements;
    }

    public void setColumnDataElements(List<ColumnDataElement> columnDataElements) {
        this.columnDataElements = columnDataElements;
    }

    public RegexDataElement getRegexDataElement() {
        return regexDataElement;
    }

    public void setRegexDataElement(RegexDataElement regexDataElement) {
        this.regexDataElement = regexDataElement;
    }
}
