

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
var getMainExtraction =  function(rectangleObject, dataType){
	var ajaxReponse;
	switch (dataType){
		case 'text':
			var dataDTO = new TextDataDTO(initData);
			var textDataElement = new TextDataElementDTO(rectangleObject);
			dataDTO.textDataElements.push(textDataElement);
			ajaxReponse = ajaxExtract('MarkUpTemplateRegionController',dataDTO,false,'POST');

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
            ajaxReponse = ajaxExtract('MarkUpTemplateRegionController',dataDTO,false,'POST');
            var imageRelativePath = ajaxReponse.extractedData;
            imageRelativePath = imageRelativePath.split("\\").join("/");
            ajaxReponse.extractedData = imageRelativePath;
            ///Transform to proper relative path format
			break;						
	}
	return ajaxReponse;
}

var getSubExtraction=  function(rectangleObject, dataType){
	var ajaxReponse; 
	var bufferedElement = ko.utils.unwrapObservable(vm.elementBuffer);
	switch (dataType){
		case 'text':
			var dataDTO = new TextDataDTO(initData);
			var textDataElement = new TextDataElementDTO(bufferedElement,rectangleObject);
			dataDTO.textDataElements.push(textDataElement);
			ajaxReponse = ajaxExtract('MarkUpTemplateRegionController',dataDTO,false,'POST');

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

var getTableExtraction =  function(tableDataElement){
    var ajaxReponse;
    var dataDTO = new TableDataDTO(initData);
    dataDTO.status = 'extract';
    var tableDataElementDTO = new TableDataElementDTO(tableDataElement,tableDataElement.subElements);
    dataDTO.tableDataElements.push(tableDataElementDTO);
    ajaxReponse = ajaxExtract('MarkUpTemplateRegionController',dataDTO,false,'POST');
    return ajaxReponse;
}


var sendBulkData=  function(sendingJSON){
    var finalDataDTO = {};
    //var sendingJSON = JSON.parse(sendingJSONString);
    finalDataDTO.status = 'insert';
    finalDataDTO.textDataParser     =   transformToTextParser(sendingJSON.textDataElements);
    finalDataDTO.tableDataParser    =   transformToTableParser(sendingJSON.tableDataElements);
    finalDataDTO.imageDataParser    =   transformToImageParser(sendingJSON.pictureDataELements);
    ajaxReponse = ajaxExtract('MarkUpTemplateRegionController',finalDataDTO,false,'POST');
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
function TextDataDTO(pageData){
    this.mainCategory       = pageData.mainCategory;
    this.subCategory        = pageData.subCategory;
    this.templateName       = pageData.templateName;
    this.status             = "extract";///Static Data
    this.dataType           = "text";////Static Data
    this.textDataElements=[];
}
function TextDataElementDTO(dataElement, metaElement){
    this.metaId         = ko.utils.unwrapObservable(dataElement.id);    //////Switched meta with id
    this.elementId      = ko.utils.unwrapObservable(dataElement.metaName);//// Switch due to data layer requirement
    this.totalX1        =ko.utils.unwrapObservable(dataElement.startX);
    this.totalY1        =ko.utils.unwrapObservable(dataElement.startY);
    this.totalWidth     = this.totalX1 + ko.utils.unwrapObservable(dataElement.width);
    this.totalHeight    = this.totalY1 + ko.utils.unwrapObservable(dataElement.height);
    this.pageNumber     = dataElement.pageNumber;/////////???????GetFromCookie

    this.metaX1 =-1;
    this.metaY1 =-1; 
    this.metaWidth =-1;   
    this.metaHeight =-1;
    this.metaAvailable;
    var self = this;
    if(metaElement !== undefined){
	    self.metaX1      =  self.totalX1 + ko.utils.unwrapObservable(metaElement.startX);
	    self.metaY1      =  self.totalY1 + ko.utils.unwrapObservable(metaElement.startY);
	    self.metaWidth   =  self.metaX1 +  ko.utils.unwrapObservable(metaElement.width);
	    self.metaHeight  =  self.metaY1 + ko.utils.unwrapObservable(metaElement.height);
        self.metaAvailable = true;
    }

 }


function ImageDataDTO(pageData){
    this.mainCategory       = pageData.mainCategory;
    this.subCategory        = pageData.subCategory;
    this.templateName       = pageData.templateName;
    this.status= "extract";///Static Data
    this.dataType= "image";////Static Data
    this.imageDataElements=[];
}

function ImageDataElementDTO(dataElement, metaElement){
    this.metaId = ko.utils.unwrapObservable(dataElement.id);
    this.totalX1 =ko.utils.unwrapObservable(dataElement.startX);
    this.totalY1 =ko.utils.unwrapObservable(dataElement.startY);
    this.totalWidth = this.totalX1 + ko.utils.unwrapObservable(dataElement.width);
    this.totalHeight = this.totalY1 + ko.utils.unwrapObservable(dataElement.height);
    this.pageNumber  = dataElement.pageNumber;/////////???????GetFromCookie
    this.metaX1 =-1;
    this.metaY1 =-1;
    this.metaWidth =-1;
    this.metaHeight =-1;

    if(metaElement !== undefined){
        this.metaX1 =this.totalX1 + ko.utils.unwrapObservable(metaElement.startX);
        this.metaY1 =this.totalY1 + ko.utils.unwrapObservable(metaElement.startY);
        this.metaWidth =this.metaX1 +  ko.utils.unwrapObservable(metaElement.width);
        this.metaHeight = this.metaY1 + ko.utils.unwrapObservable(metaElement.height);
    }

}


function TableDataDTO(pageData){
    this.mainCategory       = pageData.mainCategory;
    this.subCategory        = pageData.subCategory;
    this.templateName       = pageData.templateName;
    this.status= "extract";///Static Data
    this.dataType= "table";////Static Data
    this.tableDataElements=[];
}

function TableDataElementDTO(dataElement, metaElements){
    this.metaId         = ko.utils.unwrapObservable(dataElement.id);    //////Switched meta with id
    this.elementId      = ko.utils.unwrapObservable(dataElement.metaName);//// Switch due to data layer requirement
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

function TableDataColumnDTO(dataElement, metaElement){
    this.metaId         =   metaElement.id;    //////Switched meta with id
    this.metaX1         =   dataElement.startX + (metaElement.startX);
    this.metaY1         =   dataElement.startY + (metaElement.startY);
    this.metaWidth      =   this.metaX1 + (metaElement.width);
    this.metaHeight     =   this.metaY1 + (metaElement.height);

}

