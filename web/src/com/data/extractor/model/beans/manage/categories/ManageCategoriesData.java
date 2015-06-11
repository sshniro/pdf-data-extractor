package com.data.extractor.model.beans.manage.categories;


import java.util.List;

public class ManageCategoriesData {

    private String request;
    private Boolean status;
    private String statusMessage;
    private String templateName;
    private List<String> categories;
    private String id;
    private String parent;
    private String text;
    private List<Node> nodes;

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getCategories() {        return categories;   }

    public void setCategories(List<String> categories) {        this.categories = categories;    }

    public String getStatusMessage() {        return statusMessage;    }

    public void setStatusMessage(String statusMessage) {        this.statusMessage = statusMessage;    }

    public String getRequest() {        return request;    }

    public void setRequest(String request) {        this.request = request;    }

    public Boolean getStatus() {        return status;    }

    public void setStatus(Boolean status) {        this.status = status;    }

    public String getTemplateName() {        return templateName;    }

    public void setTemplateName(String templateName) {       this.templateName = templateName;    }

}
