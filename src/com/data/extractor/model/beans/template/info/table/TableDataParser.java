package com.data.extractor.model.beans.template.info.table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class TableDataParser {

    @Expose
    private String id;
    @Expose
    private String templateName;
    @Expose
    private String dataType;
    @Expose
    private String pdfFile;
    @Expose
    private List<TableDataElement> tableDataElements = new ArrayList<TableDataElement>();

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

    public List<TableDataElement> getTableDataElements() {
        return tableDataElements;
    }

    public void setTableDataElements(List<TableDataElement> tableDataElements) {
        this.tableDataElements = tableDataElements;
    }

    public String getPdfFile() { return pdfFile;  }

    public void setPdfFile(String pdfFile) { this.pdfFile = pdfFile;  }

}
