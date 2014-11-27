
/*
function Page(data) {
    this.pageNumber         = ko.observable(data.pageNumber);
    this.imagePath          = ko.observable(data.imagePath);
    this.pageId             = ko.observable('Page'+this.pageNumber().toString());
    this.pageIdLi            = ko.observable('PageLi'+this.pageNumber().toString());
    this.pageIdAn           = ko.observable('PageAn'+this.pageNumber().toString());
    this.pageNumberHref     = ko.observable('#Page' + this.pageNumber().toString());
    this.pageNumberName     = ko.observable('Page '+this.pageNumber().toString());
    this.activeStatus       = ko.observable(data.activeStatus);
}*/
function ViewModel(){
    
    var self = this;

    self.pagesData      = ko.observableArray([]);
    self.pagesDataCache = [];
    self.currentPage    = ko.observable(1);

    ///Page
    self.selectionInProgress = ko.observable(false);
    self.subElementSelectionInProgress = ko.observable(false);
    self.currentSelection = ko.observable();
    self.textElements = ko.observableArray([]);
    self.tableElements = ko.observableArray([]);
    self.pictureElements = ko.observableArray([]);
    self.elementBuffer;


    //Holds temporary sub elements
    self.tempSubs = ko.observableArray([]);

    //Initializes the extraction environment
    self.initExtractionPages = (function(){
        var imageArray = initData.imageRelativePaths;
        for(var key in imageArray){
            var data = {};
            data.pageNumber = parseInt(key)+1;
            data.imagePath = imageArray[key];///Need to use atob()
            data.activeStatus = "";
            if (data.pageNumber ==1){
                data.activeStatus = "active";
            }
            var page = new Page(data);
            var pageCache =  new PageCache(data.pageNumber);
            self.pagesData.push(page);
            self.pagesDataCache.push(pageCache);
        }
    });

    //changes pages
    self.changePage = function(data){
        self.savePage(self.currentPage());
        var newPageNumber = data.pageNumber();
        //Set Current Page Number
        self.currentPage(newPageNumber);
        self.loadNewPageData(newPageNumber);
    }

    //saves current page and resets all element collections
    self.savePage = function(pageNumber){

        var elementPos = self.pagesDataCache.map(function(pageCache) {return pageCache.pageNumber; }).indexOf(pageNumber);
        var pageToSave = self.pagesDataCache.splice(elementPos,1)[0];

        pageToSave.pageNumber                       =  pageNumber;
        pageToSave.selectionInProgress              =  ko.toJS(self.selectionInProgress());
        pageToSave.subElementSelectionInProgress    =  ko.toJS(self.subElementSelectionInProgress());
        pageToSave.currentSelection                 =  ko.toJS(self.currentSelection());
        pageToSave.elementBuffer                    =  ko.toJS(self.elementBuffer);
        pageToSave.textElements                     =  ko.toJS(self.textElements());
        pageToSave.tableElements                    =  ko.toJS(self.tableElements());
        pageToSave.pictureElements                  =  ko.toJS(self.pictureElements());
        self.pagesDataCache.push(pageToSave);

        // Clear all data
        self.elementBuffer = undefined
        if(self.textElements() !== undefined){
            self.textElements.removeAll();
        }
        if(self.tableElements() !== undefined){
            self.tableElements.removeAll();
        }
        if(self.pictureElements() !== undefined){
            self.pictureElements.removeAll();
        }
    }

    //Loads the various elements for the currently laoded page
    self.loadNewPageData = function(newPageNumber){

        var elementPos = self.pagesDataCache.map(function(pageCache) {return pageCache.pageNumber; }).indexOf(newPageNumber);
        var newPageTemp = self.pagesDataCache[elementPos];
        var newPage = {};
        newPage.textElements = $.map( newPageTemp.textElements, function(element) { return new DataElement(element.rectangle) });
        newPage.tableElements = $.map( newPageTemp.tableElements, function(element) { return new DataElement(element.rectangle) });
        newPage.pictureElements = $.map( newPageTemp.pictureElements, function(element) { return new DataElement(element.rectangle) });

        self.selectionInProgress(newPageTemp.selectionInProgress);
        self.subElementSelectionInProgress(newPageTemp.subElementSelectionInProgress);
        self.currentSelection(newPageTemp.currentSelection);
        self.elementBuffer = newPageTemp.elementBuffer;

        for(var key in newPage.textElements){
            if(newPage.textElements[key] !== undefined ){   self.textElements.push(newPage.textElements[key])       };
        }
        for(var key in newPage.tableElements){
            if(newPage.tableElements[key] !== undefined ){   self.tableElements.push(newPage.tableElements[key])     };
        }
        for(var key in newPage.pictureElements){
            if(newPage.pictureElements[key] !== undefined ){   self.pictureElements.push(newPage.pictureElements[key]) };
        }

        for(var key in  self.dataElements()()){
            disappearDecos(self.dataElements()()[key].elementId);
        }


    }


    //This contains the master collection of main elements
    // which is iterated in knockout using template binding
    self.dataElements = ko.computed(function(){
        elements = ko.observableArray([]);
        for(var key in self.textElements()){
            elements.push(self.textElements()[key]);
        }
        for(var key in self.tableElements()){
            elements.push(self.tableElements()[key]);
        }
        for(var key in self.pictureElements()){
            elements.push(self.pictureElements()[key]);
        }
        return elements;
    });

    //This contains the master collection of sub elements[the rectabgles within]
    // which is iterated in knockout using template binding
    self.subDataElements = ko.computed(function(){
        elements = ko.observableArray([]);
        for(var key in self.textElements()){
            for(var innerKey in self.textElements()[key].subElements()){
                elements.push(self.textElements()[key].subElements()[innerKey]);
            }
        }
        for(var key in self.tableElements()){
            for(var innerKey in self.tableElements()[key].subElements()){
                elements.push(self.tableElements()[key].subElements()[innerKey]);
            }
        }
        for(var key in self.pictureElements()){
            for(var innerKey in self.pictureElements()[key].subElements()){
                elements.push(self.pictureElements()[key].subElements()[innerKey]);
            }
        }
        return elements;
    });



    //Adds a main element to the UI logic in turn showin it in the UI
    self.addTextElement = function (data){
        var element = new DataElement(data);
        self.textElements.push(element);
        self.elementBuffer = ko.toJS(element);
    }
    self.addPictureElement = function (data){
        var element = new DataElement(data);
        self.pictureElements.push(element);
        self.elementBuffer = ko.toJS(element);

    }
    self.addTableElement = function (data){
        var element = new DataElement(data);
        self.tableElements.push(element);
        self.elementBuffer = ko.toJS(element);

    }

    //Adding sub element
    self.addSubElement = function (data){
        var subElement = new SubDataElement(data);
        self.elementBuffer.subElements.push(ko.toJS(subElement));

        if (data.elementType === 'text') {
            var relevantTextElement  = self.textElements.remove(function(item) { 
                return item.elementId === data.elementId;
            })[0];
            relevantTextElement.relevantData(subElement.relevantData());
            relevantTextElement.subElements.push(subElement);
            self.textElements.push(relevantTextElement);
        }
        else if (data.elementType === 'table') {
            var relevantTableElement  = self.tableElements.remove(function(item) { 
                return item.elementId === data.elementId;
            })[0];
            relevantTableElement.relevantData(subElement.relevantData());
            relevantTableElement.subElements.push(subElement);
            self.tableElements.push(relevantTableElement);
        }
        else if (data.elementType === 'picture') {
            var relevantPictureElement  = self.pictureElements.remove(function(item) { 
                return item.elementId === data.elementId;
            })[0];
            relevantPictureElement.relevantData(subElement.relevantData());
            relevantPictureElement.subElements.push(subElement);
            self.pictureElements.push(relevantPictureElement);
        }
        self.tempSubs.push(subElement);
            ////////////////
            ////////////////
    }

    //Can remove elements on demand
    self.removeElement = function (removedElement){
        if(removedElement.elementClass() === 'main'){
            self.elementBuffer = undefined;
            if (removedElement.elementType() === 'text') {
                //Remove element
                self.textElements.remove(removedElement);
            }
            else if (removedElement.elementType() === 'table') {
                self.tableElements.remove(removedElement);
            }
            else if (removedElement.elementType() === 'picture') {
                self.pictureElements.remove(removedElement);
            }
            resetEnvironment();
        }
        else if(removedElement.elementClass() === 'sub'){

            //Remove From Buffer
            var removedSubelementId = ko.toJS(removedElement).id
            var indexOfRemovedElmentInBuffer = $.map(self.elementBuffer.subElements, function(elementInBuffer,indexInBuffer){
                if(elementInBuffer.id === removedSubelementId){
                    return indexInBuffer;
                }
            })[0];
            self.elementBuffer.subElements.splice(indexOfRemovedElmentInBuffer,1);

            if (removedElement.elementType() === 'text') {
                //Remove element
                var relevantTextElement  = self.textElements.remove(function(item) { 
                    return item.elementId === removedElement.elementId();
                })[0];

                relevantTextElement.subElements.remove(removedElement);
                relevantTextElement.relevantData("Select Label Element");
                self.textElements.push(relevantTextElement);
                vm.subElementSelectionInProgress(true);
                $('div#'+removedElement.elementId()+'.mainElement').css('cursor','crosshair');
                selectionInitializer('div#'+removedElement.elementId()+'.mainElement',drawingRouter);
            }
            else if (removedElement.elementType()=== 'table') {
                var relevantTableElement  = self.tableElements.remove(function(item) { 
                    return item.elementId === removedElement.elementId();
                })[0];

                relevantTableElement.subElements.remove(removedElement);
                self.tableElements.push(relevantTableElement);
            }
            else if (removedElement.elementType() === 'picture') {
                var relevantPictureElement  = self.pictureElements.remove(function(item) { 
                    return item.elementId === removedElement.elementId();
                })[0];

                relevantPictureElement.subElements.remove(removedElement);
                self.pictureElements.push(relevantPictureElement);
            }
        }

        
    }

    //element is anyway saved already
    //The buffer is maintained only when its needed to remove the inserted element or cancel selection
    self.saveSelection = function(element,selection){
        self.elementBuffer = undefined;
    }

    //Allows a currently processing element selection to be cancelled
    self.cancelSelection = function(){
        if (self.elementBuffer !== undefined){
            if (self.elementBuffer.elementType === 'text') {
                //Remove element
                self.textElements.remove(function (item) {
                    return item.elementId === self.elementBuffer.elementId
                });
            }
            else if (self.elementBuffer.elementType === 'table') {
                self.tableElements.remove(function (item) {
                    return item.elementId === self.elementBuffer.elementId
                });
            }
            else if (self.elementBuffer.elementType === 'picture') {
                self.pictureElements.remove(function (item) {
                    return item.elementId === self.elementBuffer.elementId
                });
            }
            self.elementBuffer = undefined;
        }
    }

    self.sendingJson = ko.observable("Content Creating");

    self.sendingJsonFinal = ko.observable("Content Creating");

    //The alternative method to pulse extraction
    //specially tailored for table extraction
    self.extractTable = function(element){
        var table = ko.toJS(element);
        var response = getTableExtraction(table)
        element.extractedData(response.extractedData);
    }


    //The final call which saves the total bulk data to the DB
    self.sendJson = function (){
        var data = {};
//        data.textDataElements   =   self.textElements();
//        data.tableDataElements   =   self.tableElements();
//        data.pictureDataELements   =   self.pictureElements();
//        self.sendingJson(ko.toJSON(data));
        var currentPage = self.currentPage();
        self.savePage(currentPage);
        data.textDataElements   =   []
        data.tableDataElements   =   []
        data.pictureDataELements   =   []
        //Restructure removing pages super collection
        for(var key in self.pagesDataCache ){
            var pageDataCache = self.pagesDataCache[key];
            if(pageDataCache.textElements !== undefined)    {  data.textDataElements = data.textDataElements.concat(pageDataCache.textElements);   }
            if(pageDataCache.tableElements !== undefined)   {  data.tableDataElements =  data.tableDataElements.concat(pageDataCache.tableElements);   }
            if(pageDataCache.pictureElements !== undefined) {  data.pictureDataELements =  data.pictureDataELements.concat(pageDataCache.pictureElements); }
        }



        self.sendingJson(JSON.stringify(data).toString());
        var bulk = ko.toJSON(sendBulkData(data));
        self.sendingJsonFinal(bulk);
        self.loadNewPageData(currentPage);
    }


}

var vm = new ViewModel();

$(window).ready(function(){

    vm = new ViewModel();
    ko.applyBindings(vm);

    setTimeout(resetLongPage, 400);

})

var resetLongPage = function(){
    $('a#PageAn2').click();
    $('a#PageAn1').click();
}