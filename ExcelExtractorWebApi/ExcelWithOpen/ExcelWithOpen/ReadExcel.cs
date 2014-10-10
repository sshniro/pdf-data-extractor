using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using DocumentFormat.OpenXml;
using DocumentFormat.OpenXml.Packaging;
using DocumentFormat.OpenXml.Spreadsheet;



namespace ExcelWithOpen
{
    class ReadExcel
    {
       public void test(string path)
       {
            var filePath = path;
            var document = SpreadsheetDocument.Open(filePath, false);

            var workbookPart = document.WorkbookPart;
            var workbook = workbookPart.Workbook;

            var sheets = workbook.Descendants<Sheet>();
            foreach (var sheet in sheets)
            {
                var worksheetPart = (WorksheetPart)workbookPart.GetPartById(sheet.Id);
                var sharedStringPart = workbookPart.SharedStringTablePart;
                var values = sharedStringPart;
                var lastCell = worksheetPart.Worksheet.Descendants<Cell>().LastOrDefault();
                //var values2 = sharedStringPart.SharedStringTable;
                //var values3 = sharedStringPart.SharedStringTable.Elements<SharedStringItem>();
                //var values4 = sharedStringPart.SharedStringTable.Elements<SharedStringItem>().ToArray();
               
                var cells = worksheetPart.Worksheet.Descendants<Cell>();
                foreach (var cell in cells)
                {
                    Console.WriteLine(cell.CellReference + " = " + cell.CellValue.Text);

                    // The cells contains a string input that is not a formula
                    if (cell.DataType != null && cell.DataType.Value == CellValues.SharedString)
                    {
                        var index = int.Parse(cell.CellValue.Text);
                        //var value = values4[index].InnerText;
                        //Console.WriteLine(value);
                    }
                    else
                    {
                        //Console.WriteLine(cell.CellValue.Text);
                    }

                    if (cell.CellFormula != null)
                    {
                        //Console.WriteLine(cell.CellFormula.Text);                    
                    }
                }
            
            }

            Console.ReadLine();
          
       }
    }
}
