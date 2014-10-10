package com.data.extractor.model.manage.categories;


import com.data.extractor.model.beans.manage.categories.ManageCategoriesData;
import com.data.extractor.model.beans.templates.TemplatesParser;
import com.data.extractor.model.data.access.layer.TemplatesDAO;
import com.mongodb.MongoClient;

import java.util.List;

public class CreateRequestProcessor {

    public ManageCategoriesData createMainCat(ManageCategoriesData data,MongoClient mongoClient){

        TemplatesDAO templatesDAO=new TemplatesDAO(mongoClient);
        List<TemplatesParser> templatesParserList;
        templatesParserList=templatesDAO.getTemplates(data.getMainCategory());

        /* If no records exists in the templates collection create a new record*/
        if (templatesParserList.size()==0) {
            /* If no records exists in the templates collection create a new record*/
            templatesDAO.createTemplate(data.getMainCategory());
            data.setStatus(true);
            data.setStatusMessage(data.getMainCategory()+ " successfully created");
        }else{
            data.setStatus(false);
            data.setStatusMessage(data.getMainCategory()+ " already exists");
        }

        return data;
    }

    public ManageCategoriesData createSubCat(ManageCategoriesData data,MongoClient mongoClient){

        TemplatesDAO templatesDAO=new TemplatesDAO(mongoClient);

        List<TemplatesParser> templates=templatesDAO.getTemplates(data.getMainCategory(), data.getSubCategory());

        if (templates.size()==0) {
            /* If no records exists in the templates collection create a new record*/
            templatesDAO.updateTemplate(data.getMainCategory(),data.getSubCategory());
            data.setStatus(true);
            data.setStatusMessage(data.getSubCategory()+ " successfully created");

        }else{
            data.setStatus(false);
            data.setStatusMessage(data.getSubCategory() + " already exists");
        }

        return data;
    }
}
