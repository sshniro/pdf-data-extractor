package com.data.extractor.model.template.upload;

import com.data.extractor.model.beans.upload.template.UploadStatus;
import com.mongodb.MongoClient;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public class UploadRequestProcessor {

    public UploadStatus processRequest(HttpServletRequest request, UploadStatus uploadStatus,MongoClient mongoClient) {

        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        TemplatePropertyAuthenticator authenticator=new TemplatePropertyAuthenticator();

        if (isMultipart) {
            // Create a factory for disk-based file items
            FileItemFactory factory = new DiskFileItemFactory();
            // Create a new file upload handler
            ServletFileUpload upload = new ServletFileUpload(factory);
            try {
                // Parse the request
                List items = upload.parseRequest(request);
                // assign the form fields Template Name, Category Names to the templateProperties array
                AssignFormValues formValues=new AssignFormValues();
                uploadStatus=formValues.assignTemplateProperties(items, uploadStatus);

                // Sets the uploadStatus to true if the templateName is valid
                uploadStatus=authenticator.isNameValid(uploadStatus,mongoClient);

                if (uploadStatus.getIsTemplateNameValid()){
                    PdfFileProcessor fileProcessor=new PdfFileProcessor();
                    fileProcessor.processFile(items,uploadStatus);

                    if(uploadStatus.getPdfUploadStatus()){
                       //Inserts the template Name in to template Collection is pdf to image conversion is success
                       TemplateNameInsert db=new TemplateNameInsert();
                       db.insertTemplateName(uploadStatus,mongoClient);
                       return  uploadStatus;
                    }
                }
            } catch (FileUploadException e) {
                e.printStackTrace();
            }
        }
        return uploadStatus;
    }


}
