package com.data.extractor.model.beans.template.info.regex;

import com.data.extractor.model.beans.template.info.RawDataElement;
import com.data.extractor.model.beans.template.info.pattern.ColumnDataElement;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class RegexDataElement {
    @Expose
    String metaName;
    @Expose
    String startTag;
    @Expose
    String endTag;
    @Expose
    String value;
    @Expose
    String dictionaryId;
    @Expose
    RawDataElement rawData;
    @Expose
    private List<RegexPairElement> regexPairElements = new ArrayList<RegexPairElement>();

    public String getStartTag() {
        return startTag;
    }

    public void setStartTag(String startTag) {
        this.startTag = startTag;
    }

    public String getEndTag() {
        return endTag;
    }

    public void setEndTag(String endTag) {
        this.endTag = endTag;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMetaName() {
        return metaName;
    }

    public void setMetaName(String metaName) {
        this.metaName = metaName;
    }

    public String getDictionaryId() {
        return dictionaryId;
    }

    public void setDictionaryId(String dictionaryId) {
        this.dictionaryId = dictionaryId;
    }

    public RawDataElement getRawData() {
        return rawData;
    }

    public void setRawData(RawDataElement rawData) {
        this.rawData = rawData;
    }

    public List<RegexPairElement> getRegexPairElements() {
        return regexPairElements;
    }

    public void setRegexPairElements(List<RegexPairElement> regexPairElements) {
        this.regexPairElements = regexPairElements;
    }
}
