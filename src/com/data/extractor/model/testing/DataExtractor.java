package com.data.extractor.model.testing;

import com.data.extractor.model.beans.template.info.image.ImageDataElement;
import com.data.extractor.model.beans.template.info.image.ImageDataParser;
import com.data.extractor.model.beans.template.info.insert.InsertDataParser;
import com.data.extractor.model.beans.template.info.table.Cell;
import com.data.extractor.model.beans.template.info.table.Column;
import com.data.extractor.model.beans.template.info.table.TableDataElement;
import com.data.extractor.model.beans.template.info.table.TableDataParser;
import com.data.extractor.model.beans.template.info.text.TextDataElement;
import com.data.extractor.model.beans.template.info.text.TextDataParser;

import java.util.List;

public class DataExtractor {

    public String convertToString(InsertDataParser data){

        TextDataParser textDataParser = data.getTextDataParser();
        ImageDataParser imageDataParser = data.getImageDataParser();
        TableDataParser tableDataParser = data.getTableDataParser();

        StringBuilder sb=new StringBuilder("");

        if(textDataParser != null){

            List<TextDataElement> textList = textDataParser.getTextDataElements();
            sb.append("Extracted Text : \n" );
            for (int i=0 ; i < textList.size() ; i++) {
                TextDataElement text = textList.get(i);
                sb.append("Text ").append(i+1).append(" -name(").append(text.getMetaId()).append(") : ");
                sb.append(replaceNextLineChar(text.getExtractedText()));
                sb.append("\n");
            }

        }


        if(imageDataParser  != null){
            sb.append("Extracted Image : \n" );
            List<ImageDataElement> imageList = imageDataParser.getImageDataElements();
            for (ImageDataElement imageDataElement : imageList) {
                sb.append(imageDataElement.getExtractedImage());
                sb.append("\n");
            }

        }


        if(tableDataParser != null){
            sb.append("\nExtracted Table : \n" );
            List<TableDataElement> tableList= tableDataParser.getTableDataElements();

            for (int i=0 ; i < tableList.size() ; i++) {

                TableDataElement table=tableList.get(i);
                sb.append("Table " ).append(i+1).append(" -name (").append(table.getMetaId()).append(")").append("\n");
                List<Column> columnList=table.getColumns();

                for (int j=0 ; j < columnList.size() ; j++){

                    Column column=columnList.get(j);
                    sb.append("Column ").append(j+1).append(" -name (").append(column.getMetaId()).append(") : ");
                    List<Cell> cellList = column.getCellList();

                    for(int c=0 ; c < cellList.size() ; c++ ){

                        Cell cell=cellList.get(c);
                        sb.append(cell.getValue());
                        /* append ',' until the element before the last */
                        if(c != cellList.size()-1){
                            sb.append(" , ");
                        }
                    }
                sb.append("\n");
                }
            }
        }

        return sb.toString();
    }

    /* Removes the next line characters if the string has it in front of the line
    * [Because when extracting text with meta area , area which is empty adds a next line character
    * instead of adding a blank]
    * */
    public String replaceNextLineChar(String data){

        if(data.charAt(0) != 10){
            return data;
        }else {
            StringBuilder sb=new StringBuilder(data);
            sb.deleteCharAt(0);
            return replaceNextLineChar(sb.toString());
        }
    }

}
