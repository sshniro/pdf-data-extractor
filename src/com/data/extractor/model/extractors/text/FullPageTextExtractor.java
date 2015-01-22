package com.data.extractor.model.extractors.text;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FullPageTextExtractor {

    public StringBuilder pdftoText(PDDocument doc,int startPage , int endPage) {
        PDFParser parser;
        String parsedText = null;;
        PDFTextStripper pdfStripper = null;
        COSDocument cosDoc = null;
        StringBuilder sb = new StringBuilder("");
        try {
            pdfStripper = new PDFTextStripper();
            pdfStripper.setStartPage(startPage);
            pdfStripper.setEndPage(endPage);
            sb.append(pdfStripper.getText(doc));
        } catch (Exception e) {
            System.err.println("An exception occured in parsing the PDF Document."
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
}
