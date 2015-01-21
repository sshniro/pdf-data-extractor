package com.data.extractor.model.beans.template.info.pattern;

import com.data.extractor.model.beans.template.info.table.Cell;

import java.util.ArrayList;
import java.util.List;

public class ColumnDataBean {
    private String columnStartTag;
    private String columnEndTag;
    private String columnName;
    private List<Cell> cellList = new ArrayList<Cell>();

    public List<Cell> getCellList() {
        return cellList;
    }

    public void setCellList(List<Cell> cellList) {
        this.cellList = cellList;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnStartTag() {
        return columnStartTag;
    }

    public void setColumnStartTag(String columnStartTag) {
        this.columnStartTag = columnStartTag;
    }

    public String getColumnEndTag() {
        return columnEndTag;
    }

    public void setColumnEndTag(String columnEndTag) {
        this.columnEndTag = columnEndTag;
    }
}
