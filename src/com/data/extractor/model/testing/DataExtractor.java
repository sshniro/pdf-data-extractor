package com.data.extractor.model.testing;

import com.data.extractor.model.beans.template.info.image.ImageDataElement;
import com.data.extractor.model.beans.template.info.image.ImageDataParser;
import com.data.extractor.model.beans.template.info.insert.InsertDataParser;
import com.data.extractor.model.beans.template.info.pattern.ColumnDataElement;
import com.data.extractor.model.beans.template.info.pattern.PatternDataElement;
import com.data.extractor.model.beans.template.info.pattern.PatternDataParser;
import com.data.extractor.model.beans.template.info.regex.*;
import com.data.extractor.model.beans.template.info.table.Cell;
import com.data.extractor.model.beans.template.info.table.Column;
import com.data.extractor.model.beans.template.info.table.TableDataElement;
import com.data.extractor.model.beans.template.info.table.TableDataParser;
import com.data.extractor.model.beans.template.info.text.TextDataElement;
import com.data.extractor.model.beans.template.info.text.TextDataParser;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.ArrayList;
import java.util.List;

public class DataExtractor {

    public String convertToString(InsertDataParser data,Boolean isFromTemplateEdit){

        TextDataParser textDataParser = data.getTextDataParser();
        ImageDataParser imageDataParser = data.getImageDataParser();
        TableDataParser tableDataParser = data.getTableDataParser();
        RegexDataParser regexDataParser= data.getRegexDataParser();
        PatternDataParser patternDataParser= data.getPatternDataParser();

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
            String tableName;
            String columnName;

            for (int i=0 ; i < tableList.size() ; i++) {

                TableDataElement table=tableList.get(i);
                // set the tableName for east identification in the front end
                if(table.getMetaName() == null)
                    tableName = table.getMetaId();
                else
                    tableName = table.getMetaName();

                sb.append("Table " ).append(i+1).append(" -(").append(tableName).append(")").append("\n");
                List<Column> columnList=table.getColumns();

                for (int j=0 ; j < columnList.size() ; j++){

                    Column column=columnList.get(j);

                    // set the columnName for east identification in the front end
                    if(table.getMetaName() == null)
                        columnName = column.getMetaId();
                    else
                        columnName = column.getMetaName();

                    sb.append("Column ").append(j+1).append(" value - ").append(column.getExtractedText())
                    .append(" : ");

                    if(!isFromTemplateEdit){
                        List<Cell> cellList = column.getCellList();

                        for(int c=0 ; c < cellList.size() ; c++ ){

                            Cell cell=cellList.get(c);
                            sb.append(cell.getValue());
                        /* append ',' until the element before the last */
                            if(c != cellList.size()-1){
                                sb.append(" , ");
                            }
                        }
                    }else {
                        List<String> cellValues = column.getCellValues();

                        for (int c=0;c<cellValues.size();c++){
                            sb.append(cellValues.get(c));
                            if(c != cellValues.size()-1){
                                sb.append(" , ");
                            }
                        }
                    }


                sb.append("\n");
                }
            }
        }

        if(regexDataParser != null){
            List<RegexDataElement> regexDataElementList = regexDataParser.getRegexDataElements();

            for (RegexDataElement regexElement : regexDataElementList){
                List<RegexPairElement> regexPairElementList = regexElement.getRegexPairElements();

                for (RegexPairElement regexPair : regexPairElementList){
                    if(regexPair.getMetaName() == null){
                        regexPair.setMetaName("Text : ");
                    }
                    sb.append(regexPair.getMetaName()).append(" : " ).append(regexPair.getValue()).append("\n");
                }
            }
        }

        if(patternDataParser != null){
            String columnName;
            List<List<PatternDataElement>> complexList = patternDataParser.getComplexPatternList();

            for (int i=0;i<complexList.size();i++){
                List<PatternDataElement> patternDataElementList = complexList.get(i);
                sb.append("Table Collection " ).append(i+1).append(" : \n");

                for (int j=0 ;j<patternDataElementList.size();j++ ){

                    sb.append("Table " ).append(j+1).append(" : \n");

                    RegexDataElement regexDataElement= patternDataElementList.get(j).getRegexDataElements();
                    List<ColumnDataElement> columnDataElementList =patternDataElementList.get(j).getColumnDataElements();
                    List<RegexPairElement> regexPairElementList = regexDataElement.getRegexPairElements();



                    for (int k=0; k<regexPairElementList.size();k++){
                        RegexPairElement regexPairElement = regexPairElementList.get(k);
                        sb.append("Text " ).append(k+1).append(" : ").append(regexPairElement.getValue()).append("\n");
                    }

                    for (int k = 0; k < columnDataElementList.size();k++){

                        ColumnDataElement columnDataElement = columnDataElementList.get(k);


                        // set the columnName for east identification in the front end
                        if(columnDataElement.getMetaName() == null)
                            columnName = "column " +k;
                        else
                            columnName = columnDataElement.getMetaName();

                        sb.append("Column ").append(k+1).append(" -(").append(columnName).append(") : ");
                        List<Cell> cellList = columnDataElement.getCellList();

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


    public static List<List<String>> findSimilarities(TableDataParser tableDataParser){
        List<TableDataElement> tableDataElements= tableDataParser.getTableDataElements();
        List<String> metaNamesList = new ArrayList<String>();

        List<String> matchedNames = new ArrayList<String>();


        for (TableDataElement ta : tableDataElements){
            metaNamesList.add(new String(ta.getMetaName()));
        }

        for (String metaName : metaNamesList){
            if (metaNamesList.contains(metaName)){
                if(!matchedNames.contains(metaName)){
                    matchedNames.add(metaName);
                }
            }
        }

        List<List<String>> complex = new ArrayList<List<String>>();
        List<String> lessComplex;
        for (String name : matchedNames){
            lessComplex=new ArrayList<String>();
            for(TableDataElement ta : tableDataElements){
                if(ta.getMetaName().equals(name)){
                    lessComplex.add(ta.getMetaId());
                }
            }

            if(lessComplex.size() != 0){
                complex.add(lessComplex);
            }
        }

        return complex;
    }

}

