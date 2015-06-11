package com.data.extractor.model.beans.template.info.pattern;

import com.data.extractor.model.beans.template.info.table.Cell;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class ColumnDataElement {

    @Expose
    private String metaName;
    @Expose
    private ColumnStartElement columnStartElement;
    @Expose
    private ColumnEndElement columnEndElement;
    @Expose
    private List<Cell> cellList = new ArrayList<Cell>();
    @Expose
    private List<String> cellValues = new ArrayList<String>();

    public String getMetaName() {
        return metaName;
    }

    public void setMetaName(String metaName) {
        this.metaName = metaName;
    }

    public ColumnStartElement getColumnStartElement() {
        return columnStartElement;
    }

    public void setColumnStartElement(ColumnStartElement columnStartElement) {
        this.columnStartElement = columnStartElement;
    }

    public ColumnEndElement getColumnEndElement() {
        return columnEndElement;
    }

    public void setColumnEndElement(ColumnEndElement columnEndElement) {
        this.columnEndElement = columnEndElement;
    }

    public List<Cell> getCellList() {
        return cellList;
    }

    public void setCellList(List<Cell> cellList) {
        this.cellList = cellList;
    }

    public List<String> getCellValues() {
        return cellValues;
    }

    public void setCellValues(List<String> cellValues) {
        this.cellValues = cellValues;
    }
}
