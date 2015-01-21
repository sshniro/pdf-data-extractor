
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


    ///Page
    self.editingInProgress = ko.observable(false);
    self.subElementEditingInProgress = ko.observable(false);


    self.currentSelection = ko.observable();
    self.textElements = ko.observableArray([]);
    self.tableElements = ko.observableArray([]);
    self.pictureElements = ko.observableArray([]);
    self.regexElements = ko.observableArray([]);
    self.patternElements = ko.observableArray([]);
    self.currentDic = ko.observableArray([]);
    self.elementBuffer;



    self.getDictionaryData = function(){
        var data={ 'request' : "getAllDicItems"};
        $.ajax({
            type: 'POST', url: 'DictionaryController',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            data: JSON.stringify(data),
            async:false,
            success: function(data, textStatus, jqXHR) {
                var dicObj = JSON.parse(jqXHR.responseText);
                for(item in dicObj) {
                    self.currentDic.push(new Keyword(dicObj[item]));
                }
            }
        });
    }




    //Button Functionalities
    self.textButton= function(){
        vm.currentSelection("text");
        selectionStarted();
        selectionInitializer("img",drawingRouter);
        $("#runningInstructions").text('Select Text Element');
    }
    self.tableButton= function(){
        vm.currentSelection("table");
        selectionStarted();
        selectionInitializer("img",drawingRouter);
        $("#runningInstructions").text('Select Table Element');
    }
    self.pictureButton= function(){
        event.stopPropagation();
        vm.currentSelection("picture");
        selectionStarted();
        selectionInitializer("img",drawingRouter);
        $("#runningInstructions").text('Select Picure Element');
    }

    self.regexButton= function(){
        event.stopPropagation();
        vm.currentSelection("regex");
        selectionStarted();
        selectionInitializer("img",drawingRouter);
        $("#runningInstructions").text('Select Starting Tag');
    }

    self.patternButton= function(){
        event.stopPropagation();
        vm.currentSelection("pattern");
        selectionStarted();
        selectionInitializer("img",drawingRouter);
        $("#runningInstructions").text('Select Starting Tag');
    }

    self.editButton= function(){
        event.stopPropagation()
        $("#runningInstructions").text('Drag or Resize Elements');
        editStarted();
        draggableActivator();
    }
    self.cancelButton= function(){
        vm.cancelSelection();
        resetEnvironment();
    }
    self.saveButton= function(){
        event.stopPropagation()
        if(vm.elementBuffer !== undefined){
            $("div#"+vm.elementBuffer.id+".mainElement").unbind();
            disappearDecos(vm.elementBuffer.id)//Should be called before saveSelection because the buffer is cleared
        }
        resetEnvironment();
        vm.saveSelection();
    }


    //Holds temporary sub elementsx
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

        pageToSave.regexElements                    =  ko.toJS(self.regexElements());
        pageToSave.patternElements                  =  ko.toJS(self.patternElements());

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
        if(self.regexElements() !== undefined){
            self.regexElements.removeAll();
        }
        if(self.patternElements !== undefined){
            self.patternElements.removeAll();
        }
    }

    //Loads the various elements for the currently laoded page
    self.loadNewPageData = function(newPageNumber){

        var elementPos = self.pagesDataCache.map(function(pageCache) {return pageCache.pageNumber; }).indexOf(newPageNumber);
        var newPageTemp = self.pagesDataCache[elementPos];

        self.selectionInProgress(newPageTemp.selectionInProgress);
        self.subElementSelectionInProgress(newPageTemp.subElementSelectionInProgress);
        self.currentSelection(newPageTemp.currentSelection);
        self.elementBuffer = newPageTemp.elementBuffer;
        for(var dataType in newPageTemp) {
            if(dataType !== "textElements"&& dataType !== "pictureElements" && dataType !== "tableElements"){
                //this loop activates only for data elements
                continue;
            }
            for (var dataElement in newPageTemp[dataType]) {
                var rawData = newPageTemp[dataType][dataElement];

                rawData.rectangle.labelExtractedData = rawData.labelExtractedData;
                rawData.rectangle.metaName = rawData.metaName;
                rawData.rectangle.selectedDictionaryItem = rawData.selectedDictionaryItem;
                rawData.rectangle.relevantData = rawData.relevantData;

                if (rawData.selectedDictionaryItem !== undefined) {
                    var dictionaryId = rawData.selectedDictionaryItem.id;
                    var selectedDictionaryItem = vm.currentDic.remove(function (item) {
                        return item.id() === dictionaryId;
                    })[0];
                    if (selectedDictionaryItem !== undefined) {
                        vm.currentDic.push(selectedDictionaryItem);
                        rawData.rectangle.selectedDictionaryItem = selectedDictionaryItem;
                    }
                }
                var newDataElement = new DataElement(rawData.rectangle, rawData.subElements);

                self[dataType].push(newDataElement);
            }
        }

        resetEnvironment();
        for(var key in  self.dataElements()()){
            disappearDecos(self.dataElements()()[key].elementId);
            disappearSubDecos(self.dataElements()()[key].elementId)
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
        for(var key in self.regexElements()){
            elements.push(self.regexElements()[key]);
        }
        for(var key in self.patternElements()){
            elements.push(self.patternElements()[key]);
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

    self.addRegexElement = function (data){
        var element = new DataElement(data);
        self.regexElements.push(element);
        self.elementBuffer = ko.toJS(element);

    }
    self.addPatternElement = function (data){
        var element = new DataElement(data);
        self.patternElements.push(element);
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

            //Used in workflow for meta generations for column headers
            relevantTableElement.setCurrentSubElement(subElement);
            relevantTableElement.saveCurrentSubElement();

            indexInSubElements =  relevantTableElement.subElements.indexOf(subElement);
            relevantTableElement.currentSubElement(relevantTableElement.subElements()[indexInSubElements]);

            self.tableElements.push(relevantTableElement);
            var subElement = $("div#"+subElement.id()+".subElement");
            subElement.css('background-color','rgba(46, 204, 113,0.3)');
        }
        else if (data.elementType === 'picture') {
            var relevantPictureElement  = self.pictureElements.remove(function(item) {
                return item.elementId === data.elementId;
            })[0];
            relevantPictureElement.relevantData(subElement.relevantData());
            relevantPictureElement.subElements.push(subElement);
            self.pictureElements.push(relevantPictureElement);
        }
        else if (data.elementType === 'regex') {
            var relevantRegexElement  = self.regexElements.remove(function(item) {
                return item.elementId === data.elementId;
            })[0];
            relevantRegexElement.relevantData(subElement.relevantData());

            relevantRegexElement.subElements.push(subElement);

            //Used in workflow for meta generations for column headers
            relevantRegexElement.setCurrentSubElement(subElement);
            relevantRegexElement.saveCurrentSubElement();

            indexInSubElements =  relevantRegexElement.subElements.indexOf(subElement);
            //Setting the current sub element within the regex object object
            relevantRegexElement.currentSubElement(relevantRegexElement.subElements()[indexInSubElements]);

            self.regexElements.push(relevantRegexElement);
            var subElement = $("div#"+subElement.id()+".subElement");
            subElement.css('background-color','rgba(46, 204, 113,0.3)');
        }
        else if (data.elementType === 'pattern') {
            var relevantPatternElement  = self.patternElements.remove(function(item) {
                return item.elementId === data.elementId;
            })[0];
            relevantPatternElement.relevantData(subElement.relevantData());

            relevantPatternElement.subElements.push(subElement);

            //Used in workflow for meta generations for column headers
            relevantPatternElement.setCurrentSubElement(subElement);
            relevantPatternElement.saveCurrentSubElement();

            indexInSubElements =  relevantPatternElement.subElements.indexOf(subElement);
            //Setting the current sub element within the pattern object object
            relevantPatternElement.currentSubElement(relevantPatternElement.subElements()[indexInSubElements]);

            self.patternElements.push(relevantPatternElement);
            var subElement = $("div#"+subElement.id()+".subElement");
            subElement.css('background-color','rgba(46, 204, 113,0.3)');
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
            else if (removedElement.elementType() === 'regex') {
                self.regexElements.remove(removedElement);
            }
            else if (removedElement.elementType() === 'pattern') {
                self.patternElements.remove(removedElement);
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
            else if (removedElement.elementType()=== 'regex') {
                var relevantRegexElement  = self.regexElements.remove(function(item) {
                    return item.elementId === removedElement.elementId();
                })[0];

                relevantRegexElement.subElements.remove(removedElement);
                self.regexElements.push(relevantRegexElement);
            }
            else if (removedElement.elementType()=== 'pattern') {
                var relevantPatternElement  = self.patternElements.remove(function(item) {
                    return item.elementId === removedElement.elementId();
                })[0];

                relevantPatternElement.subElements.remove(removedElement);
                self.patternElements.push(relevantPatternElement);
            }
            vm.subElementSelectionInProgress(true);
            $('div#'+removedElement.elementId()+'.mainElement').css('cursor','crosshair');
            selectionInitializer('div#'+removedElement.elementId()+'.mainElement',drawingRouter);
        }


    }

    //Can remove elements on demand with the use of the relevant dom element
    //Use @ editing feature to remove a dom element on click!
    self.removeElementUsingDomElement = function (DomElement){
        var elementId = DomElement.id
        var removedElement;
        var removedTextElement = self.textElements.remove(function(item) {
            return item.elementId ===elementId;
        })[0];

        var removedTableElement = self.tableElements.remove(function(item) {
            return item.elementId ===elementId;
        })[0];

        var removedPictureElement  = self.pictureElements.remove(function(item) {
            return item.elementId ===elementId;
        })[0];

        if(removedTextElement !== undefined){
            removedElement = removedTextElement
        }
        else if(removedPictureElement !== undefined){
            removedElement = removedPictureElement
        }
        else if(removedTableElement !== undefined){
            removedElement = removedTableElement;
        }
        else{
            console.log("Unknown Error when removing prior element");
            return false;
        }

        self.currentSelection(removedElement.elementType());
        return removedElement;



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
        window.location.href = "ExtractPdf.jsp";
    }


    /////////////////////////////////////////////
    ///    default.jsp / ExtractPdf.jsp VM    ///
    /////////////////////////////////////////////

    //logout
    self.logout = function(){
        window.location.href = "index.jsp";
    };

    var nodeModel = function(data){
        this.id = ko.observable(data.id);
        this.parent = ko.observable(data.parent);
        this.text = ko.observable(data.text);
    };

    var rootNode = {'id':'#', 'parent':undefined, 'text':'root'};

    self.currentSelectedTreeNode = ko.observable(new nodeModel(rootNode));
    self.currentNodeParent = ko.observable(new nodeModel(rootNode));
    self.newSubCategoryName = ko.observable();
    self.notification_createNewSubCategory = ko.observable();
    self.newTemplateName = ko.observable();
    self.overlayNotification = ko.observable('Notification...');
    self.selectedPdfTemplate = ko.observable('');
    self.extractedPdfId = ko.observable();

    self.isSelectedTemplate = ko.observable(false);
    self.selectedDocumentId = ko.observable('');

    self.createNewSubCategory = function(){
        // validation
        var msg = 'Cannot create a sub category in selected node!\nPlease try another node which is not a template or node contain a template.';
        if (selectedNodeRow != undefined){ // if not the root
            if (selectedNodeRow.original.pdfFile != undefined) { alert(msg); return false; } // if is a template return
            else { // if not a template
                if (selectedNodeChildRow != false){ // if has children
                    if (selectedNodeChildRow.original.pdfFile != undefined){ alert(msg); return false; } // if children is a template
                }
            }
        }

        var data={ 'request' : "createNode",
            'parent' : self.currentSelectedTreeNode().id(),
            'text' : self.newSubCategoryName()
        };
        $.ajax({
            type: 'POST', url: 'ManageCategoriesController',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            data: JSON.stringify(data),
            success: function(data, textStatus, jqXHR) {
                var messages = JSON.parse(jqXHR.responseText);
                self.notification_createNewSubCategory(messages.statusMessage);
                self.newSubCategoryName('');
                window.location.reload();
            }
        });
    };

    self.setRootAsCurrentSelectedTreeNode = function(){
        self.currentSelectedTreeNode(new nodeModel(rootNode));
        self.currentNodeParent(new nodeModel(rootNode));
        selectedNodeRow = undefined;
        selectedNodeChildRow = undefined;
        selectedNodeParentRow = undefined;
        $('#treeViewDiv').jstree("deselect_all");
    };

    self.setCurrentSelectedTreeNode = function(node){
        self.currentSelectedTreeNode(new nodeModel({'id':node.id, 'parent':node.parent, 'text':node.text}));
    };

    self.uploadNewTemplate = function(){
        // alter when trying to create new template in invalid category
        if(selectedNodeRow === undefined){  // selected is the root
            alert("Templates cannot be created in root node\nPlease select a child node!");
            return false;
        }
        else{   // selected is not the root
            if(selectedNodeRow.children.length != 0){   // selected have children
                if(selectedNodeChildRow.original.pdfFile === undefined) {   // first child is another folder
                    alert("Templates cannot be created in this node\nPlease select another node!");
                    return false;
                }
            }
            else {   // selected doesn't have children
                if (selectedNodeRow.original.pdfFile != undefined) {
                    alert('You have selected a Template node!\nTemplate cannot create in another template node');
                    return false;
                }
            }
        }

        var file = document.getElementById("templateFile");
        /* alert the user to input a value to the Document ID*/
        if(self.newTemplateName()==""){
            alert("Template Name is required");
            return false;
        }
        var fileName = $("#templateFile").val();
        /* alert the user to select a File */
        if(fileName==""){
            alert("Select a PDF File");
            return false;
        }

        /* Create a FormData instance */
        var formData = new FormData();
        /* Add the file */
        formData.append("parent", self.currentSelectedTreeNode().id()); // parent id
        formData.append("text", self.newTemplateName()); // name of the template as in next
        formData.append("templateName", self.newTemplateName());
        formData.append("pdfFile", file.files[0]);

        client.open("post", "TemplateUploadController", true);
        client.send(formData);  /* Send to server */

        /*Set the upload button to be disabled */
        $("#btnUpload").attr("disabled", true);
        self.overlayNotification('Uploading...');
        $('#overlay').css('display', 'block');
    };

    self.setIsSelectedTemplate = function(){
        if(selectedNodeRow.original.pdfFile != undefined){
            self.isSelectedTemplate(true);
            self.selectedPdfTemplate(selectedNodeRow.original.pdfFile);
            document.getElementById('pdfRenderer').src = "file://" + self.selectedPdfTemplate();
        }else{
            self.isSelectedTemplate(false);
            self.selectedPdfTemplate('');
        }
    };

    self.uploadPdfFile =  function(){

        if(!self.isSelectedTemplate()){
            alert("Can't upload file.\nPlease select a template.");
            return false;
        }

        var file = document.getElementById("pdfFile");

        /* alert the user to input a value to the Document ID*/
        if(self.selectedDocumentId() == ''){
            alert("Document ID is required");
            return false;
        }

        var fileName = $("#pdfFile").val();
        /* alert the user to select a File */
        if(fileName==""){
            alert("Select a PDF File");
            return false;
        }

        /* Create a FormData instance */
        var formData = new FormData();
        /* Add the file */
        formData.append("parent", self.currentSelectedTreeNode().id()); // template id
        formData.append("text", self.selectedDocumentId()); // document name as in next
        formData.append("pdfFile", file.files[0]);

        client.open("post", "ExtractPdfController", true);
        client.send(formData);  /* Send to server */

        /*Set the upload button to be disabled */
        $("#ajaxStart").attr("disabled", true);
        self.overlayNotification('Uploading...');
        $('#overlay').css('display', 'block');
    };

    self.editTemplate =  function(){
        self.overlayNotification('Loading...');
        $('#overlay').css('display','block');
        var editData = {
            "parent"          : self.currentSelectedTreeNode().id(),
            "text"            : self.selectedDocumentId(),
            "id"              : self.extractedPdfId()
        };
        $.post("EditTemplateController",JSON.stringify(editData))
            .done(function(){
                $('#overlay').css('display','none');
                window.location = '/MarkUpTemplateRegions.jsp'
            });
        //$('button#editTemplate').css('display','none');
    };

    self.redirectToEditTemplate = function(){
        if(self.isSelectedTemplate()) {
            self.editTemplate();
        }
        else{
            alert('select a template!');
        }
    };


    self.currentDic = ko.observableArray([]);

    self.dictionaryObject= ko.observableArray()

}

var vm = new ViewModel();

