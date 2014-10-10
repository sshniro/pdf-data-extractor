package com.data.extractor.model.beans.manage.categories;


public class ManageCategoriesData {

    private String request;
    private Boolean status;
    private String statusMessage;
    private String mainCategory;
    private String subCategory;
    private String templateName;


    public String getStatusMessage() {        return statusMessage;    }

    public void setStatusMessage(String statusMessage) {        this.statusMessage = statusMessage;    }

    public String getRequest() {        return request;    }

    public void setRequest(String request) {        this.request = request;    }

    public String getMainCategory() {        return mainCategory;    }

    public void setMainCategory(String mainCategory) {        this.mainCategory = mainCategory;    }

    public String getSubCategory() {        return subCategory;    }

    public void setSubCategory(String subCategory) {        this.subCategory = subCategory;    }

    public Boolean getStatus() {        return status;    }

    public void setStatus(Boolean status) {        this.status = status;    }

    public String getTemplateName() {        return templateName;    }

    public void setTemplateName(String templateName) {       this.templateName = templateName;    }



}
