package com.data.extractor.model.template.upload;

import com.data.extractor.model.beans.manage.categories.Node;
import com.data.extractor.model.beans.upload.template.UploadStatus;
import com.data.extractor.model.data.access.layer.CounterDAO;
import com.data.extractor.model.data.access.layer.TemplatesDAO;
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
        TemplatesDAO templatesDAO = new TemplatesDAO(mongoClient);
        CounterDAO counterDAO = new CounterDAO(mongoClient);

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
                List<Node> nodeList =templatesDAO.getNodes(uploadStatus.getParent());

                /* If there is a Record Present parse the MongoObject returned */
                for (Node n : nodeList){
                    if(n.getText().equals(uploadStatus.getText())){
                        uploadStatus.setIsTemplateNameValid(false);
                        uploadStatus.setTemplateNameErrorCause("Template Name Already Taken");
                    }
                }
                uploadStatus.setIsTemplateNameValid(true);

                /* Assign an ID for the template Node */
                Integer id = counterDAO.getNextId();
                uploadStatus.setId(id.toString());

                if (uploadStatus.getIsTemplateNameValid()){
                    PdfFileProcessor fileProcessor=new PdfFileProcessor();
                    fileProcessor.processFile(items,uploadStatus);

                    if(uploadStatus.getPdfUploadStatus()){
                        templatesDAO.createLeafNode(uploadStatus.getId(),uploadStatus.getParent(),uploadStatus.getText(),uploadStatus.getUploadedPdfFile());
                        return uploadStatus;
                    }
                }
            } catch (FileUploadException e) {
                e.printStackTrace();
            }
        }
        return uploadStatus;
    }


}
