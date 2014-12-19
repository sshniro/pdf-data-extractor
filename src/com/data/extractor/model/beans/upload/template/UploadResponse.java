package com.data.extractor.model.beans.upload.template;


import com.data.extractor.model.beans.template.info.insert.InsertDataParser;

public class UploadResponse {

    private String  templateEditStatus;
    private String  id;
    private String  parent;
    private String  templateName;
    private String[] imageRelativePaths;
    private InsertDataParser insertDataParser;

    public InsertDataParser getInsertDataParser() {
        return insertDataParser;
    }

    public void setInsertDataParser(InsertDataParser insertDataParser) {
        this.insertDataParser = insertDataParser;
    }

    public String getId() {        return id;    }

    public void setId(String id) {        this.id = id;    }

    public String getParent() {        return parent;    }

    public void setParent(String parent) {        this.parent = parent;    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String[] getImageRelativePaths() {
        return imageRelativePaths;
    }

    public void setImageRelativePaths(String[] imageRelativePaths) {
        this.imageRelativePaths = imageRelativePaths;
    }

    public String getTemplateEditStatus() {        return templateEditStatus;    }

    public void setTemplateEditStatus(String templateEditStatus) {        this.templateEditStatus = templateEditStatus;    }

}
