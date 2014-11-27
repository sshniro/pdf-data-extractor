package com.data.extractor.model.extract.pdf;


import com.data.extractor.model.beans.extract.pdf.ExtractStatus;
import com.data.extractor.model.beans.template.info.image.ImageDataElement;
import com.data.extractor.model.beans.template.info.image.ImageDataParser;
import com.data.extractor.model.beans.template.info.insert.InsertDataParser;
import com.data.extractor.model.beans.template.info.table.TableDataParser;
import com.data.extractor.model.beans.template.info.text.TextDataElement;
import com.data.extractor.model.beans.template.info.text.TextDataParser;
import com.data.extractor.model.data.access.layer.TemplateInfoDAO;
import com.data.extractor.model.extract.pdf.inserter.ExtractedImageInserter;
import com.data.extractor.model.extract.pdf.inserter.ExtractedTableInserter;
import com.data.extractor.model.extract.pdf.inserter.ExtractedTextInserter;
import com.data.extractor.model.extract.pdf.table.DataProcessor;
import com.data.extractor.model.extractors.image.FullSelectionImageExtractor;
import com.data.extractor.model.extractors.text.FullSelectionTextExtractor;
import com.data.extractor.model.extractors.text.MetaSelectionTextExtractor;
import com.data.extractor.model.template.markup.calculate.coordinates.ImageDataCoordinates;
import com.data.extractor.model.template.markup.pdf.retreiver.ImagePDDocument;
import com.mongodb.MongoClient;
import org.apache.pdfbox.exceptions.CryptographyException;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class DataElementsProcessor {

    private TemplateInfoDAO templateInfoDAO;

    public DataElementsProcessor(MongoClient mongoClient){
        this.templateInfoDAO= new TemplateInfoDAO(mongoClient);
    }

    public InsertDataParser processTextDataElements(InsertDataParser totalExtractedData , ExtractStatus extractStatus,MongoClient mongoClient) throws IOException {

        List<TextDataParser> textDataList;
        TextDataParser textDataParser=null;
        textDataList = templateInfoDAO.getTextTemplateInfo(extractStatus.getMainCategory(), extractStatus.getSubCategory() ,
                                                            extractStatus.getTemplateName(), "text" );

        /* Check if no text data available to extract then skip*/
        if(textDataList.size() !=0 ){
            /*  Only one record will exist to the text data for a given PDF */
            textDataParser =textDataList.get(0);
            List<TextDataElement> textDataElements = textDataParser.getTextDataElements();

            PDDocument doc = PDDocument.load(extractStatus.getUploadedPdfFile());
            String extractedText;

            for (TextDataElement t : textDataElements) {
                if (t.getMetaX1() == -1) {
                    FullSelectionTextExtractor fullExtractor = new FullSelectionTextExtractor();
                    extractedText = fullExtractor.extract(doc, t);
                } else {
                    MetaSelectionTextExtractor metaExtractor = new MetaSelectionTextExtractor();
                    extractedText = metaExtractor.extractWithOutLabel(doc, t);
                }
                t.setExtractedText(extractedText);
            }

            ExtractedTextInserter textInserter = new ExtractedTextInserter();
            textInserter.insert(textDataParser, extractStatus ,mongoClient);

        }
        /*  to present it to the extracted HTML   */
        totalExtractedData.setTextDataParser(textDataParser);
        return totalExtractedData;
    }

    public InsertDataParser processImageDataElements(InsertDataParser totalExtractedData , ExtractStatus extractStatus,MongoClient mongoClient) throws IOException {

        List<ImageDataParser> imageDataList;
        ImageDataParser imageDataParser=null;
        imageDataList = templateInfoDAO.getImageTemplateInfo(extractStatus.getMainCategory(), extractStatus.getSubCategory() ,
                extractStatus.getTemplateName(), "image" );

        /* If there is no record exists for image data extraction skip this step*/
        if(imageDataList.size() != 0) {
            /*  Only one record will exist to the image data for a given PDF */
            imageDataParser =imageDataList.get(0);
            List<ImageDataElement> imageDataElements = imageDataParser.getImageDataElements();

            PDDocument doc = PDDocument.load(extractStatus.getUploadedPdfFile());
            String imageAbsolutePath;
            /* imageWritePath = where the extracted image have to be written*/
            String imageWritePath = getImageWritePath(extractStatus);


            ImageDataCoordinates imageDataCoordinates=new ImageDataCoordinates();
            imageDataCoordinates.setPdfProperties(doc,imageDataParser);

            for (ImageDataElement imageElement : imageDataElements) {

                FullSelectionImageExtractor imageExtractor = new FullSelectionImageExtractor();
                imageAbsolutePath = imageExtractor.extractImage(imageWritePath, doc, imageElement);
                imageElement.setExtractedImage(imageAbsolutePath);
            }

            ExtractedImageInserter imageInserter = new ExtractedImageInserter();
            imageInserter.insert(imageDataParser, extractStatus , mongoClient);
        }

        /*  to present it to the extracted HTML   */
        totalExtractedData.setImageDataParser(imageDataParser);
        return totalExtractedData;
    }

    public InsertDataParser processTableDataElements(InsertDataParser totalExtractedData , ExtractStatus extractStatus,MongoClient mongoClient) throws IOException, CryptographyException {

        List<TableDataParser> tableList;
        TableDataParser tableDataParser = null;
        tableList= templateInfoDAO.getTableTemplateInfo(extractStatus.getMainCategory(), extractStatus.getSubCategory() ,
                extractStatus.getTemplateName(), "table" );

        /* If there is no record exists for table data extraction skip this step*/
        if(tableList.size() != 0) {
            /*  Only one record will exist to the table data for a given PDF */
            tableDataParser=tableList.get(0);
            DataProcessor processor = new DataProcessor();
            tableDataParser = processor.processTable(tableDataParser, extractStatus);

            ExtractedTableInserter inserter = new ExtractedTableInserter();
            inserter.insert(tableDataParser, extractStatus , mongoClient);
        }
        /*  to present it to the extracted HTML   */
        totalExtractedData.setTableDataParser(tableDataParser);
        return totalExtractedData;
    }

    public String getImageWritePath(ExtractStatus extractStatus){
        String imageWritePath;
        PdfFileProcessor pdfFileProcessor=new PdfFileProcessor();
        String[] spaceReplaced= pdfFileProcessor.replaceSpace(extractStatus);
        imageWritePath=extractStatus.getRootPath()+ "extracts"+File.separator+
                        spaceReplaced[2]+File.separator+spaceReplaced[3]+File.separator+spaceReplaced[0]+
                        File.separator+spaceReplaced[1];

        return imageWritePath;
    }
}