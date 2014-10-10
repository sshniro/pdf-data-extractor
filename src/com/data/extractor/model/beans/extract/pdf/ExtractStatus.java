package com.data.extractor.model.beans.extract.pdf;


public class ExtractStatus {

    private String  RootPath;
    private String  mainCategory;
    private String  subCategory;
    private String  templateName;
    private String  pdfLocation;
    private String  pdfName;
    private String  uploadedPdfFile;
    private String  documentId;
    private String  errorCause;



    private Boolean  status;
    private Boolean isDocumentIdValid;

    public Boolean getIsDocumentIdValid() {
        return isDocumentIdValid;
    }

    public void setIsDocumentIdValid(Boolean isDocumentIdValid) {
        this.isDocumentIdValid = isDocumentIdValid;
    }

    public String getDocumentIdCause() {
        return documentIdCause;
    }

    public void setDocumentIdCause(String documentIdCause) {
        this.documentIdCause = documentIdCause;
    }

    private String  documentIdCause;

    public String getPdfUploadErrorCause() {
        return pdfUploadErrorCause;
    }

    public void setPdfUploadErrorCause(String pdfUploadErrorCause) {
        this.pdfUploadErrorCause = pdfUploadErrorCause;
    }

    private String pdfUploadErrorCause;

    public Boolean getPdfUploadStatus() {
        return pdfUploadStatus;
    }

    public void setPdfUploadStatus(Boolean pdfUploadStatus) {
        this.pdfUploadStatus = pdfUploadStatus;
    }

    private Boolean pdfUploadStatus;

    public String getRootPath() {
        return RootPath;
    }

    public void setRootPath(String rootPath) {
        RootPath = rootPath;
    }

    public String getMainCategory() {
        return mainCategory;
    }

    public void setMainCategory(String mainCategory) {
        this.mainCategory = mainCategory;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getPdfLocation() {
        return pdfLocation;
    }

    public void setPdfLocation(String pdfLocation) {
        this.pdfLocation = pdfLocation;
    }

    public String getPdfName() {
        return pdfName;
    }

    public void setPdfName(String pdfName) {
        this.pdfName = pdfName;
    }

    public String getUploadedPdfFile() {
        return uploadedPdfFile;
    }

    public void setUploadedPdfFile(String uploadedPdfFile) {
        this.uploadedPdfFile = uploadedPdfFile;
    }

    public String getDocumentId() {        return documentId;    }

    public void setDocumentId(String documentId) {        this.documentId = documentId;    }

    public String getErrorCause() {        return errorCause;    }

    public void setErrorCause(String errorCause) {        this.errorCause = errorCause;    }

    public Boolean getStatus() {        return status;    }

    public void setStatus(Boolean status) {        this.status = status;    }



}
