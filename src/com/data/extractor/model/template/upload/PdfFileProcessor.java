package com.data.extractor.model.template.upload;

import com.data.extractor.model.beans.upload.template.UploadStatus;
import com.data.extractor.model.convert.PdfToImage;
import org.apache.commons.fileupload.FileItem;

import java.io.File;
import java.util.Iterator;
import java.util.List;

public class PdfFileProcessor {

    public UploadStatus processFile(List items,UploadStatus uploadStatus){

        // Parse the request
        Iterator pdfIterator = items.iterator();

        while (pdfIterator.hasNext()) {
            FileItem pdfItem = (FileItem) pdfIterator.next();
            //check whether the Form Field is a File
            if (!pdfItem.isFormField()) {
                if (pdfItem.getFieldName().equals("pdfFile"))
                    uploadPdf(uploadStatus, pdfItem);
            }
        }

        return uploadStatus;
    }

    public UploadStatus uploadPdf(UploadStatus uploadStatus, FileItem pdfItem ){

        String rootPath = uploadStatus.getRootPath();

        /*
        spaceReplaced[0]=mainCategory,spaceReplaced[1]=subCategory,spaceReplaced[2]=templateName
         */
        String[] spaceReplaced=replaceSpace(uploadStatus);
        File uploadLocation = new File(rootPath + File.separator + "uploads"+File.separator+
                                    spaceReplaced[0]+File.separator+spaceReplaced[1]);
        if (!uploadLocation.exists()) {
            boolean status = uploadLocation.mkdirs();
        }

        File uploadedFile = new File(uploadLocation + File.separator + spaceReplaced[2] +".pdf");

        uploadStatus.setPdfLocation(uploadLocation.getAbsolutePath());
        uploadStatus.setPdfName(spaceReplaced[2]); // templateName space replaced with "_"
        uploadStatus.setUploadedPdfFile(uploadStatus.getPdfLocation()+File.separator+uploadStatus.getPdfName()+".pdf");

        try {
            pdfItem.write(uploadedFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
        Checks if the uploaded file is PDF if Not remove the file and set the Error Message
         */
        UploadFileType fileType=new UploadFileType();
        fileType.isPDF(uploadStatus);
        /*
        If the file is PDF convert PDF to Image and set the imageRelativePaths in the uploadStatus
         */
        if (uploadStatus.getPdfUploadStatus()) {
            PdfToImage pdfToImage=new PdfToImage();
            pdfToImage.convertToImage(uploadStatus);
        }
    return uploadStatus;
    }

    public String[] replaceSpace(UploadStatus uploadStatus){

        String[] spaceReplacedProperties=new String[3];
        spaceReplacedProperties[0]=uploadStatus.getMainCategory().replaceAll("\\s+","_");
        spaceReplacedProperties[1]=uploadStatus.getSubCategory().replaceAll("\\s+","_");
        spaceReplacedProperties[2]=uploadStatus.getTemplateName().replaceAll("\\s+","_");
        return spaceReplacedProperties;
    }
}
