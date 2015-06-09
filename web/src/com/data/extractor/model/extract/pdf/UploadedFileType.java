package com.data.extractor.model.extract.pdf;

import com.data.extractor.model.beans.extract.pdf.ExtractStatus;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;


public class UploadedFileType {

    public ExtractStatus isPDF(ExtractStatus extractStatus) {
        try {
            PDDocument doc=PDDocument.load(extractStatus.getUploadedPdfFile());
            extractStatus.setPdfUploadStatus(true);
            doc.close();

        } catch (IOException e) {
            deletePdf(extractStatus.getPdfLocation());
            extractStatus.setStatus(false);
            extractStatus.setErrorCause("Upload a PDF File");
        }
        return extractStatus;
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
