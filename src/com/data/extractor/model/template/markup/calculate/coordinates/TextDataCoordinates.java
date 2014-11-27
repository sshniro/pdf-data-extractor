package com.data.extractor.model.template.markup.calculate.coordinates;

import com.data.extractor.model.beans.template.info.text.TextDataElement;
import com.data.extractor.model.beans.template.info.text.TextDataParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;


public class TextDataCoordinates {

    public TextDataParser setPdfProperties(PDDocument doc, TextDataParser textDataParser) {

        List<TextDataElement> textDataElements;
        textDataElements = textDataParser.getTextDataElements();

        for (TextDataElement t : textDataElements) {
            List allPages = doc.getDocumentCatalog().getAllPages();
            PDPage currentPage = (PDPage) allPages.get((t.getPageNumber() - 1));
        /*
        Because some pdfs don't contain the page Rotation Property, So setting to default rotation
         */
            int pageRotation = 0;
            if (currentPage.getRotation() != null) {
                pageRotation = currentPage.getRotation();
            }

            Double pdfWidth = (double) currentPage.getMediaBox().getWidth();
            Double pdfHeight = (double) currentPage.getMediaBox().getHeight();

            t.setPageRotation(pageRotation);
            t.setPdfWidth(pdfWidth);
            t.setPdfHeight(pdfHeight);
        }
        return textDataParser;

    }

    public TextDataParser calculateCoordinates(TextDataParser textDataParser) throws IOException {

        List<TextDataElement> textDataElements;
        textDataElements=textDataParser.getTextDataElements();
        String pdfFile=textDataParser.getPdfFile();

        for(TextDataElement t:textDataElements) {

        /*
        Returns imageDimensions imageDimensions[0]=imageWidth , imageDimensions[1]=imageHeight
        */
            Double[] imageDimensions = getImageDimensions(pdfFile,t);

            if (t.getPageRotation() == 90 || t.getPageRotation() == 270) {
                t.setTotalX1((t.getTotalX1() / imageDimensions[0]) * t.getPdfHeight());                    //x1 position(by multiplying the (x1/width)*pdfWidth )
                t.setTotalY1((t.getTotalY1() / imageDimensions[1]) * t.getPdfWidth());                  //y1 position(by multiplying the (y1/width)*pdfHeight )
                t.setTotalWidth(((t.getTotalWidth() / imageDimensions[0]) * t.getPdfHeight()) - t.getTotalX1());    // width
                t.setTotalHeight(((t.getTotalHeight() / imageDimensions[1]) * t.getPdfWidth()) - t.getTotalY1()); //height

            } else {  // TO DO Find the correct Degrees to be added in the condition
                t.setTotalX1((t.getTotalX1() / imageDimensions[0]) * t.getPdfWidth());                    //x1 position(by multiplying the (x1/width)*pdfWidth )
                t.setTotalY1((t.getTotalY1() / imageDimensions[1]) * t.getPdfHeight());                  //y1 position(by multiplying the (y1/width)*pdfHeight )
                t.setTotalWidth(((t.getTotalWidth() / imageDimensions[0]) * t.getPdfWidth()) - t.getTotalX1());    // width
                t.setTotalHeight(((t.getTotalHeight() / imageDimensions[1]) * t.getPdfHeight()) - t.getTotalY1()); //height
            }

            if (t.getMetaX1() != -1) {

                if (t.getPageRotation() == 90 || t.getPageRotation() == 270) {
                    t.setMetaX1((t.getMetaX1() / imageDimensions[0]) * t.getPdfHeight());                    //x1 position(by multiplying the (x1/width)*pdfWidth )
                    t.setMetaY1((t.getMetaY1() / imageDimensions[1]) * t.getPdfWidth());                  //y1 position(by multiplying the (y1/width)*pdfHeight )
                    t.setMetaWidth(((t.getMetaWidth() / imageDimensions[0]) * t.getPdfHeight()) - t.getMetaX1());    // width
                    t.setMetaHeight(((t.getMetaHeight() / imageDimensions[1]) * t.getPdfWidth()) - t.getMetaY1()); //height

                } else {  // TO DO Find the correct Degrees to be added in the condition
                    t.setMetaX1((t.getMetaX1() / imageDimensions[0]) * t.getPdfWidth());                    //x1 position(by multiplying the (x1/width)*pdfWidth )
                    t.setMetaY1((t.getMetaY1() / imageDimensions[1]) * t.getPdfHeight());                  //y1 position(by multiplying the (y1/width)*pdfHeight )
                    t.setMetaWidth(((t.getMetaWidth() / imageDimensions[0]) * t.getPdfWidth()) - t.getMetaX1());    // width
                    t.setMetaHeight(((t.getMetaHeight() / imageDimensions[1]) * t.getPdfHeight()) - t.getMetaY1()); //height
                }
            }
        }

        return textDataParser;
    }

    /*
    Returns imageDimensions imageDimensions[0]=imageWidth , imageDimensions[1]=imageHeight
     */
    public Double[] getImageDimensions(String pdfFile,TextDataElement textDataElement) throws IOException {

        Double[] imageDimensions=new Double[2];
        // Gets the last occurrence of "/" and splits the String
        int i=pdfFile.lastIndexOf(File.separator);
        String[] imgLocProperties={pdfFile.substring(0,i), pdfFile.substring(i)};
        // Get the pdf Name with pdf Extension "pdfName.pdf" and split it to get the pdf Name
        String pdfName=imgLocProperties[1];
        int j=pdfName.lastIndexOf(".");
        String[] pdfNameContext={pdfName.substring(0,j),pdfName.substring(j)};
        // Append the current page number and ".jpg" extension to the pdfName
        String imageName=pdfNameContext[0]+ textDataElement.getPageNumber() +".jpg";
        String imageFile=imgLocProperties[0]+ File.separator+"images" + imageName;

        BufferedImage bimg = ImageIO.read(new File(imageFile));
        imageDimensions[0]         = (double) bimg.getWidth();
        imageDimensions[1]         = (double) bimg.getHeight();
        return imageDimensions;
    }
}
