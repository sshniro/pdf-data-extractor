package com.data.extractor.model.extract.pdf;


import com.data.extractor.model.beans.extract.pdf.ExtractResponse;
import com.data.extractor.model.beans.extract.pdf.ExtractStatus;

public class ResponseGenerator {
    public ExtractResponse generateResponse(ExtractStatus extractStatus , String extractedData){
        ExtractResponse extractResponse=new ExtractResponse();
        extractResponse.setStatus(extractStatus.getStatus());

        if(extractResponse.isStatus()) {
            extractResponse.setExtractedData(extractedData);
            extractResponse.setId(extractStatus.getId());
            extractResponse.setParent(extractStatus.getParent());
            extractResponse.setDocumentId(extractStatus.getDocumentId());
        }else
            extractResponse.setErrorCause(extractStatus.getErrorCause());
        return extractResponse;

    }
}
