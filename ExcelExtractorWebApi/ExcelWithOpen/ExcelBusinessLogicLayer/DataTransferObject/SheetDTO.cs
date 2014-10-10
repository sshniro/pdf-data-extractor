using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ExcelBusinessLogicLayer.DataTransferObject
{
    public class SheetDTO
    {
        public string SheetId { get; set; }
        public string SheetName { get; set; }

        public Collection<CellDTO> Cells { get; set; }
    }
}
