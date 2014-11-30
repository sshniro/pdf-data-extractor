package com.data.extractor.model.manage.categories;


import com.data.extractor.model.beans.manage.categories.ManageCategoriesData;
import com.data.extractor.model.data.access.layer.CounterDAO;
import com.data.extractor.model.data.access.layer.TemplatesDAO;
import com.mongodb.MongoClient;

public class RequestProcessor {

    TemplatesDAO templatesDAO;

    public RequestProcessor(MongoClient mongoClient){
        this.templatesDAO = new TemplatesDAO(mongoClient);
    }

    public ManageCategoriesData processRequest(ManageCategoriesData data,MongoClient mongoClient){

        if(data.getRequest().equals("createNode")){
            CounterDAO counterDAO=new CounterDAO(mongoClient);
            Integer id = counterDAO.getNextId();
            templatesDAO.createNode(id.toString(), data.getParent(), data.getText());
        }

        if (data.getRequest().equals("getAllNodes")){
            data = templatesDAO.getAllNodes(data);

        }
        return data;
    }
}
