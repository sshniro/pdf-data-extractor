package com.data.extractor.model.beans.template.info.table;

import com.data.extractor.model.beans.template.info.RawDataElement;
import com.google.gson.annotations.Expose;

import javax.annotation.Generated;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class Column {

    @Expose
    private RawDataElement rawData;
    @Expose
    private String metaId;
    @Expose
    private String metaName;
    @Expose
    private String dictionaryId;
    @Expose
    private String dictionaryName;
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
    @Expose
    private List<Cell> cellList;   // TODO remove this and refactor the code to support the cellValues array list
    @Expose
    private List<String> cellValues;

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

    public RawDataElement getRawData() {
        return rawData;
    }

    public void setRawData(RawDataElement rawData) {
        this.rawData = rawData;
    }

    public List<String> getCellValues() {        return cellValues;    }

    public void setCellValues(List<String> cellValues) {        this.cellValues = cellValues;    }

    public String getDictionaryId() {        return dictionaryId;    }

    public void setDictionaryId(String dictionaryId) {        this.dictionaryId = dictionaryId;    }

    public String getDictionaryName() {        return dictionaryName;    }

    public void setDictionaryName(String dictionaryName) {        this.dictionaryName = dictionaryName;    }


}
