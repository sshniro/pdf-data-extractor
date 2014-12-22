//Generic Extraction function call to the back end
var ajaxExtract = function(urlData,sendData,asyncState,methodType){
    var extractedData;
    var sendData = JSON.stringify(sendData);
    $.ajax({
        url: urlData,
        data: sendData,
        type: methodType,
        async:asyncState,
        contentType: 'application/json; charset=utf-8',
        dataType: 'json'
    }).fail(function(jqXHR, textStatus, errorThrown) {
        extractedData="ajax extraction failed textStatus: "+textStatus.toString()+"  errorThrown: "+errorThrown.toString();
        console.log(jqXHR);
        console.log(extractedData);
    })
        .done(function(data) {
            extractedData =data;
        });
    return extractedData;
}

//Pulse Extraction Models

//First Pulse Extraction call method
//sneds coordinates of a reactangle and gets any data within it[picture(url), text]
var getMainExtraction =  function(rectangleObject, dataType){
    var ajaxReponse;
    switch (dataType){
        case 'text':
            var dataDTO = new TextDataDTO(initData);
            var textDataElement = new TextDataElementDTO(rectangleObject);
            dataDTO.textDataElements.push(textDataElement);
            //ajaxReponse = ajaxExtract('MarkUpTemplateRegionController',dataDTO,false,'POST');
            ajaxReponse = ajaxExtract(effectiveController,dataDTO,false,'POST');

            break;
        case 'table':
            var data = {};
            data.extractedData = "Pulse Extractions Not implemented for Table, Select all column headers and click on [Extract Table]";
            ajaxReponse = data;
            break;
        case 'picture':
            var dataDTO = new ImageDataDTO(initData);
            var textDataElement = new ImageDataElementDTO(rectangleObject);
            dataDTO.imageDataElements.push(textDataElement);
            //ajaxReponse = ajaxExtract('MarkUpTemplateRegionController',dataDTO,false,'POST');
            ajaxReponse = ajaxExtract(effectiveController,dataDTO,false,'POST');
            var imageRelativePath = ajaxReponse.extractedData;
            imageRelativePath = imageRelativePath.split("\\").join("/");
            ajaxReponse.extractedData = imageRelativePath;
            ///Transform to proper relative path format
            break;
    }
    return ajaxReponse;
}

//Specially meant for text extraction where the label dat has to be removed
var getSubExtraction=  function(rectangleObject, dataType){
    var ajaxReponse;
    var bufferedElement = ko.utils.unwrapObservable(vm.elementBuffer);
    switch (dataType){
        case 'text':
            var dataDTO = new TextDataDTO(initData);
            var textDataElement = new TextDataElementDTO(bufferedElement,rectangleObject);
            dataDTO.textDataElements.push(textDataElement);
            //ajaxReponse = ajaxExtract('MarkUpTemplateRegionController',dataDTO,false,'POST');
            ajaxReponse = ajaxExtract(effectiveController,dataDTO,false,'POST');

            break;
        case 'table':
            var data = {};
            data.extractedData = "Table Sub Extraction Not Implemented";
            ajaxReponse = data;

            break;
        case 'picture':
            var data = {};
            data.extractedData = "Picture Sub Extraction Not Implemented";
            ajaxReponse = data;
            break;
    }
    return ajaxReponse;
}

//gets a table extraction preview since pulse extraction is not supported
//This is called with an object that has the total table rectabgle coordinates and an array of column header coordinates
var getTableExtraction =  function(tableDataElement){
    var ajaxReponse;
    var dataDTO = new TableDataDTO(initData);
    dataDTO.status = 'extract';
    var tableDataElementDTO = new TableDataElementDTO(tableDataElement,tableDataElement.subElements);
    dataDTO.tableDataElements.push(tableDataElementDTO);
    //ajaxReponse = ajaxExtract('MarkUpTemplateRegionController',dataDTO,false,'POST');
    ajaxReponse = ajaxExtract(effectiveController,dataDTO,false,'POST');
    return ajaxReponse;
}

//Final saving of the whole Template to the DB
var sendBulkData=  function(sendingJSON){
    var finalDataDTO = {};
    //var sendingJSON = JSON.parse(sendingJSONString);
    finalDataDTO.status = 'insert';
    finalDataDTO.textDataParser     =   transformToTextParser(sendingJSON.textDataElements);
    finalDataDTO.tableDataParser    =   transformToTableParser(sendingJSON.tableDataElements);
    finalDataDTO.imageDataParser    =   transformToImageParser(sendingJSON.pictureDataELements);
    //ajaxReponse = ajaxExtract('MarkUpTemplateRegionController',finalDataDTO,false,'POST');
    ajaxReponse = ajaxExtract(effectiveController,finalDataDTO,false,'POST');
    return finalDataDTO;
}

//Final Data Transformers
var transformToTextParser = function(textDataElements){
    var dataDTO = new TextDataDTO(initData);
    dataDTO.status = 'insert';
    for(var key in textDataElements) {
        var dataElement = textDataElements[key];
        var metaElement = dataElement.subElements[0];///Since text will have only one sub element
        var textDataElement = new TextDataElementDTO(dataElement,metaElement);
        dataDTO.textDataElements.push(textDataElement);
    }
    return dataDTO;
}
var  transformToImageParser= function(imageDataElements){
    var dataDTO = new ImageDataDTO(initData);
    dataDTO.status = 'insert';
    for(var key in imageDataElements) {
        var dataElement = imageDataElements[key];
        var metaElement = undefined;///Since image will have no sub element
        var imageDataElement = new ImageDataElementDTO(dataElement,metaElement);
        dataDTO.imageDataElements.push(imageDataElement);
    }
    return dataDTO;
}
var  transformToTableParser= function(tableDataElements){
    var dataDTO = new TableDataDTO(initData);
    dataDTO.status = 'insert';
    for(var key in tableDataElements) {
        var dataElement = tableDataElements[key];
        var metaElements = tableDataElements[key].subElements;
        var tableDataElement = new TableDataElementDTO(dataElement,metaElements);
        dataDTO.tableDataElements.push(tableDataElement);
    }
    return dataDTO;
}



///Message Broker Models
//Total Text Data Object containing arrays of text elements
function TextDataDTO(pageData){
    this.id = pageData.id;
    this.templateName       = pageData.templateName;
    this.status             = "extract";///Static Data
    this.dataType           = "text";////Static Data
    this.textDataElements=[];
}
//A single text element
function TextDataElementDTO(dataElement, metaElement){
    this.rawData = dataElement.rectangle;
    this.metaId         = ko.utils.unwrapObservable(dataElement.id);    //////Switched meta with id
    this.metaName      = ko.utils.unwrapObservable(dataElement.metaName);//// Switch due to data layer requirement
    if(dataElement.dictionaryObject){
        this.dictionaryId   = ko.utils.unwrapObservable(dataElement.dictionaryObject.id);
        this.dictionaryName   = ko.utils.unwrapObservable(dataElement.dictionaryObject.dictionaryName);
    }
    else{
        this.dictionaryId   = -1;
        this.dictionaryName   =  -1;
    }
    this.totalX1        =ko.utils.unwrapObservable(dataElement.startX);
    this.totalY1        =ko.utils.unwrapObservable(dataElement.startY);
    this.totalWidth     = this.totalX1 + ko.utils.unwrapObservable(dataElement.width);
    this.totalHeight    = this.totalY1 + ko.utils.unwrapObservable(dataElement.height);
    this.pageNumber     = dataElement.pageNumber;/////////???????GetFromCookie

    this.metaX1 =-1;
    this.metaY1 =-1;
    this.metaWidth =-1;
    this.metaHeight =-1;
    this.metaRawData = new RawDataSkeleton();
    this.metaAvailable;
    var self = this;
    if(metaElement !== undefined){

        self.metaRawData =   metaElement.rectangle;
        self.metaX1      =  self.totalX1 + ko.utils.unwrapObservable(metaElement.startX);
        self.metaY1      =  self.totalY1 + ko.utils.unwrapObservable(metaElement.startY);
        self.metaWidth   =  self.metaX1 +  ko.utils.unwrapObservable(metaElement.width);
        self.metaHeight  =  self.metaY1 + ko.utils.unwrapObservable(metaElement.height);
        self.metaAvailable = true;
    }

}

//Total image data object containing arrays of image data elements
function ImageDataDTO(pageData){
    this.id = pageData.id;
    this.status= "extract";///Static Data
    this.dataType= "image";////Static Data
    this.imageDataElements=[];
}
//A single image data element
function ImageDataElementDTO(dataElement, metaElement){
    this.rawData = dataElement.rectangle;
    this.metaId = ko.utils.unwrapObservable(dataElement.id);
    this.metaName      = ko.utils.unwrapObservable(dataElement.metaName);//// Switch due to data layer requirement
    if(dataElement.dictionaryObject){
        this.dictionaryId   = ko.utils.unwrapObservable(dataElement.dictionaryObject.id);
        this.dictionaryName   = ko.utils.unwrapObservable(dataElement.dictionaryObject.dictionaryName);
    }
    else{
        this.dictionaryId   = -1;
        this.dictionaryName   =  -1;
    }
    this.totalX1 =ko.utils.unwrapObservable(dataElement.startX);
    this.totalY1 =ko.utils.unwrapObservable(dataElement.startY);
    this.totalWidth = this.totalX1 + ko.utils.unwrapObservable(dataElement.width);
    this.totalHeight = this.totalY1 + ko.utils.unwrapObservable(dataElement.height);
    this.pageNumber  = dataElement.pageNumber;/////////???????GetFromCookie
    this.metaX1 =-1;
    this.metaY1 =-1;
    this.metaWidth =-1;
    this.metaHeight =-1;
    this.metaRawData = new RawDataSkeleton();
    if(metaElement !== undefined){
        this.metaRawData    =   metaElement.rectangle;
        this.metaX1         =   this.totalX1 + ko.utils.unwrapObservable(metaElement.startX);
        this.metaY1         =   this.totalY1 + ko.utils.unwrapObservable(metaElement.startY);
        this.metaWidth      =   this.metaX1 +  ko.utils.unwrapObservable(metaElement.width);
        this.metaHeight     =   this.metaY1 + ko.utils.unwrapObservable(metaElement.height);
    }

}


//Total table DTO containing arrays of table elements
function TableDataDTO(pageData){
    this.id = pageData.id;
    this.status= "extract";///Static Data
    this.dataType= "table";////Static Data
    this.tableDataElements=[];
}
//a single table element
function TableDataElementDTO(dataElement, metaElements){
    this.rawData = dataElement.rectangle;
    this.metaId         = ko.utils.unwrapObservable(dataElement.id);    //////Switched meta with id
    this.metaName      = ko.utils.unwrapObservable(dataElement.metaName);//// Switch due to data layer requirement
    if(dataElement.dictionaryObject){
        this.dictionaryId   = ko.utils.unwrapObservable(dataElement.dictionaryObject.id);
        this.dictionaryName   = ko.utils.unwrapObservable(dataElement.dictionaryObject.dictionaryName);
    }
    else{
        this.dictionaryId   = -1;
        this.dictionaryName   =  -1;
    }
    this.totalX1        =ko.utils.unwrapObservable(dataElement.startX);
    this.totalY1        =ko.utils.unwrapObservable(dataElement.startY);
    this.totalWidth     = this.totalX1 + ko.utils.unwrapObservable(dataElement.width);
    this.totalHeight    = this.totalY1 + ko.utils.unwrapObservable(dataElement.height);
    this.pageNumber     = dataElement.pageNumber;
    this.columns = [];
    for(var key in metaElements) {
        var metaElement = metaElements[key];
        var tableDataColumn = new TableDataColumnDTO(dataElement,metaElement);
        this.columns.push(tableDataColumn);
    }



}
// a single column element in the table
function TableDataColumnDTO(dataElement, metaElement){
    this.rawData        =   metaElement.rectangle;
    this.metaId         =   metaElement.id;    //////Switched meta with id
    this.metaName      = ko.utils.unwrapObservable(dataElement.metaName);//// Switch due to data layer requirement
    if(dataElement.dictionaryObject){
        this.dictionaryId   = ko.utils.unwrapObservable(metaElement.dictionaryObject.id);
        this.dictionaryName   = ko.utils.unwrapObservable(metaElement.dictionaryObject.dictionaryName);
    }
    else{
        this.dictionaryId   = -1;
        this.dictionaryName   =  -1;
    }
    this.metaX1         =   dataElement.startX + (metaElement.startX);
    this.metaY1         =   dataElement.startY + (metaElement.startY);
    this.metaWidth      =   this.metaX1 + (metaElement.width);
    this.metaHeight     =   this.metaY1 + (metaElement.height);
    //

}

