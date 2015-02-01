package com.data.extractor.model.beans.manage.categories;


import com.data.extractor.model.beans.user.UserBean;

import java.util.List;

public class Node {

    private String id;
    private String parent;
    private String text;
    private String pdfFile;
    private List<String> userIdList;

    public List<String> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<String> userIdList) {
        this.userIdList = userIdList;
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
