package com.data.extractor.model.beans.template.info.regex;


import com.google.gson.annotations.Expose;

public class RegexPairElement {

    @Expose
    private String metaName;
    @Expose
    private String value;
    @Expose
    private RegexStartElement regexStartElement;
    @Expose
    private RegexEndElement regexEndElement;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public RegexStartElement getRegexStartElement() {
        return regexStartElement;
    }

    public void setRegexStartElement(RegexStartElement regexStartElement) {
        this.regexStartElement = regexStartElement;
    }

    public RegexEndElement getRegexEndElement() {
        return regexEndElement;
    }

    public void setRegexEndElement(RegexEndElement regexEndElement) {
        this.regexEndElement = regexEndElement;
    }

    public String getMetaName() {
        return metaName;
    }

    public void setMetaName(String metaName) {
        this.metaName = metaName;
    }
}
