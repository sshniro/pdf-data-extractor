package com.data.extractor.model.beans.template.info.text;

import com.google.gson.annotations.Expose;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class TextDataElement {

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
    private Double metaX1;
    @Expose
    private Double metaY1;
    @Expose
    private Double metaWidth;
    @Expose
    private Double metaHeight;
    @Expose
    private Double pdfWidth;
    @Expose
    private Double pdfHeight;
    @Expose
    private String extractedText;

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

    public Double getMetaX1() {
        return metaX1;
    }

    public void setMetaX1(Double metaX1) {
        this.metaX1 = metaX1;
    }

    public Double getMetaY1() {
        return metaY1;
    }

    public void setMetaY1(Double metaY1) {
        this.metaY1 = metaY1;
    }

    public Double getMetaWidth() {  return metaWidth; }

    public void setMetaWidth(Double metaWidth) {
        this.metaWidth = metaWidth;
    }

    public Double getMetaHeight() {
        return metaHeight;
    }

    public void setMetaHeight(Double metaHeight) {
        this.metaHeight = metaHeight;
    }

    public Double getPdfWidth() {
        return pdfWidth;
    }

    public void setPdfWidth(Double pdfWidth) {        this.pdfWidth = pdfWidth;    }

    public Double getPdfHeight() {        return pdfHeight;    }

    public void setPdfHeight(Double pdfHeight) {        this.pdfHeight = pdfHeight;    }

    public String getExtractedText() {        return extractedText;    }

    public void setExtractedText(String extractedText) {        this.extractedText = extractedText;    }

}