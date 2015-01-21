package com.data.extractor.controllers;

import com.data.extractor.model.beans.extract.pdf.ExtractResponse;
import com.data.extractor.model.beans.extract.pdf.ExtractStatus;
import com.data.extractor.model.beans.template.info.pattern.HeaderDataBean;
import com.data.extractor.model.beans.template.info.pattern.PatternDataParser;
import com.data.extractor.model.beans.upload.template.UploadStatus;
import com.data.extractor.model.data.access.layer.CounterDAO;
import com.google.gson.Gson;
import com.mongodb.MongoClient;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class PatternControllerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



        boolean isMultipart = ServletFileUpload.isMultipartContent(request);

        /* Get the mongo client from the servletContext */
        MongoClient mongoClient = (MongoClient) request.getServletContext().getAttribute("MONGO_CLIENT");
        CounterDAO counterDAO = new CounterDAO(mongoClient);
        Gson gson=new Gson();
        ExtractStatus extractStatus=new ExtractStatus();
        extractStatus.setRootPath(getServletContext().getRealPath(File.separator));

        if (isMultipart) {
            /* Create a factory for disk-based file items */
            FileItemFactory factory = new DiskFileItemFactory();
            /* Create a new file upload handler */
            ServletFileUpload upload = new ServletFileUpload(factory);
            try {
                /* Parse the request */
                List items = upload.parseRequest(request);

                Integer extractId = counterDAO.getNextExtractId();
                extractStatus.setId(extractId.toString());
                extractStatus.setParent("test");

                com.data.extractor.model.extract.pdf.PdfFileProcessor fileProcessor = new com.data.extractor.model.extract.pdf.PdfFileProcessor();
                extractStatus = fileProcessor.processFile(items, extractStatus , mongoClient);

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

            response.getWriter().print(gson.toJson(extractStatus));
        }

        if(!isMultipart){
            StringBuilder sbRequest = new StringBuilder();
            String s;
            while ((s = request.getReader().readLine()) != null) {
                sbRequest.append(s);
            }

            PatternDataParser patternDataElement = gson.fromJson(sbRequest.toString(),PatternDataParser.class);
            UploadStatus uploadStatus=new UploadStatus();
            uploadStatus.setId("12");

            StringBuilder sb=pdftoText("/Users/niro273/Desktop/test.pdf");
            StringBuilder extractedData=new StringBuilder("");
            String unprocessed = sb.toString();
            String processing = null;
            String[] splits = null;

            for (HeaderDataBean h : patternDataElement.getHeaderDataBeanList()){
                try {
                    splits = unprocessed.split(h.getStartTag(), 2);
                    processing =splits[1];
                    splits = processing.split(h.getEndTag(),2);
                    h.setValue(splits[0]);
                    extractedData.append("Text").append(" -(").append(h.getHeaderName()).append(") : ");
                    extractedData.append(replaceNextLineChar(h.getValue()));
                    extractedData.append("\n");
                    //System.out.println(h.getValue());
                }catch (ArrayIndexOutOfBoundsException e){
                    e.printStackTrace();
                }
            }

            ExtractResponse extractResponse = new ExtractResponse();
            extractResponse.setExtractedData(extractedData.toString());
            response.getWriter().print(gson.toJson(extractResponse));
        }

    }
    // Extract text from PDF Document
    static StringBuilder pdftoText(String fileName) {
        PDFParser parser;
        String parsedText = null;;
        PDFTextStripper pdfStripper = null;
        PDDocument doc = null;
        COSDocument cosDoc = null;
        StringBuilder sb = new StringBuilder("");
        File file = new File(fileName);
        if (!file.isFile()) {
            System.err.println("File " + fileName + " does not exist.");
            return null;
        }
        try {
            parser = new PDFParser(new FileInputStream(file));
        } catch (IOException e) {
            System.err.println("Unable to open PDF Parser. " + e.getMessage());
            return null;
        }
        try {
            parser.parse();
            cosDoc = parser.getDocument();
            pdfStripper = new PDFTextStripper();
            doc = new PDDocument(cosDoc);
            int pageNumber = doc.getNumberOfPages();
            pdfStripper.setStartPage(1);
            pdfStripper.setEndPage(pageNumber);
            //parsedText = pdfStripper.getText(doc);
            sb.append(pdfStripper.getText(doc));
            //System.out.println(doc.getNumberOfPages());
        } catch (Exception e) {
            System.err
                    .println("An exception occured in parsing the PDF Document."
                            + e.getMessage());
        } finally {
            try {
                if (cosDoc != null)
                    cosDoc.close();
                if (doc != null)
                    doc.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sb;
    }

    /* Removes the next line characters if the string has it in front of the line
* [Because when extracting text with meta area , area which is empty adds a next line character
* instead of adding a blank]
* */
    public String replaceNextLineChar(String data){

        if(data.charAt(0) != 10){
            return data;
        }else {
            StringBuilder sb=new StringBuilder(data);
            sb.deleteCharAt(0);
            return replaceNextLineChar(sb.toString());
        }
    }
}
