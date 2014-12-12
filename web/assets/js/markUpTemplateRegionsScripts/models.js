//Status Variable
//Global Variables
//Starting pixel coordinates of the ui component drawn upon
var uiCompStartingX;
var uiCompStartingY;

//current imgareSelection instance
var selectedImgAreInstance = undefined;


//Bufers
var currentElement = undefined;

function RawDataSkeleton() {
    this.baseUiComponentStartX     =-1;
    this.baseUiComponentStartY     =-1;
    this.baseUiComponentHeight     =-1;
    this.baseUiComponentWidth      =-1;

    this.startX                    =-1;
    this.startY                    =-1;
    this.width                     =-1;
    this.height                    =-1;

    this.id                        = -1;
    this.elementType               = -1;
    this.page                      = -1;
}

function Page(data) {
    this.pageNumber         = ko.observable(data.pageNumber);
    this.imagePath          = ko.observable(data.imagePath);
    this.pageId             = ko.observable('Page'+this.pageNumber().toString());
    this.pageIdLi            = ko.observable('PageLi'+this.pageNumber().toString());
    this.pageIdAn           = ko.observable('PageAn'+this.pageNumber().toString());
    this.pageNumberHref     = ko.observable('#Page' + this.pageNumber().toString());
    this.pageNumberName     = ko.observable('Page '+this.pageNumber().toString());
    this.activeStatus       = ko.observable(data.activeStatus);


}

function PageCache(pageNumber) {
    this.pageNumber = pageNumber;
    this.selectionInProgress = false;
    this.subElementSelectionInProgress = false;
    this.currentSelection = undefined;
    this.textElements=[];
    this.tableElements=[];
    this.pictureElements=[];
}

// Models
function DataElement(rectangle){
    var self = this;
    self.rectangle = rectangle;
    self.id = ko.observable(rectangle.id);
    self.name = ko.observable();
    self.elementId  = self.id();

    self.elementType = ko.observable(rectangle.elementType);

    self.startX = ko.observable(rectangle.startX);
    self.startY= ko.observable(rectangle.startY);
    self.width = ko.observable(rectangle.width);
    self.height = ko.observable(rectangle.height);
    self.pageNumber = ko.observable(rectangle.pageNumber);



    self.extractedData = ko.observable(rectangle.extractedData);
    self.labelExtractedData = ko.observable(rectangle.labelExtractedData);
    //Change at message broker to meta id
    self.metaName = ko.observable(rectangle.metaName);
    self.elementClass = ko.observable('main');

    self.relevantData = ko.observable(rectangle.relevantData);
    if(rectangle.relevantData === undefined ) {
        self.relevantData = ko.observable("Select Label Element");
    }


    self.subElements = ko.observableArray([rectangle.subElements]);

    if(rectangle.subElements === undefined ) {
        self.subElements = ko.observableArray([]);
    }
    else{
        self.tempSubElements = $.map( rectangle.subElements, function(subElement) { return new SubDataElement(subElement) });
        self.subElements = ko.observableArray(self.tempSubElements);

    }

    self.uiData = new UiData(rectangle);
    
}


//Coordinates Relative to parent ELement!!!!
function SubDataElement(rectangle){
    var self = this;
    self.rectangle = rectangle;
    self.elementId  = ko.observable(rectangle.elementId);
    self.id = ko.observable(rectangle.id);
    self.elementType = ko.observable(rectangle.elementType);
    self.metaName = ko.observable();

    self.startX = ko.observable(rectangle.startX);
    self.startY= ko.observable(rectangle.startY);
    self.width = ko.observable(rectangle.width);
    self.height = ko.observable(rectangle.height);

    self.extractedData = ko.observable(rectangle.extractedData);
    self.relevantData = ko.observable(rectangle.relevantData);

    self.elementClass = ko.observable('sub');

    //Fix for when reading from cache
    if(rectangle.uiData === undefined){
        self.uiData = new SubUiData(rectangle);
    }
    else {
        self.uiData = new SubUiData(rectangle.uiData);
    }

}

function UiData(rectangle){
    var self = this;
    self.baseUiComponentStartX = ko.observable(rectangle.baseUiComponentStartX);
    self.baseUiComponentStartY = ko.observable(rectangle.baseUiComponentStartY);
    self.baseUiComponentHeight = ko.observable(rectangle.baseUiComponentHeight);
    self.baseUiComponentWidth = ko.observable(rectangle.baseUiComponentWidth);
    self.startX =  rectangle.startX;
    self.startY =  rectangle.startY;
    self.width = rectangle.width;
    self.height = rectangle.height;

    self.metaStartY = ko.computed(function(){
        return self.baseUiComponentStartY() + self.startY - 85
    });

    self.metaStartX = ko.computed(function(){
        return self.baseUiComponentStartX() + self.startX
    });


    self.removeX = ko.computed(function(){
        return self.width - 14
    });


    self.removeY = ko.computed(function(){
        return 0;
        //margin inside di
    });

    self.rectY = ko.computed(function(){
        return self.baseUiComponentStartY() + self.startY
    });
    self.rectX = ko.computed(function(){
        return self.baseUiComponentStartX() + self.startX
    });

    self.extractedY = ko.computed(function(){
        return self.baseUiComponentStartY() + self.startY + self.height + 2
    });
    self.extractedX = ko.computed(function(){
        return self.baseUiComponentStartX() + self.startX
    });

    self.elementMap = ko.computed(function(){
        var styleObject = {
            left    : self.rectX(),
            top     : self.rectY(),
            width   : self.width ,
            height  : self.height
        }
        return styleObject;
    });

}
function SubUiData(rectangle){
    var self = this;
    self.baseUiComponentStartX = ko.observable(rectangle.baseUiComponentStartX);
    self.baseUiComponentStartY = ko.observable(rectangle.baseUiComponentStartY);
    self.baseUiComponentHeight = ko.observable(rectangle.baseUiComponentHeight);
    self.baseUiComponentWidth = ko.observable(rectangle.baseUiComponentWidth);
    self.startX =  rectangle.startX;
    self.startY =  rectangle.startY;
    self.width = rectangle.width;
    self.height = rectangle.height;
    self.id = rectangle.id;

    self.metaStartY = ko.computed(function(){
        return self.startY - 97
    });

    self.metaStartX = ko.computed(function(){
        return self.startX
    });


    self.removeX = ko.computed(function(){
        return self.startX + self.width - 14
    });


    self.removeY = ko.computed(function(){
        return self.startY
        //margin inside di
    });

    self.rectY = ko.computed(function(){
        return self.startY
    });
    self.rectX = ko.computed(function(){
        return self.startX
    });

    self.extractedY = ko.computed(function(){
        return self.startY + self.height + 2
    });
    self.extractedX = ko.computed(function(){
        return self.startX
    });

    self.elementMap = ko.computed(function(){
        var styleObject = {
            left    : self.rectX(),
            top     : self.rectY(),
            width   : self.width ,
            height  : self.height
        }
        return styleObject;
    });

}
/*
function MappingDataElement(dataElement){
    var self = this;
    self.rectangle = rectangle;
    self.id = ko.observable(dataElement.id);
    self.name = ko.observable();
    self.elementId  = self.id();
    self.pageNumber = ko.observable(dataElement.pageNumber);

    self.elementType = ko.observable(dataElement.elementType);

    self.startX = ko.observable(dataElement.startX);
    self.startY= ko.observable(dataElement.startY);
    self.width = ko.observable(dataElement.width);
    self.height = ko.observable(dataElement.height);



    self.extractedData = ko.observable(dataElement.extractedData);
    self.labelExtractedData = ko.observable(dataElement.labelExtractedData);
    //Change at message broker to meta id
    self.metaName = ko.observable(dataElement.metaName);
    self.elementClass = ko.observable('main');

    self.relevantData = ko.observable(dataElement.relevantData);
    if(dataElement.relevantData === undefined ) {
        self.relevantData = ko.observable("Select Label Element");
    }

    self.tempSubElements = $.map( dataElement.subElements, function(subElement) { return new SubDataElement(subElement) });
    self.subElements = ko.observableArray(self.tempSubElements);

    if(dataElement.subElements === undefined ) {
        self.subElements = ko.observableArray([]);
    }

    self.uiData = new UiData(dataElement.uiData);

}
*/



/*
function Page(data,textElements, tableElements, pictureElements, elementBuffer) {

    self.pageNumber = data.pageNumber;
    self.pictureSource = data.pictureSource;

    self.textElements = textElements;
    self.tableElements = tableElements;
    self.pictureElements = pictureElements;
    self.elementBuffer = elementBuffer;
}*/
