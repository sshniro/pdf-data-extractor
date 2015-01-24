package com.data.extractor.model.extract.pdf;

import com.data.extractor.model.beans.extract.pdf.ExtractStatus;
import com.data.extractor.model.beans.template.info.insert.InsertDataParser;
import com.data.extractor.model.data.access.layer.CounterDAO;
import com.data.extractor.model.data.access.layer.TemplatesDAO;
import com.mongodb.MongoClient;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;


public class ExtractRequestProcessor {

    public ExtractStatus processRequest(InsertDataParser insertDataParser,HttpServletRequest request, ExtractStatus extractStatus , MongoClient mongoClient) {

        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        CounterDAO counterDAO = new CounterDAO(mongoClient);

        if (isMultipart) {
            /* Create a factory for disk-based file items */
            FileItemFactory factory = new DiskFileItemFactory();
            /* Create a new file upload handler */
            ServletFileUpload upload = new ServletFileUpload(factory);
            try {
                /* Parse the request */
                List items = upload.parseRequest(request);

                /* assign the form fields Template Name, Category Names to the templateProperties array */
                AssignFormValues formValues = new AssignFormValues();
                formValues.assignTemplateProperties(items, extractStatus);

                DocumentIdAuthenticator authenticator = new DocumentIdAuthenticator();
                authenticator.isDocIdValid(extractStatus,mongoClient);


                if (extractStatus.getStatus()){
                    Integer extractId = counterDAO.getNextExtractId();
                    extractStatus.setId(extractId.toString());
                    PdfFileProcessor fileProcessor = new PdfFileProcessor();
                    extractStatus = fileProcessor.processFile(items, extractStatus , mongoClient);

                    DataElementsProcessor dataElementsProcessor = new DataElementsProcessor(mongoClient);
                    /* Check to ensure the uploaded file is a PDF. Status set in the isPDF Function*/
                    // TODO CHECK WITH ANOTHER VARIABLE RATHER THAN STATUS
                    if (extractStatus.getStatus()) {

                        /* Retrieves DataElements [text,image,table] from templateInfo MongoDB Collection and
                        Insert in to extractedData Mongo DB collection
                        */
                        insertDataParser = dataElementsProcessor.processTextDataElements( insertDataParser , extractStatus, mongoClient);

                        insertDataParser = dataElementsProcessor.processImageDataElements(insertDataParser , extractStatus, mongoClient);

                        insertDataParser = dataElementsProcessor.processTableDataElements(insertDataParser , extractStatus, mongoClient);

                        insertDataParser = dataElementsProcessor.processRegexDataElements(insertDataParser , extractStatus, mongoClient);
                    }
            }

            } catch (FileUploadException e) {
                e.printStackTrace();
                extractStatus.setErrorCause("File Upload Exception Occurred");
            } catch (IOException e) {
                e.printStackTrace();
                extractStatus.setErrorCause("PDF File not Found. Contact ADMIN");
            } catch (Exception e) {
                e.printStackTrace();
                extractStatus.setErrorCause("Exception Occurred. Contact ADMIN");
            }
        }
        return extractStatus;
    }
}
