package com.data.extractor.model.beans.manage.categories;


import com.data.extractor.model.beans.user.UserBean;

import java.util.List;

public class Node {

    private String id;
    private String parent;
    private String text;
    private String pdfFile;
    private List<UserBean> users;

    public List<UserBean> getUsers() {
        return users;
    }

    public void setUsers(List<UserBean> users) {
        this.users = users;
    }

    public String getId() {        return id;    }

    public String getPdfFile() {        return pdfFile;    }

    public void setPdfFile(String pdfFile) {        this.pdfFile = pdfFile;    }

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
}
