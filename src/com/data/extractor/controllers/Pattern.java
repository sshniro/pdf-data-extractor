package com.data.extractor.controllers;

import com.data.extractor.model.beans.template.info.pattern.ColumnDataElement;
import com.data.extractor.model.beans.template.info.pattern.PatternDataElement;
import com.data.extractor.model.beans.template.info.regex.RegexDataElement;
import com.data.extractor.model.beans.template.info.table.Cell;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Pattern {

    // Extract text from PDF Document
    static StringBuilder pdftoText(String fileName) {
        PDFParser parser;
        String parsedText = null;;
        PDFTextStripper pdfStripper = null;
        PDDocument doc = null;
        COSDocument cosDoc = null;
        StringBuilder sb = new StringBuilder("");
        File file = new File(fileName);
        if (!file.isFile()) {
            System.err.println("File " + fileName + " does not exist.");
            return null;
        }
        try {
            parser = new PDFParser(new FileInputStream(file));
        } catch (IOException e) {
            System.err.println("Unable to open PDF Parser. " + e.getMessage());
            return null;
        }
        try {
            parser.parse();
            cosDoc = parser.getDocument();
            pdfStripper = new PDFTextStripper();
            doc = new PDDocument(cosDoc);
            int pageNumber = doc.getNumberOfPages();
            pdfStripper.setStartPage(1);
            pdfStripper.setEndPage(pageNumber);
            //parsedText = pdfStripper.getText(doc);
            sb.append(pdfStripper.getText(doc));
            //System.out.println(doc.getNumberOfPages());
        } catch (Exception e) {
            System.err
                    .println("An exception occured in parsing the PDF Document."
                            + e.getMessage());
        } finally {
            try {
                if (cosDoc != null)
                    cosDoc.close();
                if (doc != null)
                    doc.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sb;
    }
    public static void main(String args[]){

        StringBuilder sb=pdftoText("/Users/niro273/Desktop/test.pdf");
        String unprocessed = sb.toString();
        String processing = null;
        String[] splits = null;


        List<RegexDataElement> regexDataElementList = new ArrayList<RegexDataElement>();

        RegexDataElement r1 = new RegexDataElement();
        r1.setMetaName("Vendor Name");
        r1.setStartTag("Vendor Name:");
        r1.setEndTag("eol");

        regexDataElementList.add(r1);

        for(RegexDataElement r : regexDataElementList){
            try {
                splits = unprocessed.split(r.getStartTag(),2);
                if(r.getEndTag().equals("eol")){
                    splits = splits[1].split(System.getProperty("line.separator"),2);
                }else {
                    splits = splits[1].split(r.getEndTag(),2);
                }

                try {
                    r.setValue(splits[0]);
                }catch (ArrayIndexOutOfBoundsException e){
                    System.out.println("No match found for the end tag : " + r.getEndTag());
                }
            }catch (ArrayIndexOutOfBoundsException e){
                System.out.println("No match found for the start tag : "+ r.getStartTag());
            }
        }

        List<PatternDataElement> patternDataElementList = new ArrayList<PatternDataElement>();

        PatternDataElement p1 = new PatternDataElement();
        List<RegexDataElement> regexDataElementList1 = new ArrayList<RegexDataElement>();
        List<ColumnDataElement> columnDataElementList = new ArrayList<ColumnDataElement>();

        RegexDataElement r2= new RegexDataElement();
        r2.setStartTag("heelo");
        r2.setEndTag("");
        r2.setMetaName("");

        regexDataElementList1.add(r2);

        ColumnDataElement c1=new ColumnDataElement();

        c1.setColumnName("");
        c1.setColumnStartTag("");
        c1.setColumnEndTag("");

        ColumnDataElement c2 = new ColumnDataElement();

        c2.setColumnName("");
        c2.setColumnStartTag("");
        c2.setColumnEndTag("");

        columnDataElementList.add(c1);
        columnDataElementList.add(c2);

        List<List<Cell>> complexCellList = new ArrayList<List<Cell>>();
        List<Cell> cellList = new ArrayList<Cell>();

        for (ColumnDataElement c : columnDataElementList){

            splits = unprocessed.split(c.getColumnStartTag(),2);

            if(c.getColumnEndTag().equals("eol")){
                splits  = splits[1].split(System.getProperty("line.separator"),2);
            }else {
                splits  = splits[1].split(c.getColumnEndTag(),2);
            }

            splits[1] = c.getColumnEndTag() + splits[1];
            String cellValue = splits[0];


        }



//
//        List<TableDataBean> tableDataBeans = new ArrayList<TableDataBean>();
//
//        TableDataBean ta1=new TableDataBean();
//        ta1.setTableName("VSC Color Cde:");
//
//
//        List<ColumnDataElement> columnDataElementList = new ArrayList<ColumnDataElement>();
//
//        ColumnDataElement c1= new ColumnDataElement();
//        c1.setColumnName("VSC Color Cde");
//        c1.setColumnStartTag("VSC Color Cde:");
//        c1.setColumnEndTag("VSS Color Code:");
//
//
//        ColumnDataElement c2= new ColumnDataElement();
//        c2.setColumnName("Size");
//        c2.setColumnStartTag("Size:");
//        c2.setColumnEndTag("Qty Ordered:");
//
//        columnDataElementList.add(c1);
//        columnDataElementList.add(c2);
//
//        ta1.setColumnDataElementList(columnDataElementList);
//
//        tableDataBeans.add(ta1);
//
//        String table =null;
//        List<ColumnDataElement> columnDataElementList2 =null;
//        List<Cell> cellList=null;
//        for(TableDataBean ta : tableDataBeans){
//
//            columnDataElementList2 = ta.getColumnDataElementList();
//            for(ColumnDataElement c: columnDataElementList2){
//                cellList = new ArrayList<Cell>();
//                Cell cell;
//                /* c.startTag : column 1 -> splits[0] = xx + startTag , splits[1] = rest of the table [ROT] */
//                splits = unprocessed.split(c.getColumnStartTag(), 2);
//                try {
//                    /* splits[1] = c.startTag + ROT */
//                    //splits[1] =  splits [1];
//                    /* If this column is the last column */
//                    if(c.getColumnEndTag().equals("eol")){
//                        // Code to break the text when eol occurs
//                    }else {
//                        splits = splits[1].split(c.getColumnEndTag(),2);
//                        /* splits[1] = c.endTag + ROT */
//                        splits[1] = c.getColumnEndTag() + splits [1];
//                        cell = new Cell();
//                        cell.setValue(splits[0]);
//                        cellList.add(cell);
//                    }
//                }catch (ArrayIndexOutOfBoundsException e){
//                    /* No columns start tag found in the text */
//                    break;
//                }
//            }
//
//            splits = unprocessed.split(ta.getTableStartTag(), 2);
//            try{
//                table = ta.getTableStartTag() + splits[1];
//                splits = table.split(ta.getTableEndTag(),2);
//            }catch (ArrayIndexOutOfBoundsException e){
//                // No table with specified name exists in the text
//                e.printStackTrace();
//                break;
//            }


                

        }

    }



//}

//        PatternDataElement patternDataElement;
//
//        List<HeaderDataBean> headerDataBeans = new ArrayList<HeaderDataBean>();
//
//        HeaderDataBean h1 = new HeaderDataBean();
//        h1.setHeaderName("Vendor Name:");
//        h1.setStartTag("Vendor Name:");
//        h1.setEndTag("Address:");
//
//        HeaderDataBean h2 = new HeaderDataBean();
//        h2.setHeaderName("Address:");
//        h2.setStartTag("Address:");
//        h2.setEndTag("Ship To:");
//
//        headerDataBeans.add(h1);
//        headerDataBeans.add(h2);
//
//        for (HeaderDataBean h : headerDataBeans){
//            try {
//                splits = unprocessed.split(h.getStartTag(), 2);
//                processing =splits[1];
//                splits = processing.split(h.getEndTag(),2);
//                h.setValue(splits[0]);
//                System.out.println(h.getValue());
//            }catch (ArrayIndexOutOfBoundsException e){
//                e.printStackTrace();
//            }
//        }
