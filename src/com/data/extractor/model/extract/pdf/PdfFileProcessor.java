package com.data.extractor.model.extract.pdf;



import com.data.extractor.model.beans.extract.pdf.ExtractStatus;
import org.apache.commons.fileupload.FileItem;

import java.io.File;
import java.util.Iterator;
import java.util.List;

public class PdfFileProcessor {

    public ExtractStatus processFile(List items,ExtractStatus extractStatus) throws Exception {

        // Parse the request
        Iterator pdfIterator = items.iterator();

        while (pdfIterator.hasNext()) {
            FileItem pdfItem = (FileItem) pdfIterator.next();
            //check whether the Form Field is a File
            if (!pdfItem.isFormField()) {
                if (pdfItem.getFieldName().equals("pdfFile"))
                    uploadPdf(extractStatus, pdfItem);
            }
        }
        return extractStatus;
    }

    public ExtractStatus uploadPdf(ExtractStatus extractStatus, FileItem pdfItem ) throws Exception {

        String rootPath = extractStatus.getRootPath();

        /*
        spaceReplaced[0]=mainCategory,spaceReplaced[1]=subCategory,spaceReplaced[2]=templateName
         */
        //String[] spaceReplaced=replaceSpace(extractStatus);
        File uploadLocation = new File(rootPath + File.separator + "uploads"+File.separator+"temp");
        if (!uploadLocation.exists()) {
            boolean status = uploadLocation.mkdirs();
        }

        // Concatenate TemplateName with Document Name
        File uploadedFile = new File(uploadLocation + File.separator + extractStatus.getParent() + extractStatus.getId() +".pdf");

        extractStatus.setPdfLocation(uploadLocation.getAbsolutePath());
        extractStatus.setPdfName(extractStatus.getParent() + extractStatus.getId()); // templateName space replaced with "_"
        extractStatus.setUploadedPdfFile(extractStatus.getPdfLocation()+File.separator+extractStatus.getPdfName()+".pdf");

        pdfItem.write(uploadedFile);

        /*  Checks if the uploaded file is PDF if Not remove the file and set the Error Message */
        UploadedFileType fileType=new UploadedFileType();
        fileType.isPDF(extractStatus);

        return extractStatus;
    }

    public String[] replaceSpace(ExtractStatus extractStatus){

        String[] spaceReplacedProperties=new String[4];
        spaceReplacedProperties[0]=extractStatus.getTemplateName().replaceAll("\\s+","_");
        spaceReplacedProperties[1]=extractStatus.getDocumentId().replaceAll("\\s+","_");
        spaceReplacedProperties[2]=extractStatus.getMainCategory().replaceAll("\\s+","_");
        spaceReplacedProperties[3]=extractStatus.getSubCategory().replaceAll("\\s+","_");
        return spaceReplacedProperties;
    }
}
