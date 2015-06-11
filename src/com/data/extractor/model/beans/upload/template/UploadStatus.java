package com.data.extractor.model.beans.upload.template;


import com.data.extractor.model.beans.template.info.insert.InsertDataParser;

public class UploadStatus {

    private Boolean isTemplateNameValid;
    private Boolean pdfUploadStatus;
    private String  templateNameErrorCause;
    private String  pdfUploadErrorCause;
    private String  RootPath;
    private String  templateName;
    private String  docName;
    private String  pdfLocation;
    private String  pdfName;
    private String  uploadedPdfFile;
    private String[] imageRelativePaths;
    private String  parent;
    private String  text;
    private String  type;
    private String  id;
    private String  excelPath;
    private InsertDataParser insertDataParser;

    public InsertDataParser getInsertDataParser() {
        return insertDataParser;
    }

    public void setInsertDataParser(InsertDataParser insertDataParser) {
        this.insertDataParser = insertDataParser;
    }

    public String getId() {        return id;    }

    public void setId(String id) {        this.id = id;    }

    public String getParent() {        return parent;    }

    public void setParent(String parent) {        this.parent = parent;    }

    public String getText() {        return text;    }

    public void setText(String text) {        this.text = text;    }

    public String getType() {        return type;    }

    public void setType(String type) {        this.type = type;    }

    public String getTemplateNameErrorCause() { return templateNameErrorCause; }

    public void setTemplateNameErrorCause(String templateNameErrorCause) {        this.templateNameErrorCause = templateNameErrorCause;  }

    public String getUploadedPdfFile() {  return uploadedPdfFile; }

    public void setUploadedPdfFile(String uploadedPdfFile) {
        this.uploadedPdfFile = uploadedPdfFile;
    }

    public Boolean getPdfUploadStatus() {
        return pdfUploadStatus;
    }

    public void setPdfUploadStatus(Boolean pdfUploadStatus) {
        this.pdfUploadStatus = pdfUploadStatus;
    }

    public String getPdfLocation() {
        return pdfLocation;
    }

    public void setPdfLocation(String pdfLocation) {
        this.pdfLocation = pdfLocation;
    }

    public Boolean getIsTemplateNameValid() { return isTemplateNameValid;    }

    public void setIsTemplateNameValid(Boolean isTemplateNameValid) {
        this.isTemplateNameValid = isTemplateNameValid;
    }

    public String[] getImageRelativePaths() {
        return imageRelativePaths;
    }

    public void setImageRelativePaths(String[] imageRelativePaths) {
        this.imageRelativePaths = imageRelativePaths;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getRootPath() {
        return RootPath;
    }

    public void setRootPath(String rootPath) {
        RootPath = rootPath;
    }

    public String getPdfUploadErrorCause() { return pdfUploadErrorCause; }

    public void setPdfUploadErrorCause(String pdfUploadErrorCause) { this.pdfUploadErrorCause = pdfUploadErrorCause; }

    public String getPdfName() {  return pdfName; }

    public void setPdfName(String pdfName) {  this.pdfName = pdfName; }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getExcelPath() {
        return excelPath;
    }

    public void setExcelPath(String excelPath) {
        this.excelPath = excelPath;
    }
}
