package com.data.extractor.model.convert;

import com.data.extractor.model.beans.upload.template.UploadStatus;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;


public class PdfToImage {
    public UploadStatus convertToImage(UploadStatus uploadStatus){

        try {
            PDDocument doc = PDDocument.load(uploadStatus.getUploadedPdfFile());
            //Get all pages from document and store them in a list
            List<PDPage> pages = doc.getDocumentCatalog().getAllPages();
            //create iterator object so it is easy to access each page from the list
            Iterator<PDPage> i = pages.iterator();
            int count = 1; //count variable used to separate each image file
            System.out.println("Please wait...");

            File path = new File(uploadStatus.getPdfLocation() + File.separator+ "images");
            if (!path.exists()) {
                path.mkdirs();
            }

            /* spaceReplaced[0]=mainCategory,spaceReplaced[1]=subCategory,spaceReplaced[2]=templateName */
            //String[] spaceReplaced=replaceSpace(uploadStatus);
            String[] imageRelativePaths=new String[pages.size()];
            String imgPath=uploadStatus.getPdfLocation()+File.separator+"images"+File.separator;


            while (i.hasNext()) {
                PDPage page = i.next();
                BufferedImage bi = page.convertToImage();
                ImageIO.write(bi, "jpg", new File(imgPath + uploadStatus.getId() + count + ".jpg"));

                /*imageRelativePaths=uploads/mainCategory/SubCategory/imageName */
                imageRelativePaths[count-1]="uploads"+File.separator + uploadStatus.getId()+ File.separator + "images"+ File.separator+ uploadStatus.getId() + count+".jpg";
                count++;
            }

            System.out.println("Conversion complete");
            doc.close();
            uploadStatus.setImageRelativePaths(imageRelativePaths);

        } catch (IOException ie) {
            ie.printStackTrace();
        }
        return uploadStatus;
    }
}
