//Status Variable
//Global Variables
//Starting pixel coordinates of the ui component drawn upon
var uiCompStartingX;
var uiCompStartingY;

//current imgareSelection instance
var selectedImgAreInstance = undefined;


//Bufers
var currentElement = undefined;

function Keyword(data){
    this.id = ko.observable(data.id);
    this.name = ko.observable(data.name);
    this.type = ko.observable(data.type);
    this.description = ko.observable(data.description);
    this.dataType = ko.observable(data.dataType);
    this.length = ko.observable(data.length);
    this.defaultValues = ko.observable(data.defaultValues);
    this.allowedValues = ko.observable(data.allowedValues);
}

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
function DataElement(rectangle, subElements){
    var self = this;
    self.rectangle = rectangle;
    self.id = ko.observable(rectangle.id);
    self.name = ko.observable();
    self.elementId  = self.id();
    self.elementClass = ko.observable('main');

    self.elementType = ko.observable(rectangle.elementType);

    self.startX = ko.observable(rectangle.startX);
    self.startY= ko.observable(rectangle.startY);
    self.width = ko.observable(rectangle.width);
    self.height = ko.observable(rectangle.height);
    self.pageNumber = ko.observable(rectangle.pageNumber);

    self.extractedData = ko.observable(rectangle.extractedData);

    self.labelExtractedData = ko.observable(rectangle.labelExtractedData);
    //Change at message broker to meta id
    self.metaName = ko.observable();

    self.elementViseCurrentDic = ko.observable(ko.utils.unwrapObservable(vm.currentDic));
    self.selectedDictionaryItem = ko.observable();
    if(rectangle.selectedDictionaryItem !== undefined){
        self.selectedDictionaryItem = ko.observable(rectangle.selectedDictionaryItem);
    }


    self.relevantData = ko.observable(rectangle.relevantData);
    if(rectangle.relevantData === undefined ) {
        self.relevantData = ko.observable("Select Label Element");
    }


    self.subElements = ko.observableArray([]);

    if(subElements !== undefined){
        self.tempSubelements = [];
        for(var key in subElements) {
            var tempRectangle = subElements[key];
            var subDataElement   = new SubDataElement(tempRectangle);
            self.subElements.push(subDataElement);
        };


    }
    else if(rectangle.subElements !== undefined ) {
        self.tempSubElements = $.map( rectangle.subElements, function(subElement) { return new SubDataElement(subElement) });
        self.subElements = ko.observableArray(self.tempSubElements);

    }
    else {
        self.subElements = ko.observableArray([]);
    }

    if(self.subElements.length === 0){
        var dummy = {};
        self.currentSubElement = ko.observable(new SubDataElement(dummy));
    }
    else{
        self.currentSubElement = ko.observable(subElements()[0]);
    }


    self.setCurrentSubElement = function(data,event){
        //Save current sub element
        //event.stopPropagation();
        self.saveCurrentSubElement();
        self.showNewSubElement(data)

    }


    self.saveCurrentSubElement = function(){
        var currentSubElement = self.currentSubElement();
        if(currentSubElement !== undefined){
            var relevantSubElementIndex = $.map(
                self.subElements(),
                function(element) {return element.id();
                }).indexOf(currentSubElement.id());

            if(relevantSubElementIndex !== -1){
                self.subElements.remove(function(item) {
                    return item.id() === currentSubElement.id();
                })[0];
                self.subElements.splice(relevantSubElementIndex, 0, currentSubElement);

            }

        }
        self.subElements.sort();

    };

    self.showNewSubElement = function(data){
        var indexOfCurrentSubElement = self.subElements.indexOf(data);
        data.index = indexOfCurrentSubElement;
        self.currentSubElement(data);
        $("div.subElement").css('background-color','rgba(0, 0, 0,0)');
        var subElement = $("div#"+data.id()+".subElement");
        subElement.css('background-color','rgba(46, 204, 113,0.3)');
    };

    self.uiData = new UiData(rectangle);

}

function immediateSelectedObjectModel(baseUiComponent, rectangle){
    var self = this;
    if(!(baseUiComponent == undefined || rectangle == undefined)){
        self.baseUiComponent = baseUiComponent;
        self.rectangle = rectangle;
    }
};

//Coordinates Relative to parent ELement!!!!
function SubDataElement(rectangle) {
    var self = this;
    self.rectangle = rectangle;
    self.elementId = ko.observable(rectangle.elementId);
    self.id = ko.observable(rectangle.id);
    self.elementType = ko.observable(rectangle.elementType);
    self.metaName = ko.observable();

    //Used for Pattern and regex workflows/////////////////
    self.subElementType = ko.observable(rectangle.subElementType);
    self.isSubElementTypeSelected = ko.observable(rectangle.isSubElementTypeSelected);
    self.isHavingEndTag = ko.observable(rectangle.isHavingEndTag);
    self.subElementEndTag = ko.observable(rectangle.subElementEndTag);
    self.subElementStartTag = ko.observable(rectangle.subElementStartTag);
    self.isHavingRepeatedHeaders = ko.observable(false);

    self.repeatingSubElements = ko.observableArray([]);
    if(rectangle.repeatingSubElements !== undefined){
        self.repeatingSubElements(rectangle.repeatingSubElements);
    }

    self.bufferedRepeatingElement_start = ko.observable('');
    self.bufferedRepeatingElement_end = ko.observable('');

    /*
    if (self.elementType() === 'regex' || self.elementType() === 'pattern') {

    }*/

    self.selectElementType = function (data, element) {
        self.subElementType(data);
        self.isSubElementTypeSelected(true);
        vm.currentProcessingSubElement(data);
        if (data == 'RE') {
            self.isHavingEndTag(true);
            self.isHavingRepeatedHeaders(false);
            self.subElementEndTag('SELECT END TAG');
            selectionInitializer('#' + vm.immediateSelectedObject().baseUiComponent.id + '.mainElement', drawingRouter, vm.immediateSelectedObject().rectangle.id);
        }
        else if (data == 'PE') {
            self.isHavingEndTag(false);
            self.isHavingRepeatedHeaders(true);
            //Bringing VM logic
            var data = getSubExtraction(rectangle,"pattern2");
            ///TODO: DESERIALIZE AND INTEGRATE HERE
            var data = [];
            var pair1 = {
                start:"Hardcoded1Start",
                end:"Hardcoded1End"
            };
            var pair2 = {
                start:"Hardcoded2Start",
                end:"Hardcoded2End"
            };
            data.push(pair1);
            data.push(pair2);
            for(var pairkey in data){
                self.repeatingSubElements.push(data[pairkey]);
            }

        }
        else if (data == 'LEE') {
            self.isHavingEndTag(false);
            self.isHavingRepeatedHeaders(false);
            self.subElementEndTag('eol');
            vm.currentProcessingSubElement('');
            selectionInitializer('#' + vm.immediateSelectedObject().baseUiComponent.id + '.mainElement', drawingRouter, vm.immediateSelectedObject().rectangle.id);
        }
    };

    self.changeElementType = function () {
        self.subElementType('');
        self.isSubElementTypeSelected(false);
        vm.currentProcessingSubElement('');
    };

    self.addRepeatingElement = function () {
        var pair = {
            start:self.bufferedRepeatingElement_start(),
            end:self.bufferedRepeatingElement_end()
        };
        self.repeatingSubElements.push(pair);
        self.bufferedRepeatingElement_start('');
        self.bufferedRepeatingElement_end('');
    };

    self.removeRepeatingElement = function (data) {
        self.repeatingSubElements.remove(data);
    };

    self.completeElement = function () {
        vm.currentProcessingSubElement('');
        $('.addStartEnd').hide();
        selectionInitializer('#' + vm.immediateSelectedObject().baseUiComponent.id + '.mainElement', drawingRouter, vm.immediateSelectedObject().rectangle.id);
    };
    /////////////////////////////////////////////////////

    self.elementViseCurrentDic = ko.observable(ko.utils.unwrapObservable(vm.currentDic));
    self.selectedDictionaryItem = ko.observable();
    if(rectangle.selectedDictionaryItem !== undefined){
        self.selectedDictionaryItem = ko.observable(rectangle.selectedDictionaryItem);
    }

    self.startX = ko.observable(rectangle.startX);
    self.startY= ko.observable(rectangle.startY);
    self.width = ko.observable(rectangle.width);
    self.height = ko.observable(rectangle.height);

    //getMainExtraction gives extactedData
    self.extractedData = ko.observable(rectangle.extractedData);
    self.metaName(ko.utils.unwrapObservable(self.extractedData));

    //getSubExtraction gives relevantData
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
        return self.baseUiComponentStartY() + self.startY - 90
    });

    self.metaStartX = ko.computed(function(){
        return self.baseUiComponentStartX() + self.startX - 5
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
        return self.startY + self.height + 1
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
