package com.data.extractor.model.beans.markup.template;

import com.google.gson.annotations.Expose;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class MarkUpRequest {
    @Expose
    private String dataType;
    @Expose
    private String status;

    public String getDataType() {        return dataType;    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
