package com.data.extractor.model.beans.dictionary;

public class Dictionary {

    private int id;
    private String name;
    private String dataType;
    private String description;
    private int length;
    private String defaultValues;
    private String allowedValues;
    private String request;

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public int getId() {        return id;    }

    public void setId(int id) {        this.id = id;    }

    public String getAllowedValues() {
        return allowedValues;
    }

    public void setAllowedValues(String allowedValues) {
        this.allowedValues = allowedValues;
    }

    public String getDefaultValues() {
        return defaultValues;
    }

    public void setDefaultValues(String defaultValues) {
        this.defaultValues = defaultValues;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
