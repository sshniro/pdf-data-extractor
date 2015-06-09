package com.data.extractor.model.extractors.tabel;

import com.data.extractor.model.beans.template.info.table.Column;
import com.data.extractor.model.beans.template.info.table.TableDataElement;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.util.PDFTextStripperByArea;

import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.List;

public class TableExtractor {


    public String extract(PDDocument doc,TableDataElement tableDataElement){

        String extractedText=null;
        List<Column> columns=tableDataElement.getColumns();
        try {
            List allPages = doc.getDocumentCatalog().getAllPages();
            // Because the first page in PD Page is mapped to 0
            PDPage currentPage = (PDPage)allPages.get((tableDataElement.getPageNumber()-1));

            for(Column c:columns) {
                Double[] coordinates = new Double[4];
                coordinates[0] = c.getMetaX1();
                coordinates[1] = c.getMetaHeight()+c.getMetaY1();
                coordinates[2] = c.getMetaWidth();
                coordinates[3] = tableDataElement.getTotalHeight();

                PDFTextStripperByArea areaStriper = new PDFTextStripperByArea();
                Rectangle2D region = new Rectangle2D.Double(coordinates[0], coordinates[1], coordinates[2], coordinates[3]);
                String regionName = "region";
                areaStriper.addRegion(regionName, region);

                areaStriper.extractRegions(currentPage);
                //System.out.println("Text in the area:" + region);
                extractedText = areaStriper.getTextForRegion("region");
                //System.out.println(extractedText);
                c.setExtractedValues(extractedText);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return extractedText;
    }

}
