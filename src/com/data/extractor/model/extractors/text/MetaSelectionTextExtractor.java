package com.data.extractor.model.extractors.text;


import com.data.extractor.model.beans.template.info.text.TextDataElement;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.util.PDFTextStripperByArea;

import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.List;

public class MetaSelectionTextExtractor {

    public String extractWithOutLabel(PDDocument doc,TextDataElement textDataElement ) throws IOException {


        List allPages = doc.getDocumentCatalog().getAllPages();
        PDPage currentPage = (PDPage)allPages.get(textDataElement.getPageNumber()-1);

        StringBuilder extractedText=new StringBuilder("");
        String regionName1="regionAfterLabel";
        String regionName2="regionBelowLabel";

        Double[] coordinates=assignRegionCoordinates(textDataElement);

        PDFTextStripperByArea areaStriper=new PDFTextStripperByArea();
        Double[] afterCoordinates=extractTextAfterLabel(coordinates);

        /* Skip if width or height of the area is zero or less */
        if(afterCoordinates[2] > 0 && afterCoordinates[3] > 0) {

            Rectangle2D regionAfterLabel = new Rectangle2D.Double(afterCoordinates[0], afterCoordinates[1],
                    afterCoordinates[2], afterCoordinates[3]);
            areaStriper.addRegion(regionName1, regionAfterLabel);
            areaStriper.extractRegions(currentPage);
            extractedText.append(areaStriper.getTextForRegion("regionAfterLabel"));

        }

        Double[] belowCoordinates = extractTextBelowLabel(coordinates);

        /* Skip if width or height of the area is zero or less */
        if(belowCoordinates[2] > 0 && belowCoordinates[3] > 0){

            Rectangle2D regionBelowLabel = new Rectangle2D.Double(belowCoordinates[0], belowCoordinates[1],
                    belowCoordinates[2], belowCoordinates[3]);
            areaStriper.addRegion(regionName2, regionBelowLabel);
            areaStriper.extractRegions(currentPage);
            extractedText.append(areaStriper.getTextForRegion("regionBelowLabel"));

        }

        return extractedText.toString();
    }

    public Double[] assignRegionCoordinates(TextDataElement textDataElement){
        Double[] regionCoordinates=new Double[8];

        regionCoordinates[0]=textDataElement.getMetaX1();
        regionCoordinates[1]=textDataElement.getMetaY1();
        regionCoordinates[2]=textDataElement.getMetaWidth();
        regionCoordinates[3]=textDataElement.getMetaHeight();
        regionCoordinates[4]=textDataElement.getTotalX1();
        regionCoordinates[5]=textDataElement.getTotalY1();
        regionCoordinates[6]=textDataElement.getTotalWidth();
        regionCoordinates[7]=textDataElement.getTotalHeight();

        return regionCoordinates;
    }

    public Double[] extractTextAboveLabel(Double[] coordinates){

        Double[] aboveCoordinates=new Double[4];
        /* extractRegionX1 = fullX1 */
        aboveCoordinates[0]=coordinates[4];
        /* extractRegionY1 = fullY1 */
        aboveCoordinates[1]=coordinates[5];
        /* extractRegionWidth = fullWidth */
        aboveCoordinates[2]= coordinates[6];
        /* extractRegionHeight = metaY1 - fullY1 */
        aboveCoordinates[3]=coordinates[1]-coordinates[5];

        return aboveCoordinates;

    }

    public Double[] extractTextAfterLabel(Double[] coordinates){

        Double[] afterCoordinates=new Double[4];
        /* extractRegionX1 = metaX1 + metaWidth */
        afterCoordinates[0]=coordinates[0]+coordinates[2];
        /* extractRegionY1 = metaY1 */
        afterCoordinates[1]=coordinates[1];
        /* extractRegionWidth = ( totalX1 + totalWidth ) - extractRegionX1 */
        afterCoordinates[2]=(coordinates[4]+coordinates[6])-afterCoordinates[0];
        /* extractRegionHeight = metaHeight */
        afterCoordinates[3]=coordinates[3];

        return afterCoordinates;

    }
    public Double[] extractTextBeforeLabel(Double[] coordinates){

        Double[] beforeCoordinates=new Double[4];
        /* extractRegionX1 = totalX1 */
        beforeCoordinates[0]=coordinates[4];
        /* extractRegionY1 = metaY1 */
        beforeCoordinates[1]=coordinates[1];
        /* extractRegionWidth = metaX1 - totalX1 */
        beforeCoordinates[2]=coordinates[0]-coordinates[4];
        /* extractRegionHeight = metaHeight */
        beforeCoordinates[3]=coordinates[3];

        return beforeCoordinates;

    }

    public Double[] extractTextBelowLabel(Double[] coordinates){

        Double[] belowCoordinates=new Double[4];
        /*  extractRegionX1 = totalX1   */
        belowCoordinates[0]=coordinates[4];
        /*  extractRegionY1 = metaY1 + metaHeight   */
        belowCoordinates[1]=coordinates[1]+coordinates[3];
        /*  extractRegionWidth = totalWidth   */
        belowCoordinates[2]=coordinates[6];
        /*  extractRegionHeight = (totalY1+totalHeight) - extractRegionY1   */
        belowCoordinates[3]=(coordinates[5]+coordinates[7])-belowCoordinates[1];

        return belowCoordinates;
    }
}
