package com.data.extractor.model.interfaces;


import com.data.extractor.model.beans.template.info.image.ImageDataElement;
import com.data.extractor.model.beans.template.info.image.ImageDataParser;
import com.data.extractor.model.beans.template.info.table.TableDataElement;
import com.data.extractor.model.beans.template.info.table.TableDataParser;
import com.data.extractor.model.beans.template.info.text.TextDataElement;
import com.data.extractor.model.beans.template.info.text.TextDataParser;

import java.util.List;

public interface TemplateInfo {

    public int getTemplateInfoSize(String mainCat , String subCat,String tempName,String dataType);
    public List<TextDataParser> getTextTemplateInfo(String mainCat , String subCat,String tempName,String dataType);
    public List<ImageDataParser> getImageTemplateInfo(String mainCat , String subCat,String tempName,String dataType);
    public List<TableDataParser> getTableTemplateInfo(String mainCat , String subCat,String tempName,String dataType);

    public void createTemplateInfo(String mainCat , String subCat,String tempName,String dataType,
                                   TextDataElement textDataElement);
    public void createTemplateInfo(String mainCat , String subCat,String tempName,String dataType,
                                   ImageDataElement imageDataElement);
    public void createTemplateInfo(String mainCat , String subCat,String tempName,String dataType,
                                   TableDataElement tableDataElement);

    public void updateTemplateInfo(TextDataParser textDataParser,TextDataElement textDataElement);
    public void updateTemplateInfo(ImageDataParser imageDataParser,ImageDataElement imageDataElement);
    public void updateTemplateInfo(TableDataParser tableDataParser,TableDataElement tableDataElement);

    public void removeTemplateInfo(String mainCategory);
    public void removeTemplateInfo(String mainCategory,String subCategory);
    public void removeTemplateInfo(String mainCategory,String subCategory,String templateName);


}
