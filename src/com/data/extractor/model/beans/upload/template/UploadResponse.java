package com.data.extractor.model.beans.upload.template;


public class UploadResponse {

    private String  id;
    private String  mainCategory;
    private String  subCategory;
    private String  templateName;
    private String[] imageRelativePaths;


    public String getId() {        return id;    }

    public void setId(String id) {        this.id = id;    }

    public String getMainCategory() {
        return mainCategory;
    }

    public void setMainCategory(String mainCategory) {
        this.mainCategory = mainCategory;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

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

}
