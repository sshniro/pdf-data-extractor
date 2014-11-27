package com.data.extractor.model.beans.manage.categories;


public class Node {

    private String id;
    private String parent;
    private String text;
    private String pdfFile;

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
