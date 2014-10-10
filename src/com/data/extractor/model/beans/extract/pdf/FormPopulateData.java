package com.data.extractor.model.beans.extract.pdf;


public class FormPopulateData {
    String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    String mainCategories[];
    String subCategories[];

    public String[] getTemplates() {
        return templates;
    }

    public void setTemplates(String[] templates) {
        this.templates = templates;
    }

    public String[] getMainCategories() {
        return mainCategories;
    }

    public void setMainCategories(String[] mainCategories) {
        this.mainCategories = mainCategories;
    }

    public String[] getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(String[] subCategories) {
        this.subCategories = subCategories;
    }

    String templates[];

}
