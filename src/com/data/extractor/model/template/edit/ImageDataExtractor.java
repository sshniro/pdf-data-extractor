package com.data.extractor.model.template.edit;

import com.data.extractor.model.beans.manage.categories.Node;
import com.data.extractor.model.beans.markup.template.MarkUpResponse;
import com.data.extractor.model.beans.template.info.image.ImageDataElement;
import com.data.extractor.model.beans.template.info.image.ImageDataParser;
import com.data.extractor.model.beans.upload.template.UploadStatus;
import com.data.extractor.model.data.access.layer.ExtractedFilesDAO;
import com.data.extractor.model.data.access.layer.TemplatesDAO;
import com.data.extractor.model.extractors.image.FullSelectionImageExtractor;
import com.data.extractor.model.template.edit.coordinates.ImageDataCoordinates;
import com.google.gson.Gson;
import com.mongodb.MongoClient;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class ImageDataExtractor {

    public MarkUpResponse extractImage(String jsonRequest,MongoClient mongoClient) throws IOException {
        String imageFile;
        String imageRelativePath;
        String imageWritePath[];
        ExtractedFilesDAO xFilesDAO = new ExtractedFilesDAO(mongoClient);


        MarkUpResponse markUpResponse=new MarkUpResponse();

        Gson gson=new Gson();
        ImageDataParser imageDataParser;
        imageDataParser=gson.fromJson(jsonRequest, ImageDataParser.class);

        List<ImageDataElement> imageDataElements=imageDataParser.getImageDataElements();


        List<UploadStatus> uploadStatusList = xFilesDAO.getRecord(imageDataParser.getId());

        imageDataParser.setPdfFile(uploadStatusList.get(0).getUploadedPdfFile());
        PDDocument doc =PDDocument.load(imageDataParser.getPdfFile());

        /* Get the first textDataElement because only  textDataElement to extract */
        ImageDataElement imageDataElement=imageDataElements.get(0);

        ImageDataCoordinates imageDataCoordinates=new ImageDataCoordinates();
        /* Set Values for the PDF width, Height and Rotation for the first imageDataElement */
        imageDataCoordinates.setPdfProperties(doc, imageDataParser);
        /* Recalculate and set coordinates according to the actual pdf width and height & Page Rotation */
        imageDataCoordinates.calculateCoordinates(imageDataParser);

        imageWritePath=imageDataCoordinates.getPdfWritePath(imageDataParser);
        /*
            set the image write path to
            RootPath/uploads/temp/parentId/id/images/extracts
        */
        imageWritePath[0]=imageWritePath[0]+File.separator + "images" + File.separator + "extracts";

        FullSelectionImageExtractor imageExtractor=new FullSelectionImageExtractor();
        imageFile = imageExtractor.extractImage(imageWritePath[0],doc,imageDataElement);

        /* imageRelativePath = uploads/mainCategory/subCategory/templateName/images/extracts/img.jpg */
        imageRelativePath=getImageRelativePath(imageFile);

        doc.close();

        markUpResponse.setDataType(imageDataParser.getDataType());
        markUpResponse.setExtractedData(imageRelativePath);
        return markUpResponse;
    }

    /*
    Method will split the String when the 'uploads' << word begins
    IMPORTANT  - When deploying the system do not deploy to a folder named uploads
     */
    public String getImageRelativePath(String imageFile){
        String[] splits = imageFile.split("uploads",2);
        return "uploads"+splits[1];
    }
}
