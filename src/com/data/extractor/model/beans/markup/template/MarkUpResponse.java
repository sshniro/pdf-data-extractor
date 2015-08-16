package com.data.extractor.model.beans.markup.template;


import com.data.extractor.model.beans.template.info.pattern.FormPairData;

import java.util.List;

public class MarkUpResponse {

    private String dataType;
    /*  */
    private String extractedData;

    private List<FormPairData> formPairDatas;

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

    public List<FormPairData> getFormPairDatas() {
        return formPairDatas;
    }

    public void setFormPairDatas(List<FormPairData> formPairDatas) {
        this.formPairDatas = formPairDatas;
    }
}
