package com.data.extractor.model.extract.pdf.table;


import com.data.extractor.model.beans.doc.text.positions.TextData;
import com.data.extractor.model.beans.extract.pdf.ExtractStatus;
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

/*
@Returns all the textData Data positions of the given page
 */
public class TextLocationRetriever extends PDFTextStripper {

    public static List<TextData> textDatas;

    public TextLocationRetriever() throws IOException
    {
        super.setSortByPosition( true );
    }

    public List<TextData> getTableData(ExtractStatus extractStatus,int pageNumber) throws IOException, CryptographyException {

        PDDocument document = null;
        textDatas=new ArrayList<TextData>();
        try
        {
            document = PDDocument.load( new File(extractStatus.getUploadedPdfFile()) );
            if( document.isEncrypted() )
            {
                document.decrypt( "" );
            }
            TextLocationRetriever printer = new TextLocationRetriever();
            List allPages = document.getDocumentCatalog().getAllPages();


                PDPage page = (PDPage)allPages.get( pageNumber );
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
        //System.out.println(textDatas.size());
        return textDatas;
    }

    /*
    This method acts as observer when extracting  text from a pdf
     */
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
    }

}
