package com.data.extractor.model.beans.template.info.text;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class TextDataParser {

    @SerializedName("_id")
    @Expose
    private Id id;
    @Expose
    private String mainCategory;
    @Expose
    private String subCategory;
    @Expose
    private String templateName;
    @Expose
    private String dataType;
    @Expose
    private String pdfFile;
    @Expose
    private List<TextDataElement> textDataElements = new ArrayList<TextDataElement>();

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public String getMainCategory() {
        return mainCategory;
    }

    public void setMainCategory(String mainCategory) {
        this.mainCategory = mainCategory;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public List<TextDataElement> getTextDataElements() {
        return textDataElements;
    }

    public void setTextDataElements(List<TextDataElement> textDataElements) {
        this.textDataElements = textDataElements;
    }

    public String getPdfFile() { return pdfFile;  }

    public void setPdfFile(String pdfFile) { this.pdfFile = pdfFile;  }

}
