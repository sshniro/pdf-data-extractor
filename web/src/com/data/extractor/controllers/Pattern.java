package com.data.extractor.controllers;

import com.data.extractor.model.beans.template.info.pattern.PatternDataElement;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by niro273 on 1/15/15.
 */
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

        PatternDataElement patternDataElement;

        List<HeaderDataBean> headerDataBeans = new ArrayList<HeaderDataBean>();

        HeaderDataBean h1 = new HeaderDataBean();
        h1.setHeaderName("Vendor Name:");
        h1.setStartTag("Vendor Name:");
        h1.setEndTag("Address:");

        HeaderDataBean h2 = new HeaderDataBean();
        h2.setHeaderName("Address:");
        h2.setStartTag("Address:");
        h2.setEndTag("Ship To:");

        headerDataBeans.add(h1);
        headerDataBeans.add(h2);

        for (HeaderDataBean h : headerDataBeans){
            try {
                splits = unprocessed.split(h.getStartTag(), 2);
                processing =splits[1];
                splits = processing.split(h.getEndTag(),2);
                h.setValue(splits[0]);
                System.out.println(h.getValue());
            }catch (ArrayIndexOutOfBoundsException e){
                e.printStackTrace();
            }
        }




    }



}
