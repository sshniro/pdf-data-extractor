package com.data.extractor.model.top.secret;


import com.data.extractor.model.beans.doc.text.positions.TextData;
import org.apache.pdfbox.exceptions.CryptographyException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.pdfbox.util.TextPosition;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TextLocationRetreiver extends PDFTextStripper {

    public static List<TextData> textDatas;

    public TextLocationRetreiver() throws IOException
    {
        super.setSortByPosition( true );
    }

    public List<TextData> processPdf(int pageNumber) throws IOException, CryptographyException {
        PDDocument document = null;
        textDatas=new ArrayList<TextData>();
        try
        {
            document = PDDocument.load( new File("/home/niro273/brandix_doc_mining/documents/complex.pdf") );
            if( document.isEncrypted() )
            {
                document.decrypt( "" );
            }
            TextLocationRetreiver printer = new TextLocationRetreiver();
            List allPages = document.getDocumentCatalog().getAllPages();


                PDPage page = (PDPage)allPages.get( pageNumber );
                System.out.println( "Processing page: " + pageNumber );
                PDStream contents = page.getContents();
                if( contents != null )
                {
                    printer.processStream( page, page.findResources(), page.getContents().getStream() );
                }

        }
        finally
        {
            if( document != null )
            {
                document.close();
            }
        }
        System.out.println(textDatas.size());
        return textDatas;
    }

    protected void processTextPosition( TextPosition text )
    {

        TextData textData=new TextData();
        textData.setCharacter(text.getCharacter());
        textData.setxDirAdj(text.getXDirAdj());
        textData.setyDirAdj(text.getYDirAdj());
        textData.setFontSize(text.getFontSize());
        textData.setxScale(text.getXScale());
        textData.setHeightDir(text.getHeightDir());
        textData.setWidthOfSpace(text.getWidthOfSpace());
        textData.setWidthDirAdj(text.getWidthDirAdj());

        textDatas.add(textData);

        System.out.println( "String[" + text.getXDirAdj() + "," +
                text.getYDirAdj() + " fs=" + text.getFontSize() + " xscale=" +
                text.getXScale() + " height=" + text.getHeightDir() + " space=" +
                text.getWidthOfSpace() + " width=" +
                text.getWidthDirAdj() + "]" + text.getCharacter() );


    }

}
