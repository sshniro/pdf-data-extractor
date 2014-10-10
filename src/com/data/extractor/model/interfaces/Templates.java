package com.data.extractor.model.interfaces;

import com.data.extractor.model.beans.templates.TemplatesParser;

import java.util.List;

public interface Templates {

    public  void createTemplate(String mainCategory);
    public void updateTemplate(String mainCategory,String subCategory);
    public List<TemplatesParser> getTemplates(String mainCategory);
    public List<TemplatesParser> getTemplates(String mainCategory,String subCategory);
    public  void  removeTemplate(String mainCategory);
    public  void  removeTemplate(String mainCategory,String subCategory);
    public  void  removeTemplate(String mainCategory,String subCategory,String templateName);

}
