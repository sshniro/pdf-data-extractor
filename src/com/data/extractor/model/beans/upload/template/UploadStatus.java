package com.data.extractor.model.beans.upload.template;


public class UploadStatus {

    private Boolean isTemplateNameValid;
    private Boolean pdfUploadStatus;
    private String  templateNameErrorCause;
    private String pdfUploadErrorCause;
    private String  RootPath;
    private String  mainCategory;
    private String  subCategory;
    private String  templateName;
    private String  pdfLocation;
    private String  pdfName;
    private String  uploadedPdfFile;
    private String[] imageRelativePaths;

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

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getMainCategory() {
        return mainCategory;
    }

    public void setMainCategory(String mainCategory) {
        this.mainCategory = mainCategory;
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

}
