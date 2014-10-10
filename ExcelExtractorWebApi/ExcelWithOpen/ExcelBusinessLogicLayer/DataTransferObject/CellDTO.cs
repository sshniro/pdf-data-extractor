using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ExcelBusinessLogicLayer.DataTransferObject
{
    public class CellDTO
    {
        public string CellAddress { get; set; }
        public string Value { get; set; }

        public string Formula { get; set; }

        public CellFormattingDTO Formatting { get; set; }

    }
}
