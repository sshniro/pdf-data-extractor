package com.data.extractor.controllers;

import com.google.gson.annotations.Expose;

/**
 * Created by niro273 on 1/15/15.
 */
public class HeaderDataBean {

    @Expose
    String headerName;
    @Expose
    String startTag;
    @Expose
    String endTag;
    @Expose
    String value;

    public String getHeaderName() {
        return headerName;
    }

    public void setHeaderName(String headerName) {
        this.headerName = headerName;
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
