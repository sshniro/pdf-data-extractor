package com.data.extractor.model.template.edit.coordinates;

import com.data.extractor.model.beans.template.info.image.ImageDataElement;
import com.data.extractor.model.beans.template.info.image.ImageDataParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ImageDataCoordinates {

    public ImageDataParser setPdfProperties(PDDocument doc, ImageDataParser imageDataParser){

        List<ImageDataElement> imageDataElements;
        imageDataElements=imageDataParser.getImageDataElements();

        for(ImageDataElement i:imageDataElements) {
            List allPages = doc.getDocumentCatalog().getAllPages();

            PDPage currentPage = (PDPage) allPages.get((i.getPageNumber() - 1));
        /*
        Because some pdfs don't contain the page Rotation Property, So setting to default rotation
         */
            int pageRotation = 0;
            if (currentPage.getRotation() != null) {
                pageRotation = currentPage.getRotation();
            }

            Double pdfWidth = (double) currentPage.getMediaBox().getWidth();
            Double pdfHeight = (double) currentPage.getMediaBox().getHeight();

            i.setPageRotation(pageRotation);
            i.setPdfWidth(pdfWidth);
            i.setPdfHeight(pdfHeight);
        }
        return imageDataParser;
    }

    public ImageDataParser calculateCoordinates(ImageDataParser imageDataParser) throws IOException {

        List<ImageDataElement> imageDataElements;
        imageDataElements=imageDataParser.getImageDataElements();
        String pdfFile=imageDataParser.getPdfFile();

        for(ImageDataElement i:imageDataElements) {

        /*
        Returns imageDimensions imageDimensions[0]=imageWidth , imageDimensions[1]=imageHeight
        */
            Double[] imageDimensions = getImageDimensions(pdfFile,i);

            if (i.getPageRotation() == 90 || i.getPageRotation() == 270) {
                i.setTotalX1((i.getTotalX1() / imageDimensions[0]) * i.getPdfHeight());                    //x1 position(by multiplying the (x1/width)*pdfWidth )
                i.setTotalY1((i.getTotalY1() / imageDimensions[1]) * i.getPdfWidth());                  //y1 position(by multiplying the (y1/width)*pdfHeight )
                i.setTotalWidth(((i.getTotalWidth() / imageDimensions[0]) * i.getPdfHeight()) - i.getTotalX1());    // width
                i.setTotalHeight(((i.getTotalHeight() / imageDimensions[1]) * i.getPdfWidth()) - i.getTotalY1()); //height

            } else {  // TO DO Find the correct Degrees to be added in the condition
                i.setTotalX1((i.getTotalX1() / imageDimensions[0]) * i.getPdfWidth());                    //x1 position(by multiplying the (x1/width)*pdfWidth )
                i.setTotalY1((i.getTotalY1() / imageDimensions[1]) * i.getPdfHeight());                  //y1 position(by multiplying the (y1/width)*pdfHeight )
                i.setTotalWidth(((i.getTotalWidth() / imageDimensions[0]) * i.getPdfWidth()) - i.getTotalX1());    // width
                i.setTotalHeight(((i.getTotalHeight() / imageDimensions[1]) * i.getPdfHeight()) - i.getTotalY1()); //height
            }
        }
        return imageDataParser;
    }

    /*
    Returns imageDimensions imageDimensions[0]=imageWidth , imageDimensions[1]=imageHeight
     */
    public Double[] getImageDimensions(String pdfFile,ImageDataElement imageDataElement) throws IOException {

        Double[] imageDimensions=new Double[2];

        int i=pdfFile.lastIndexOf(File.separator);
        String[] imgLocProperties={pdfFile.substring(0,i), pdfFile.substring(i)};

        // Get the pdf Name with pdf Extension "pdfName.pdf" and split it to get the pdf Name
        String pdfName=imgLocProperties[1];
        int j=pdfName.lastIndexOf(".");
        String[] pdfNameContext={pdfName.substring(0,j),pdfName.substring(j)};
        // Append the current page number and ".jpg" extension to the pdfName
        String imageName=pdfNameContext[0]+ imageDataElement.getPageNumber() +".jpg";
        String imageFile=imgLocProperties[0]+ File.separator+"images" + imageName;

        BufferedImage bimg = ImageIO.read(new File(imageFile));
        imageDimensions[0]         = (double) bimg.getWidth();
        imageDimensions[1]         = (double) bimg.getHeight();
        return imageDimensions;
    }

    public String[] getPdfWritePath(ImageDataParser imageDataParser){
        String pdfFile=imageDataParser.getPdfFile();
        // Gets the last occurrence of "/" and splits the String
        int i=pdfFile.lastIndexOf(File.separator);
        return new String[]{pdfFile.substring(0,i), pdfFile.substring(i)};
    }


}
