package com.data.extractor.model.template.edit;

import com.data.extractor.model.beans.manage.categories.Node;
import com.data.extractor.model.beans.markup.template.MarkUpResponse;
import com.data.extractor.model.beans.template.info.text.TextDataElement;
import com.data.extractor.model.beans.template.info.text.TextDataParser;
import com.data.extractor.model.data.access.layer.TemplatesDAO;
import com.data.extractor.model.extractors.text.FullSelectionTextExtractor;
import com.data.extractor.model.extractors.text.MetaSelectionTextExtractor;
import com.data.extractor.model.template.edit.coordinates.TextDataCoordinates;
import com.google.gson.Gson;
import com.mongodb.MongoClient;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;
import java.util.List;


public class TextDataExtractor {

    public MarkUpResponse extractText(String jsonRequest,MongoClient mongoClient) throws IOException {

        String extractedText;
        MarkUpResponse markUpResponse=new MarkUpResponse();
        TemplatesDAO templatesDAO =  new TemplatesDAO(mongoClient);

        /* Assign the input from the user to the textDataParser */
        Gson gson=new Gson();
        TextDataParser textDataParser;
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

        }else {
            /* If There is a metaArea defined exclude the metaArea and only extract the otherArea */
            MetaSelectionTextExtractor metaSelectionTextExtractor =new MetaSelectionTextExtractor();
            extractedText= metaSelectionTextExtractor.extractWithOutLabel(doc,textDataElement);

        }

        markUpResponse.setDataType(textDataParser.getDataType());
        markUpResponse.setExtractedData(extractedText);

        doc.close();

        return markUpResponse;
    }
}
