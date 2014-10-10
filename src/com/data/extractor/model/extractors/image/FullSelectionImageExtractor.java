package com.data.extractor.model.extractors.image;

import com.data.extractor.model.beans.template.info.image.ImageDataElement;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class FullSelectionImageExtractor {

    public String extractImage(String imageWritePath,PDDocument doc,ImageDataElement imageDataElement) throws IOException {

        String imageFile;
        List allPages = doc.getDocumentCatalog().getAllPages();
        PDPage currentPage = (PDPage)allPages.get((imageDataElement.getPageNumber()-1));

        PDRectangle rectangle=new PDRectangle();

        if(imageDataElement.getPageRotation()==90 || imageDataElement.getPageRotation()==270) {

            /*   lowerLeftX1 =  totalY1 */
            rectangle.setLowerLeftX(imageDataElement.getTotalY1().floatValue());
            /*   lowerLeftY1 =  totalX1 */
            rectangle.setLowerLeftY(imageDataElement.getTotalX1().floatValue());
            /*   upperRightX2 =  totalHeight + totalY1 */
            rectangle.setUpperRightX(imageDataElement.getTotalHeight().floatValue() +
                                    imageDataElement.getTotalY1().floatValue());
            /*   upperRightY2 =  totalWidth - totalX1 */
            rectangle.setUpperRightY(imageDataElement.getTotalWidth().floatValue() +
                                    imageDataElement.getTotalX1().floatValue());

        }else { // TO DO ~ Coordinates Calculations for other page rotations
            /*   lowerLeftX1=  totalX1 */
            rectangle.setLowerLeftX(imageDataElement.getTotalX1().floatValue());
            /*   lowerLeftY1=  pdfHeight - (totalY1 + totalHeight) */
            rectangle.setLowerLeftY(imageDataElement.getPdfHeight().floatValue()-
                                    (imageDataElement.getTotalY1().floatValue() +
                                    imageDataElement.getTotalHeight().floatValue()));
            /*   upperRightX2=  totalX1 + totalWidth */
            rectangle.setUpperRightX(imageDataElement.getTotalWidth().floatValue() +
                                    imageDataElement.getTotalX1().floatValue());
            /*   upperRightY2=  pdfHeight - totalY1 */
            rectangle.setUpperRightY(imageDataElement.getPdfHeight().floatValue() -
                                    imageDataElement.getTotalY1().floatValue());
        }

        currentPage.setCropBox(rectangle);

        String imgPath=imageWritePath+ File.separator;
        BufferedImage bi = currentPage.convertToImage();

        /* If folder Structure does not exists create folders */
        File uploadLocation = new File(imageWritePath);
        if (!uploadLocation.exists()) {
            boolean status = uploadLocation.mkdirs();
        }

        String spaceRmvdMetaId=replaceSpace(imageDataElement.getMetaId());
        imageFile=imgPath+spaceRmvdMetaId + ".jpg";
        ImageIO.write(bi, "jpg", new File(imgPath+spaceRmvdMetaId + ".jpg"));

        return imageFile;
    }

    /*
    Replaces all white space with '_' character
     */
    public String replaceSpace(String imgMEtaId){

        String replacedTxt=imgMEtaId.replaceAll("\\s+","_");
        return replacedTxt;
    }
}
