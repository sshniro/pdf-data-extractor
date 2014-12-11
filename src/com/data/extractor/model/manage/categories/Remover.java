package com.data.extractor.model.manage.categories;


import com.data.extractor.model.beans.manage.categories.ManageCategoriesData;
import com.data.extractor.model.data.access.layer.TemplateInfoDAO;
import com.data.extractor.model.data.access.layer.TemplatesDAO;
import com.mongodb.MongoClient;

public class Remover {

    public ManageCategoriesData removeMainCat(ManageCategoriesData data,MongoClient mongoClient) {


        return data;
    }

    public ManageCategoriesData removeSubCat(ManageCategoriesData data,MongoClient mongoClient) {


        return data;
    }

    public ManageCategoriesData removeTemplates(ManageCategoriesData data,MongoClient mongoClient){

        TemplatesDAO templatesDAO=new TemplatesDAO(mongoClient);;
        TemplateInfoDAO templateInfoDAO=new TemplateInfoDAO(mongoClient);

        data.setStatus(true);
        data.setStatusMessage(data.getTemplateName() + " successfully removed" );

        return data;
    }
}
