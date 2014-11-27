
function Cell(data) {
    this.CellAddress    = data.CellAddress;
    this.Value          = data.Value;
    this.Formula        = data.Formula;
    this.Formatting     = data.Formatting;
}

function Sheet(data) {
    this.SheetId   = data.SheetId;
    this.SheetName = data.SheetName;
    //this.Cells     = $.map(data.Cells)data.Cells;
}

function Formatting(data) {
    this.BackColour   = data.BackColour;
}