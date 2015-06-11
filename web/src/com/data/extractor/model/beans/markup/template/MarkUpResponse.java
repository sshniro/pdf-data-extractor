package com.data.extractor.model.beans.markup.template;


public class MarkUpResponse {

    private String dataType;
    /*  */
    private String extractedData;

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getExtractedData() {
        return extractedData;
    }

    public void setExtractedData(String extractedData) {
        this.extractedData = extractedData;
    }
}
