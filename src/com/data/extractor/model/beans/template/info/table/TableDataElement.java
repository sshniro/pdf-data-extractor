package com.data.extractor.model.beans.template.info.table;

import com.data.extractor.model.beans.template.info.RawDataElement;
import com.google.gson.annotations.Expose;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class TableDataElement {

    @Expose
    private RawDataElement rawData;
    @Expose
    private String metaId; // Unique filed generated from the front end for the field
    @Expose
    private String elementId; // Name given by the user for the field
    @Expose
    private String metaName;
    @Expose
    private String dictionaryId;
    @Expose
    private String dictionaryName;
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
    private List<Column> columns = new ArrayList<Column>();


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

    public String getElementId() {        return elementId;    }

    public void setElementId(String elementId) {        this.elementId = elementId;    }

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

    public List<Column> getColumns() {        return columns;    }

    public void setColumns(List<Column> columns) {        this.columns = columns;    }

    public RawDataElement getRawData() {
        return rawData;
    }

    public void setRawData(RawDataElement rawData) {
        this.rawData = rawData;
    }

    public String getDictionaryId() {        return dictionaryId;    }

    public void setDictionaryId(String dictionaryId) {        this.dictionaryId = dictionaryId;    }

    public String getDictionaryName() {        return dictionaryName;    }

    public void setDictionaryName(String dictionaryName) {        this.dictionaryName = dictionaryName;    }

}