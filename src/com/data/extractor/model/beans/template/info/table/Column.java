package com.data.extractor.model.beans.template.info.table;

import com.google.gson.annotations.Expose;

import javax.annotation.Generated;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class Column {

    @Expose
    private String metaId;
    @Expose
    private String metaName;
    @Expose
    private Double metaX1;
    @Expose
    private Double metaY1;
    @Expose
    private Double metaWidth;
    @Expose
    private Double metaHeight;
    @Expose
    private String extractedValues;

    private List<Cell> cellList;

    public String getExtractedValues() {
        return extractedValues;
    }

    public void setExtractedValues(String extractedValues) {
        this.extractedValues = extractedValues;
    }

    public String getMetaId() {        return metaId;    }

    public void setMetaId(String metaId) {        this.metaId = metaId;    }

    public String getMetaName() {        return metaName;    }

    public void setMetaName(String metaName) {        this.metaName = metaName;    }

    public Double getMetaX1() {        return metaX1;    }

    public void setMetaX1(Double metaX1) {        this.metaX1 = metaX1;    }

    public Double getMetaY1() {        return metaY1;    }

    public void setMetaY1(Double metaY1) {        this.metaY1 = metaY1;    }

    public Double getMetaWidth() {        return metaWidth;    }

    public void setMetaWidth(Double metaWidth) {        this.metaWidth = metaWidth;    }

    public Double getMetaHeight() {        return metaHeight;    }

    public void setMetaHeight(Double metaHeight) {        this.metaHeight = metaHeight;    }

    public List<Cell> getCellList() {        return cellList;    }

    public void setCellList(List<Cell> cellList) {       this.cellList = cellList;    }
}
