using ClosedXML.Excel;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ExcelWithOpen
{
    class Program
    {
        static void Main(string[] args)

        {

            ReadExcel x = new ReadExcel();
            x.test("BasicTable.xlsx");
            
            var workbook = new XLWorkbook("BasicTable.xlsx");
            var ws = workbook.Worksheet(1);

            // Change the background color of the headers
            var rngHeaders = ws.Range("B3:F3");
            rngHeaders.Style.Fill.BackgroundColor = XLColor.LightSalmon;

            // Change the date formats
            var rngDates = ws.Range("E4:E6");
            rngDates.Style.DateFormat.Format = "MM/dd/yyyy";

            // Change the income values to text
            var rngNumbers = ws.Range("A1:G10");
            foreach (var cell in rngNumbers.Cells())
            {
                Console.WriteLine(cell.Value);
                cell.DataType = XLCellValues.Text;
                cell.Value += " Dollars";
            }

            workbook.SaveAs("BasicTable_Modified.xlsx");
        }
    }
}
