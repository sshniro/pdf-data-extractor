package com.data.extractor.model.beans.template.info.regex;

import com.google.gson.annotations.Expose;

public class RegexDataElement {
    @Expose
    String regexName;
    @Expose
    String startTag;
    @Expose
    String endTag;
    @Expose
    String value;

    public String getRegexName() {
        return regexName;
    }

    public void setRegexName(String regexName) {
        this.regexName = regexName;
    }

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
}
