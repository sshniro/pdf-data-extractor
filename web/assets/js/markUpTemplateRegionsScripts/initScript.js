/**
 * Created by Dehan De Croos on 1/9/2015.
 */
(function initEnvironment() {
    vm = new ViewModel();
    ko.applyBindings(vm);

    if (responseObj === null) {
        initDataJSON = '{"mainCategory":"Sales Order",' +
            '"subCategory":"Supplier 10","templateName":"template1","imageRelativePaths":["assets/img/pdfimage1.jpg","assets/img/pdfimage1.jpg"]}'
        initData = JSON.parse(initDataJSON);
    }
    else {
        initData = responseObj;
        var imageRelativePaths = initData.imageRelativePaths;
        var newImageRelativePaths = [];
        for (var key in imageRelativePaths) {
            var imageRelativePath = imageRelativePaths[key];
            imageRelativePath = atob(imageRelativePath);
            imageRelativePath = imageRelativePath.split("\\").join("/");
            newImageRelativePaths.push(imageRelativePath);
        }
        initData.imageRelativePaths = newImageRelativePaths;
    }
    vm.initExtractionPages();
    vm.getDictionaryData();

    //Setting up core functionality and data
    if (responseObj.insertDataParser === undefined) {
        effectiveController = "MarkUpTemplateRegionController";
    }
    else {
        effectiveController = "EditMarkupController";
        //Load existing data to page
        //load text data

        /*
         var relevantTableElement  = self.tableElements.remove(function(item) {
         return item.elementId === data.elementId;
         })[0];*/
        if (responseObj.insertDataParser.textDataParser !== undefined) {
            var textElements = responseObj.insertDataParser.textDataParser.textDataElements;
            for (textElement in textElements) {
                var currentDataElement = textElements[textElement];

                currentDataElement.rawData.dictionaryId = currentDataElement.dictionaryId;
                var selectedDictionaryItem = vm.currentDic.remove(function (item) {
                    return item.id() === parseInt(currentDataElement.dictionaryId);
                })[0];
                if(selectedDictionaryItem !== undefined){
                    vm.currentDic.push(selectedDictionaryItem);
                    currentDataElement.rawData.selectedDictionaryItem = selectedDictionaryItem;
                }



                currentDataElement.rawData.metaName = currentDataElement.metaName;
                currentDataElement.rawData.pageNumber = currentDataElement.rawData.page;
                data = {};
                data.pageNumber = ko.observable(currentDataElement.pageNumber);
                vm.changePage(data);
                vm.addTextElement(currentDataElement.rawData);
                if (currentDataElement.metaRawData !== undefined) {
                    vm.addSubElement(currentDataElement.metaRawData);

                }
            }
        }

        if (responseObj.insertDataParser.imageDataParser !== undefined) {
            var imageElements = responseObj.insertDataParser.imageDataParser.imageDataElements;
            for (imageElement in imageElements) {
                var currentDataElement = imageElements[imageElement];

                currentDataElement.rawData.dictionaryId = currentDataElement.dictionaryId;
                var selectedDictionaryItem = vm.currentDic.remove(function (item) {
                    return item.id() === parseInt(currentDataElement.dictionaryId);
                })[0];
                if(selectedDictionaryItem !== undefined){
                    vm.currentDic.push(selectedDictionaryItem);
                    currentDataElement.rawData.selectedDictionaryItem = selectedDictionaryItem;
                }


                currentDataElement.rawData.metaName = currentDataElement.metaName;
                currentDataElement.rawData.pageNumber = currentDataElement.rawData.page;
                data = {};
                data.pageNumber = ko.observable(currentDataElement.pageNumber);
                vm.changePage(data);
                vm.addPictureElement(currentDataElement.rawData);
                if (currentDataElement.metaRawData !== undefined) {
                    vm.addSubElement(currentDataElement.metaRawData);
                }

            }
        }

        if (responseObj.insertDataParser.tableDataParser !== undefined) {
            var tableElements = responseObj.insertDataParser.tableDataParser.tableDataElements;
            for (tableElement in tableElements) {
                var currentDataElement = tableElements[tableElement];

                currentDataElement.rawData.dictionaryId = currentDataElement.dictionaryId;
                var selectedDictionaryItem = vm.currentDic.remove(function (item) {
                    return item.id() === parseInt(currentDataElement.dictionaryId);
                })[0];
                if(selectedDictionaryItem !== undefined){
                    vm.currentDic.push(selectedDictionaryItem);
                    currentDataElement.rawData.selectedDictionaryItem = selectedDictionaryItem;
                }


                currentDataElement.rawData.metaName = currentDataElement.metaName;
                currentDataElement.rawData.pageNumber = currentDataElement.rawData.page;
                data = {};
                data.pageNumber = ko.observable(currentDataElement.pageNumber);
                vm.changePage(data);
                vm.addTableElement(currentDataElement.rawData);
                for (column in currentDataElement.columns) {
                    if (currentDataElement.columns[column].rawData !== undefined) { //TODO:Delete this if condition after implementing raw data

                        currentDataElement.rawData.dictionaryId = currentDataElement.dictionaryId;
                        var selectedDictionaryItem = vm.currentDic.remove(function (item) {
                            return item.id === currentDataElement.dictionaryId;
                        })[0];
                        if(selectedDictionaryItem !== undefined){
                            vm.currentDic.push(selectedDictionaryItem);
                            currentDataElement.columns[column].rawData.selectedDictionaryItem = selectedDictionaryItem;
                        }


                        currentDataElement.columns[column].rawData.metaName = currentDataElement.columns[column].metaName;
                        currentDataElement.columns[column].rawData.page = currentDataElement.columns[column].page;
                        vm.addSubElement(currentDataElement.columns[column].rawData);
                        disappearSubDecos(currentDataElement.columns[column].rawData.id);
                    }
                }
                disappearDecos(currentDataElement.id);


            }
        }

        data = {};
        data.pageNumber = ko.observable(1);
        vm.changePage(data);
        resizeImage();
        resetEnvironment();


    }


    initBindings();
    $("button#cancelSelection").attr('disabled', true);
    $("button#saveSelection").attr('disabled', true);



    setTimeout(resetLongPage, 400);
})();