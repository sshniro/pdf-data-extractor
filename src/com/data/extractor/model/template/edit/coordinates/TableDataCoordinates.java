package com.data.extractor.model.template.edit.coordinates;

import com.data.extractor.model.beans.template.info.table.Column;
import com.data.extractor.model.beans.template.info.table.TableDataElement;
import com.data.extractor.model.beans.template.info.table.TableDataParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class TableDataCoordinates {

    public TableDataParser setPdfProperties(PDDocument doc, TableDataParser tableDataParser){

        List<TableDataElement> tableDataElements;
        tableDataElements=tableDataParser.getTableDataElements();

        List<Column> columns;

        for(TableDataElement ta:tableDataElements) {
            List allPages = doc.getDocumentCatalog().getAllPages();

            PDPage currentPage = (PDPage) allPages.get((ta.getPageNumber() - 1));
        /*
        Because some pdfs don't contain the page Rotation Property, So setting to default rotation
         */
            int pageRotation = 0;
            if (currentPage.getRotation() != null) {
                pageRotation = currentPage.getRotation();
            }

            Double pdfWidth = (double) currentPage.getMediaBox().getWidth();
            Double pdfHeight = (double) currentPage.getMediaBox().getHeight();

            ta.setPageRotation(pageRotation);
            ta.setPdfWidth(pdfWidth);
            ta.setPdfHeight(pdfHeight);


        }
        return tableDataParser;
    }

    public TableDataParser calculateCoordinates(TableDataParser tableDataParser) throws IOException {

        List<TableDataElement> tableDataElements;
        String pdfFile=tableDataParser.getPdfFile();
        tableDataElements=tableDataParser.getTableDataElements();

        List<Column> columns;

        for(TableDataElement ta:tableDataElements) {

        /*
        Returns imageDimensions imageDimensions[0]=imageWidth , imageDimensions[1]=imageHeight
        */
            Double[] imageDimensions = getImageDimensions(pdfFile, ta);

            if (ta.getPageRotation() == 90 || ta.getPageRotation() == 270) {
                ta.setTotalX1((ta.getTotalX1() / imageDimensions[0]) * ta.getPdfHeight());                    //x1 position(by multiplying the (x1/width)*pdfWidth )
                ta.setTotalY1((ta.getTotalY1() / imageDimensions[1]) * ta.getPdfWidth());                  //y1 position(by multiplying the (y1/width)*pdfHeight )
                ta.setTotalWidth(((ta.getTotalWidth() / imageDimensions[0]) * ta.getPdfHeight()) - ta.getTotalX1());    // width
                ta.setTotalHeight(((ta.getTotalHeight() / imageDimensions[1]) * ta.getPdfWidth()) - ta.getTotalY1()); //height

            } else {  // TO DO Find the correct Degrees to be added in the condition
                ta.setTotalX1((ta.getTotalX1() / imageDimensions[0]) * ta.getPdfWidth());                    //x1 position(by multiplying the (x1/width)*pdfWidth )
                ta.setTotalY1((ta.getTotalY1() / imageDimensions[1]) * ta.getPdfHeight());                  //y1 position(by multiplying the (y1/width)*pdfHeight )
                ta.setTotalWidth(((ta.getTotalWidth() / imageDimensions[0]) * ta.getPdfWidth()) - ta.getTotalX1());    // width
                ta.setTotalHeight(((ta.getTotalHeight() / imageDimensions[1]) * ta.getPdfHeight()) - ta.getTotalY1()); //height
            }


            columns=ta.getColumns();
            /* imageDimensions[0]=imageWidth , imageDimensions[1]=imageHeight */

            for (Column c:columns){
                if (ta.getPageRotation() == 90 || ta.getPageRotation() == 270) {
                    c.setMetaX1((c.getMetaX1() / imageDimensions[0]) * ta.getPdfHeight());                    //x1 position(by multiplying the (x1/width)*pdfWidth )
                    c.setMetaY1((c.getMetaY1() / imageDimensions[1]) * ta.getPdfWidth());                  //y1 position(by multiplying the (y1/width)*pdfHeight )
                    c.setMetaWidth(((c.getMetaWidth() / imageDimensions[0]) * ta.getPdfHeight()) - c.getMetaX1());    // width
                    c.setMetaHeight(((c.getMetaHeight() / imageDimensions[1]) * ta.getPdfWidth()) - c.getMetaY1()); //height

                } else {  // TO DO Find the correct Degrees to be added in the condition
                    c.setMetaX1((c.getMetaX1() / imageDimensions[0]) * ta.getPdfWidth());                    //x1 position(by multiplying the (x1/width)*pdfWidth )
                    c.setMetaY1((c.getMetaY1() / imageDimensions[1]) * ta.getPdfHeight());                  //y1 position(by multiplying the (y1/width)*pdfHeight )
                    c.setMetaWidth(((c.getMetaWidth() / imageDimensions[0]) * ta.getPdfWidth()) - c.getMetaX1());    // width
                    c.setMetaHeight(((c.getMetaHeight() / imageDimensions[1]) * ta.getPdfHeight()) - c.getMetaY1()); //height
                }
            }
        }
        return tableDataParser;
    }

    /*
    Returns imageDimensions imageDimensions[0]=imageWidth , imageDimensions[1]=imageHeight
     */
    public Double[] getImageDimensions(String pdfFile,TableDataElement tableDataElement) throws IOException {

        Double[] imageDimensions=new Double[2];
        // Gets the last occurance of "/" and splits the String
        int i=pdfFile.lastIndexOf(File.separator);
        String[] imgLocProperties={pdfFile.substring(0,i), pdfFile.substring(i)};
        // Get the pdf Name with pdf Extension "pdfName.pdf" and split it to get the pdf Name
        String pdfName=imgLocProperties[1];
        int j=pdfName.lastIndexOf(".");
        String[] pdfNameContext={pdfName.substring(0,j),pdfName.substring(j)};
        // Append the current page number and ".jpg" extension to the pdfName
        String imageName=pdfNameContext[0]+ tableDataElement.getPageNumber() +".jpg";
        String imageFile=imgLocProperties[0]+ File.separator+"images" + imageName;

        BufferedImage bimg = ImageIO.read(new File(imageFile));
        imageDimensions[0]         = (double) bimg.getWidth();
        imageDimensions[1]         = (double) bimg.getHeight();
        return imageDimensions;
    }
}
