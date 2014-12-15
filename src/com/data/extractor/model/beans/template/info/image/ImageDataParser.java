package com.data.extractor.model.beans.template.info.image;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class ImageDataParser {

    private String id;
    @Expose
    private String templateName;
    @Expose
    private String dataType;
    @Expose
    private String pdfFile;
    @Expose
    private List<ImageDataElement> imageDataElements = new ArrayList<ImageDataElement>();

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

    public List<ImageDataElement> getImageDataElements() {
        return imageDataElements;
    }

    public void setImageDataElements(List<ImageDataElement> imageDataElements) {
        this.imageDataElements = imageDataElements;
    }

    public String getPdfFile() { return pdfFile;  }

    public void setPdfFile(String pdfFile) { this.pdfFile = pdfFile;  }

}