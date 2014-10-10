package com.data.extractor.model.manage.categories;


import com.data.extractor.model.beans.manage.categories.ManageCategoriesData;
import com.data.extractor.model.data.access.layer.TemplateInfoDAO;
import com.data.extractor.model.data.access.layer.TemplatesDAO;
import com.mongodb.MongoClient;

public class Remover {

    public ManageCategoriesData removeMainCat(ManageCategoriesData data,MongoClient mongoClient) {

        TemplatesDAO templatesDAO=new TemplatesDAO(mongoClient);
        TemplateInfoDAO templateInfoDAO=new TemplateInfoDAO(mongoClient);

        templateInfoDAO.removeTemplateInfo(data.getMainCategory());
        templatesDAO.removeTemplate(data.getMainCategory());

        data.setStatus(true);
        data.setStatusMessage(data.getMainCategory() + " Successfully Removed");

        return data;
    }

    public ManageCategoriesData removeSubCat(ManageCategoriesData data,MongoClient mongoClient) {

        TemplatesDAO templatesDAO=new TemplatesDAO(mongoClient);
        TemplateInfoDAO templateInfoDAO=new TemplateInfoDAO(mongoClient);

        templatesDAO.removeTemplate(data.getMainCategory(),data.getSubCategory());
        templateInfoDAO.removeTemplateInfo(data.getMainCategory(),data.getSubCategory());

        data.setStatus(true);
        data.setStatusMessage(data.getSubCategory() + " Successfully Removed");


        return data;
    }

    public ManageCategoriesData removeTemplates(ManageCategoriesData data,MongoClient mongoClient){

        TemplatesDAO templatesDAO=new TemplatesDAO(mongoClient);;
        TemplateInfoDAO templateInfoDAO=new TemplateInfoDAO(mongoClient);

        templatesDAO.removeTemplate(data.getMainCategory(),data.getSubCategory(),data.getTemplateName());
        templateInfoDAO.removeTemplateInfo(data.getMainCategory(),data.getSubCategory(),data.getTemplateName());

        data.setStatus(true);
        data.setStatusMessage(data.getTemplateName() + " successfully removed" );

        return data;
    }
}
