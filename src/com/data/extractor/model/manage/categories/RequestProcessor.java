package com.data.extractor.model.manage.categories;


import com.data.extractor.model.beans.manage.categories.ManageCategoriesData;
import com.data.extractor.model.beans.manage.categories.Node;
import com.data.extractor.model.beans.user.UserBean;
import com.data.extractor.model.data.access.layer.CounterDAO;
import com.data.extractor.model.data.access.layer.TemplatesDAO;
import com.data.extractor.model.data.access.layer.UsersDAO;
import com.mongodb.MongoClient;

import java.util.ArrayList;
import java.util.List;

public class RequestProcessor {

    TemplatesDAO templatesDAO;

    public RequestProcessor(MongoClient mongoClient){
        this.templatesDAO = new TemplatesDAO(mongoClient);
    }

    public ManageCategoriesData processRequest(ManageCategoriesData data,MongoClient mongoClient){

        UsersDAO usersDAO = new UsersDAO(mongoClient);

        if(data.getRequest().equals("createNode")){
            CounterDAO counterDAO=new CounterDAO(mongoClient);
            Integer id = counterDAO.getNextId();
            templatesDAO.createNode(id.toString(), data.getParent(), data.getText());
        }

        if (data.getRequest().equals("getAllNodes")){
            /*Sets the NodesList*/
            data = templatesDAO.getAllNodes(data);

        }

        if(data.getRequest().equals("getUserNodes")){
            UserBean userBean = usersDAO.getUser(data.getUserId());
            List<String> nodeIdList = userBean.getNodes();
            List<Node> nodeList= new ArrayList<Node>();

            for (String nodeId  : nodeIdList){
                nodeList.add(templatesDAO.getNode(nodeId));
            }
            data.setNodes(nodeList);
        }
        return data;
    }
}
