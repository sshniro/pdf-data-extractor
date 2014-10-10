package com.data.extractor.model.manage.categories;


import com.data.extractor.model.beans.manage.categories.ManageCategoriesData;
import com.mongodb.MongoClient;

public class RequestProcessor {

    public ManageCategoriesData processRequest(ManageCategoriesData manageCategoriesData,MongoClient mongoClient){

        if(manageCategoriesData.getRequest().equals("createMainCategory")) {
            CreateRequestProcessor requestProcessor=new CreateRequestProcessor();
            requestProcessor.createMainCat(manageCategoriesData,mongoClient);
        }

        if(manageCategoriesData.getRequest().equals("createSubCategory")) {
            CreateRequestProcessor requestProcessor=new CreateRequestProcessor();
            requestProcessor.createSubCat(manageCategoriesData,mongoClient);
        }

        if(manageCategoriesData.getRequest().equals("deleteTemp")){
            Remover remover=new Remover();
            remover.removeTemplates(manageCategoriesData,mongoClient);
        }

        if(manageCategoriesData.getRequest().equals("deleteSubCat")){
            Remover remover=new Remover();
            remover.removeSubCat(manageCategoriesData , mongoClient);
        }

        if(manageCategoriesData.getRequest().equals("deleteMainCat")){
            Remover remover=new Remover();
            remover.removeMainCat(manageCategoriesData,mongoClient);
        }

        return manageCategoriesData;
    }
}
