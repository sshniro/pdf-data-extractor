package com.data.extractor.model.extract.pdf;


import com.data.extractor.model.beans.extract.pdf.ExtractStatus;
import com.data.extractor.model.beans.template.info.image.ImageDataElement;
import com.data.extractor.model.beans.template.info.image.ImageDataParser;
import com.data.extractor.model.beans.template.info.insert.InsertDataParser;
import com.data.extractor.model.beans.template.info.regex.RegexDataElement;
import com.data.extractor.model.beans.template.info.regex.RegexDataParser;
import com.data.extractor.model.beans.template.info.table.TableDataParser;
import com.data.extractor.model.beans.template.info.text.TextDataElement;
import com.data.extractor.model.beans.template.info.text.TextDataParser;
import com.data.extractor.model.data.access.layer.TemplateInfoDAO;
import com.data.extractor.model.extract.pdf.inserter.ExtractedDataInserter;
import com.data.extractor.model.extract.pdf.table.DataProcessor;
import com.data.extractor.model.extractors.image.FullSelectionImageExtractor;
import com.data.extractor.model.extractors.regex.RegexDataExtractor;
import com.data.extractor.model.extractors.text.FullSelectionTextExtractor;
import com.data.extractor.model.extractors.text.MetaSelectionTextExtractor;
import com.data.extractor.model.template.markup.calculate.coordinates.ImageDataCoordinates;
import com.mongodb.MongoClient;
import org.apache.pdfbox.exceptions.CryptographyException;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class DataElementsProcessor {

    private TemplateInfoDAO templateInfoDAO;
    private ExtractedDataInserter dataInserter;

    public DataElementsProcessor(MongoClient mongoClient){
        this.templateInfoDAO= new TemplateInfoDAO(mongoClient);
        this.dataInserter = new ExtractedDataInserter();
    }

    public InsertDataParser processTextDataElements(InsertDataParser insertDataParser , ExtractStatus extractStatus,MongoClient mongoClient) throws IOException {

        List<TextDataParser> textDataList;
        TextDataParser textDataParser=null;
        textDataList = templateInfoDAO.getTextTemplateInfo(extractStatus.getParent() , "text" );

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
            dataInserter.insert(textDataParser, extractStatus ,mongoClient);
        }
        /*  to present it to the extracted HTML   */
        insertDataParser.setTextDataParser(textDataParser);
        return insertDataParser;
    }

    public InsertDataParser processImageDataElements(InsertDataParser insertDataParser , ExtractStatus extractStatus,MongoClient mongoClient) throws IOException {

        List<ImageDataParser> imageDataList;
        ImageDataParser imageDataParser=null;
        imageDataList = templateInfoDAO.getImageTemplateInfo(extractStatus.getParent(),"image" );

        /* If there is no record exists for image data extraction skip this step*/
        if(imageDataList.size() != 0) {
            /*  Only one record will exist to the image data for a given PDF */
            imageDataParser =imageDataList.get(0);
            List<ImageDataElement> imageDataElements = imageDataParser.getImageDataElements();

            PDDocument doc = PDDocument.load(extractStatus.getUploadedPdfFile());
            String imageAbsolutePath;
            /* imageWritePath = where the extracted image have to be written*/
            String imageWritePath = extractStatus.getPdfLocation()+File.separator+"images";

            ImageDataCoordinates imageDataCoordinates=new ImageDataCoordinates();
            imageDataCoordinates.setPdfProperties(doc,imageDataParser);

            for (ImageDataElement imageElement : imageDataElements) {

                FullSelectionImageExtractor imageExtractor = new FullSelectionImageExtractor();
                imageAbsolutePath = imageExtractor.extractImage(imageWritePath, doc, imageElement);
                imageElement.setExtractedImage(imageAbsolutePath);
            }
            dataInserter.insert(imageDataParser, extractStatus , mongoClient);
        }

        /*  to present it to the extracted HTML   */
        insertDataParser.setImageDataParser(imageDataParser);
        return insertDataParser;
    }

    public InsertDataParser processTableDataElements(InsertDataParser insertDataParser , ExtractStatus extractStatus,MongoClient mongoClient) throws IOException, CryptographyException {

        List<TableDataParser> tableList;
        TableDataParser tableDataParser = null;
        tableList= templateInfoDAO.getTableTemplateInfo(extractStatus.getParent() , "table" );

        /* If there is no record exists for table data extraction skip this step*/
        if(tableList.size() != 0) {
            /*  Only one record will exist to the table data for a given PDF */
            tableDataParser=tableList.get(0);
            DataProcessor processor = new DataProcessor();
            tableDataParser = processor.processTable(tableDataParser, extractStatus);

            dataInserter.insert(tableDataParser, extractStatus , mongoClient);
        }
        /*  to present it to the extracted HTML   */
        insertDataParser.setTableDataParser(tableDataParser);
        return insertDataParser;
    }

    public InsertDataParser processRegexDataElements(InsertDataParser insertDataParser, ExtractStatus extractStatus, MongoClient mongoClient) throws IOException {

        List<RegexDataParser> regexDataParserList;
        RegexDataParser regexDataParser = null;

        regexDataParserList = templateInfoDAO.getRegexTemplateInfo(extractStatus.getParent() , "regex");

        /* Check if no text data available to extract then skip*/
        if(regexDataParserList.size() !=0 ){
            /*  Only one record will exist to the text data for a given PDF */
            regexDataParser =regexDataParserList.get(0);
            List<RegexDataElement> regexDataElementList = regexDataParser.getRegexDataElements();

            PDDocument doc = PDDocument.load(extractStatus.getUploadedPdfFile());
            String extractedText;

            for (RegexDataElement r : regexDataElementList) {
                RegexDataExtractor regexDataExtractor = new RegexDataExtractor();
                if(r.getEndTag().equals("eol")){
                    r.setEndTag(System.getProperty("line.separator"));
                }
                extractedText = regexDataExtractor.extract("text",r.getStartTag(),r.getEndTag());
            }
            dataInserter.insert(regexDataParser, extractStatus ,mongoClient);
        }
        /*  to present it to the extracted HTML   */
        insertDataParser.setRegexDataParser(regexDataParser);
        return insertDataParser;
    }
}
