package com.data.extractor.model.template.edit;

import com.data.extractor.model.beans.manage.categories.ManageCategoriesData;
import com.data.extractor.model.beans.template.info.image.ImageDataParser;
import com.data.extractor.model.beans.template.info.insert.InsertDataParser;
import com.data.extractor.model.beans.template.info.table.TableDataParser;
import com.data.extractor.model.beans.template.info.text.TextDataParser;
import com.data.extractor.model.beans.upload.template.UploadStatus;
import com.data.extractor.model.convert.PdfToImage;
import com.data.extractor.model.data.access.layer.TemplateInfoDAO;
import com.mongodb.MongoClient;

import java.io.File;
import java.util.List;

public class PdfToImageConverter {

public UploadStatus convertToImage(UploadStatus uploadStatus , ManageCategoriesData data , MongoClient mongoClient){
    TemplateInfoDAO templateInfoDAO = new TemplateInfoDAO(mongoClient);

    List<TextDataParser> textDataParserList = templateInfoDAO.getTextTemplateInfo(data.getParent(), "text");
    List<ImageDataParser> imageDataParserList= templateInfoDAO.getImageTemplateInfo(data.getParent(), "image");
    List<TableDataParser> tableDataParserList= templateInfoDAO.getTableTemplateInfo(data.getParent(), "table");


    InsertDataParser insertDataParser = new InsertDataParser();
    if(textDataParserList.size() != 0)
        insertDataParser.setTextDataParser(textDataParserList.get(0));
    if(imageDataParserList.size() != 0)
        insertDataParser.setImageDataParser(imageDataParserList.get(0));
    if(tableDataParserList.size() != 0)
        insertDataParser.setTableDataParser(tableDataParserList.get(0));

    PdfToImage pdfToImage =new PdfToImage();
    uploadStatus.setInsertDataParser(insertDataParser);
    File uploadLocation = new File(uploadStatus.getRootPath()+ File.separator + "uploads"+File.separator+"temp" + File.separator + data.getParent() +
            File.separator + data.getId());

    uploadStatus.setPdfLocation(uploadLocation.getAbsolutePath());
    uploadStatus.setUploadedPdfFile(uploadStatus.getPdfLocation() + File.separator + data.getId() + ".pdf");
    uploadStatus.setId(data.getId());
    uploadStatus.setParent(data.getParent());
    pdfToImage.convertToImageFromTemplateEdit(uploadStatus);
    return uploadStatus;
}

}
