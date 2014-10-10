package com.data.extractor.model.beans.template.info.image;

import com.google.gson.annotations.Expose;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class ImageDataElement {

    @Expose
    private String metaId;
    @Expose
    private String metaName;
    @Expose
    private int pageNumber;
    @Expose
    private int pageRotation;
    @Expose
    private Double totalX1;
    @Expose
    private Double totalY1;
    @Expose
    private Double totalWidth;
    @Expose
    private Double totalHeight;
    @Expose
    private Double pdfWidth;
    @Expose
    private Double pdfHeight;
    @Expose
    private String extractedImage;

    public String getMetaId() {
        return metaId;
    }

    public void setMetaId(String metaId) {
        this.metaId = metaId;
    }

    public String getMetaName() {
        return metaName;
    }

    public void setMetaName(String metaName) {
        this.metaName = metaName;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageRotation() {
        return pageRotation;
    }

    public void setPageRotation(int pageRotation) {
        this.pageRotation = pageRotation;
    }

    public Double getTotalX1() {
        return totalX1;
    }

    public void setTotalX1(Double totalX1) {
        this.totalX1 = totalX1;
    }

    public Double getTotalY1() {
        return totalY1;
    }

    public void setTotalY1(Double totalY1) {
        this.totalY1 = totalY1;
    }

    public Double getTotalWidth() {
        return totalWidth;
    }

    public void setTotalWidth(Double totalWidth) {
        this.totalWidth = totalWidth;
    }

    public Double getTotalHeight() {
        return totalHeight;
    }

    public void setTotalHeight(Double totalHeight) {
        this.totalHeight = totalHeight;
    }

    public Double getPdfWidth() {
        return pdfWidth;
    }

    public void setPdfWidth(Double pdfWidth) {        this.pdfWidth = pdfWidth;    }

    public Double getPdfHeight() {        return pdfHeight;    }

    public void setPdfHeight(Double pdfHeight) {        this.pdfHeight = pdfHeight;    }

    public String getExtractedImage() {        return extractedImage;    }

    public void setExtractedImage(String extractedImage) {        this.extractedImage = extractedImage;    }

}