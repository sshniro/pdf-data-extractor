using DocumentFormat.OpenXml;
using DocumentFormat.OpenXml.Packaging;
using DocumentFormat.OpenXml.Spreadsheet;
using ExcelBusinessLogicLayer.DataTransferObject;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ExcelBusinessLogicLayer
{
    public class ExcelExtractor
    {
        public Collection<SheetDTO> extractExcelToCellDtos(string path)
        {
            Collection<SheetDTO> rtnCollection = new Collection<SheetDTO>();

            var filePath = path;
            var document = SpreadsheetDocument.Open(filePath, false);

            var workbookPart = document.WorkbookPart;
            var workbook = workbookPart.Workbook;
            var sheets = workbook.Descendants<Sheet>();

            foreach (var sheet in sheets)
            {
                SheetDTO sheetDTO = new SheetDTO();
                sheetDTO.Cells = new Collection<CellDTO>();
                sheetDTO.SheetId = sheet.Id;
                sheetDTO.SheetName = sheet.Name;



                var worksheetPart   = (WorksheetPart)workbookPart.GetPartById(sheet.Id);
                var sharedStringPart = workbookPart.SharedStringTablePart;
                var values = sharedStringPart;
                var lastCell = worksheetPart.Worksheet.Descendants<Cell>().LastOrDefault();
                //var values2 = sharedStringPart.SharedStringTable;
                //var values3 = sharedStringPart.SharedStringTable.Elements<SharedStringItem>();
                //var values4 = sharedStringPart.SharedStringTable.Elements<SharedStringItem>().ToArray();

                var cells = worksheetPart.Worksheet.Descendants<Cell>();
                foreach (var cell in cells)
                {
                    CellDTO cellDTO = new CellDTO();
                    cellDTO.CellAddress = cell.CellReference;
                    cellDTO.Value = cell.CellValue != null ? cell.CellValue.Text : null;
                    cellDTO.Formula = cell.CellFormula != null ? cell.CellFormula.Text : null;
                    sheetDTO.Cells.Add(cellDTO);
                }

                rtnCollection.Add(sheetDTO);

            }


            return rtnCollection;
        }
    }
}
