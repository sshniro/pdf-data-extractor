package com.data.extractor.model.template.markup;

import com.data.extractor.model.beans.manage.categories.Node;
import com.data.extractor.model.beans.markup.template.MarkUpResponse;
import com.data.extractor.model.beans.template.info.pattern.FormPairData;
import com.data.extractor.model.beans.template.info.text.TextDataElement;
import com.data.extractor.model.beans.template.info.text.TextDataParser;
import com.data.extractor.model.data.access.layer.TemplatesDAO;
import com.data.extractor.model.extractors.text.FullSelectionTextExtractor;
import com.data.extractor.model.extractors.text.MetaSelectionTextExtractor;
import com.data.extractor.model.template.markup.calculate.coordinates.TextDataCoordinates;
import com.google.gson.Gson;
import com.mongodb.MongoClient;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PatternDataExtractor {

    public MarkUpResponse extractText(String jsonRequest,MongoClient mongoClient) throws IOException {

        String extractedText=null;
        MarkUpResponse markUpResponse=new MarkUpResponse();
        TemplatesDAO templatesDAO =  new TemplatesDAO(mongoClient);

        /* Assign the input from the user to the textDataParser */
        Gson gson=new Gson();
        TextDataParser textDataParser;
        jsonRequest = jsonRequest.replaceFirst("patternDataElements","textDataElements");
        textDataParser=gson.fromJson(jsonRequest, TextDataParser.class);

        List<TextDataElement> textDataElements=textDataParser.getTextDataElements();

        Node node = templatesDAO.getNode(textDataParser.getId());
        textDataParser.setPdfFile(node.getPdfFile());
        PDDocument doc =PDDocument.load(textDataParser.getPdfFile());

        /* Get the first textDataElement because only  textDataElement to extract*/
        TextDataElement textDataElement=textDataElements.get(0);

        TextDataCoordinates textDataCoordinates=new TextDataCoordinates();
        /* Set Values for the PDF width, Height and Rotation for the first textDataElement*/
        textDataCoordinates.setPdfProperties(doc, textDataParser);
        /* Recalculate and set coordinates according to the actual pdf width and height & Page Rotation */
        textDataCoordinates.calculateCoordinates(textDataParser);

        /* If There is no metaArea defined extract the full area */
        if(textDataElement.getMetaX1()==-1) {

            FullSelectionTextExtractor fullExtractor=new FullSelectionTextExtractor();
            extractedText=fullExtractor.extract(doc,textDataElement);

        }

        markUpResponse.setDataType(textDataParser.getDataType());
        markUpResponse.setExtractedData(extractedText);

        doc.close();

        return markUpResponse;
    }

//    public static void main(String[] args) {
//
//        String extracted = "Size: L.L Qty Ordered: 93 Cost per Unit: $ 10.00";
//        String[] split = splitWordsByColon(extracted);
//        int arrayLength = split.length;
//
//        List<FormPairData> pairs = new ArrayList<FormPairData>();
//
//        for (int i=0;i<arrayLength;i++){
//            FormPairData pairData = new FormPairData();
//            /* If it is a last attribute */
//            if(i == arrayLength-1){
//                break;
//            }
//            if(i == arrayLength-2){
//                split[i+1] = "eol";
//            }
//            split[i] = split[i] + ":";
//            pairData.setStart(split[i]);
//            pairData.setEnd(split[i+1]);
//            pairs.add(pairData);
//        }
//    }

    public List<FormPairData> processRequest(String extracted){
        String[] split = splitWordsByColon(extracted);
        int arrayLength = split.length;

        List<FormPairData> pairs = new ArrayList<FormPairData>();

        for (int i=0;i<arrayLength;i++){
            FormPairData pairData = new FormPairData();
            /* If it is a last attribute */
            if(i == arrayLength-1){
                break;
            }
            if(i == arrayLength-2){
                split[i+1] = "eol";
            }
            split[i] = split[i] + ":";
            pairData.setStart(split[i]);
            pairData.setEnd(split[i+1]);
            pairs.add(pairData);
        }
        return pairs;
    }

    public static String[] splitWordsByColon(String line){
        String[] split = line.split(":");
        return  split;
    }


}
