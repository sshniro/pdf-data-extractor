package com.data.extractor.model.extractors.text;

import com.data.extractor.model.beans.template.info.text.TextDataElement;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.util.PDFTextStripperByArea;

import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.List;


public class FullSelectionTextExtractor {

    public String extract(PDDocument doc,TextDataElement textDataElement) throws IOException {
        String extractedText=null;

        List allPages = doc.getDocumentCatalog().getAllPages();
        // Because the first page in PD Page is mapped to 0
        PDPage currentPage = (PDPage)allPages.get((textDataElement.getPageNumber()-1));

        Double[] coordinates=new Double[4];
        coordinates[0]=textDataElement.getTotalX1();
        coordinates[1]=textDataElement.getTotalY1();
        coordinates[2]=textDataElement.getTotalWidth();
        coordinates[3]=textDataElement.getTotalHeight();

        PDFTextStripperByArea areaStriper=new PDFTextStripperByArea();
        Rectangle2D region = new Rectangle2D.Double(coordinates[0],coordinates[1],coordinates[2],coordinates[3]);
        String regionName = "region";
        areaStriper.addRegion(regionName, region);

        areaStriper.extractRegions(currentPage);
        //System.out.println( "Text in the area:" + region );
        extractedText=areaStriper.getTextForRegion("region");



        return extractedText;
    }
}
