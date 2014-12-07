package com.data.extractor.model.beans.template.info;

import com.data.extractor.model.beans.template.info.text.*;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class RawDataElement {

    @Expose
    private Double baseUiComponentStartY;
    @Expose
    private Double baseUiComponentHeight;
    @Expose
    private Double baseUiComponentWidth;
    @Expose
    private Double startX;
    @Expose
    private Double startY;
    @Expose
    private Double width;
    @Expose
    private Double height;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Expose
    private String id;
    @Expose
    private String elementType;

    @Expose
    private Double baseUiComponentStartX;

    public String getElementType() {
        return elementType;
    }

    public void setElementType(String elementType) {
        this.elementType = elementType;
    }

    public Double getBaseUiComponentStartX() {
        return baseUiComponentStartX;
    }

    public void setBaseUiComponentStartX(Double baseUiComponentStartX) {
        this.baseUiComponentStartX = baseUiComponentStartX;
    }

    public Double getBaseUiComponentStartY() {
        return baseUiComponentStartY;
    }

    public void setBaseUiComponentStartY(Double baseUiComponentStartY) {
        this.baseUiComponentStartY = baseUiComponentStartY;
    }

    public Double getBaseUiComponentHeight() {
        return baseUiComponentHeight;
    }

    public void setBaseUiComponentHeight(Double baseUiComponentHeight) {
        this.baseUiComponentHeight = baseUiComponentHeight;
    }

    public Double getBaseUiComponentWidth() {
        return baseUiComponentWidth;
    }

    public void setBaseUiComponentWidth(Double baseUiComponentWidth) {
        this.baseUiComponentWidth = baseUiComponentWidth;
    }

    public Double getStartX() {
        return startX;
    }

    public void setStartX(Double startX) {
        this.startX = startX;
    }

    public Double getStartY() {
        return startY;
    }

    public void setStartY(Double startY) {
        this.startY = startY;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }



}
