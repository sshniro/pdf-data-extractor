package com.data.extractor.model.beans.templates;

import com.google.gson.annotations.Expose;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Template {

    @Expose
    private String templateName;
    @Expose
    private String pdfFile;

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getPdfFile() {  return pdfFile;   }

    public void setPdfFile(String pdfFile) {  this.pdfFile = pdfFile;  }

}