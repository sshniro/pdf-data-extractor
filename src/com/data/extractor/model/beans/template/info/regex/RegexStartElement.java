package com.data.extractor.model.beans.template.info.regex;

import com.google.gson.annotations.Expose;

public class RegexStartElement {
    @Expose
    private String tag;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
