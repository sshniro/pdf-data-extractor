package com.data.extractor.model.beans.form.populate;


public class FormPopulateData {

    private String request;
    private String templates[];
    private String mainCategories[];
    private String subCategories[];
    private String templateNames[];
    private String selectedMC;
    private String selectedSC;

    public String getSelectedMC() {        return selectedMC;    }

    public void setSelectedMC(String selectedCategoryValue) {        this.selectedMC = selectedCategoryValue;    }

    public String getRequest() {        return request;    }

    public void setRequest(String request) {        this.request = request;    }

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

    public String getSelectedSC() {        return selectedSC;    }

    public void setSelectedSC(String selectedSC) {        this.selectedSC = selectedSC;    }

    public String[] getTemplateNames() {        return templateNames;    }

    public void setTemplateNames(String[] templateNames) {        this.templateNames = templateNames;    }



}
