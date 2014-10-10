package com.data.extractor.model.template.upload;

import com.data.extractor.model.beans.upload.template.UploadStatus;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;


public class UploadFileType {

    public UploadStatus isPDF(UploadStatus uploadStatus) {
        try {
            PDDocument doc=PDDocument.load(uploadStatus.getUploadedPdfFile());
            uploadStatus.setPdfUploadStatus(true);
            doc.close();

        } catch (IOException e) {
            deletePdf(uploadStatus.getPdfLocation());
            uploadStatus.setPdfUploadStatus(false);
            uploadStatus.setPdfUploadErrorCause("Upload a PDF File");
        }
        return uploadStatus;
    }

    public static void deletePdf(String pdfLocation){
        boolean fileExist;
        File file=new File(pdfLocation);
        fileExist=file.exists();
        if(fileExist){
            file.delete();
        }
    }
}
