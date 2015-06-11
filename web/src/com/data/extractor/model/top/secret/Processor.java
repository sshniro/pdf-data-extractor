package com.data.extractor.model.top.secret;


import com.data.extractor.model.beans.doc.text.positions.TextData;
import com.data.extractor.model.beans.extract.pdf.ExtractStatus;
import com.data.extractor.model.beans.template.info.table.Cell;
import com.data.extractor.model.beans.template.info.table.Column;
import com.data.extractor.model.beans.template.info.table.TableDataElement;
import com.data.extractor.model.beans.template.info.table.TableDataParser;
import com.mongodb.MongoClient;
import org.apache.pdfbox.exceptions.CryptographyException;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Processor {

    public static void main(String args[]) throws IOException, CryptographyException {

        ExtractStatus extractStatus=new ExtractStatus();
        //extractStatus.setMainCategory("Sales Order");
        //extractStatus.setSubCategory("Supplier 10");
        //extractStatus.setTemplateName("template1");
        extractStatus.setDocumentId("testing one");


        /* Get the mongo client from the servletContext */
        MongoClient mongoClient = new MongoClient("localhost",27017);

        TableDataParser tableDataParser;
        TableDataRetriever retriever=new TableDataRetriever();
        tableDataParser=retriever.retrieveTableData(extractStatus,mongoClient);

        List<TableDataElement> tableDataElements;
        tableDataElements=tableDataParser.getTableDataElements();

        Cell cell;
        List<Cell> cells;
        Boolean found = true;

        for(TableDataElement ta:tableDataElements) {
            //tableDataElement = tableDataElements.get(0);

            List<Column> columns;
            columns = ta.getColumns();
            TextLocationRetreiver retreiver = new TextLocationRetreiver();
            List<TextData> textDatas = retreiver.processPdf(ta.getPageNumber()-1);

            StringBuilder testSb = new StringBuilder();

            cell=new Cell();
            cells=new ArrayList<Cell>();

            // run for each loop for the columns
            for (Column c : columns) {

                //Where the X extraction should take place
                Double startX = c.getMetaX1();
                //Where the Y extraction should take place
                Double startY = c.getMetaY1() + c.getMetaHeight();
                //Where the X extraction should end
                Double endX = c.getMetaX1() + c.getMetaWidth();
                //Where the Y extraction should end
                Double endY = ta.getTotalY1() + ta.getTotalHeight();

                // For each loop for all the textdata retreived from processing the PDF
                for (TextData te : textDatas) {

                    if (startX < te.getxDirAdj() && startY < te.getyDirAdj() && te.getyDirAdj() < endY) {

                        if (endX > te.getxDirAdj()) {

                            if (!found) {
                                // Check if this is needed
                                cell = new Cell();
                                cell.setValue(testSb.toString());
                                cells.add(cell);

                                testSb = new StringBuilder();
                                found = true;
                            }
                            testSb.append(te.getCharacter());
                        } else {
                            found = false;
                        }
                    }
                }

                if (!found) {
                    cell = new Cell();
                    cell.setValue(testSb.toString());
                    cells.add(cell);

                    testSb = new StringBuilder();
                    found = true;
                }
                if (cell.getValue().length() != 0) {
                    c.setCellList(cells);
                    cells = new ArrayList<Cell>();
                }


            }
        }
        ExtractedTableInserter inserter=new ExtractedTableInserter();
        inserter.insert(tableDataParser,extractStatus,mongoClient);

    }
}
