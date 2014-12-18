package com.data.extractor.model.beans.template.info.text;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class TextDataParser {

    @Expose
    private String id;
    @Expose
    private String templateName;
    @Expose
    private String dataType;
    @Expose
    private String pdfFile;
    @Expose
    private List<TextDataElement> textDataElements = new ArrayList<TextDataElement>();

    public String getId() {        return id;    }

    public void setId(String id) {        this.id = id;    }

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
