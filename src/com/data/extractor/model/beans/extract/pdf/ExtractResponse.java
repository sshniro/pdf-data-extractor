package com.data.extractor.model.beans.extract.pdf;

public class ExtractResponse {
    private boolean status;
    private String errorCause;
    private String successMsg;
    private String extractedData;

    public String getSuccessMsg() {
        return successMsg;
    }

    public void setSuccessMsg(String successMsg) {
        this.successMsg = successMsg;
    }

    public String getErrorCause() {
        return errorCause;
    }

    public void setErrorCause(String errorCause) {
        this.errorCause = errorCause;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getExtractedData() {        return extractedData;    }

    public void setExtractedData(String extractedData) {        this.extractedData = extractedData;    }



}
