


var effectiveController;

//Initialize the page depending on the context [edit,create]
$(window).ready(function(){




});


$( window ).ready(function() {
    setTimeout(resizeImage, 400);
});


var resizeImage = function(){
    var images = $('img.templatingImage');
    for(var image in images){
        var htmlImg = images[image];
        var id = htmlImg.id;
        $('img#'+id+'.templatingImage').css('minWidth',htmlImg.naturalWidth);
    }
}




//TODO: Delete this implementation below
var initBindings =  (function(){
    $("img").bind('mousedown',function(event){
        event.stopPropagation()
        alert("Select Element Type");

    });

});



// This activates the imgAreaSelect on a given element and is the main plugin which drives the element selection
var selectionInitializer = (function (element,onSelectEndCb, previousElementId){

    selectedImgAreInstance = $(element).imgAreaSelect({
        onSelectChange: preview,
        onSelectEnd: onSelectEndCb,
        onSelectStart: selectionStartedCallBack(previousElementId),
        instance:true,
        autoHide: true   
    })

});


var currentWorkingImg = undefined;


// The drawing router controls the workflow of the application
// It is invoked each time the image area select plugin fires its selection end event
// the base ui component is the current element on which was drawn
// selection contains the dimensions of the selection
var drawingRouter = (function (baseUiComponent, selection){

    if (selection.width<10 || selection.height<10){
        return;
    }

    currentWorkingImg = baseUiComponent;

    $(baseUiComponent).css('cursor','default');
    var rectangle = drawRectangle(baseUiComponent, selection);
    rectangle.pageNumber =  vm.currentPage();

    //If this is the very first selction after either of the  [text/image/table] buttons were selected
    // then it enters into this flow
    if(!vm.subElementSelectionInProgress()){
        var response = getMainExtraction(rectangle,vm.currentSelection());
        rectangle.extractedData = response.extractedData;
        rectangle.elementId = rectangle.id;
        //Releases the imageAreaSelect binding on the image
        // because the rest of the selection will be done inside the current selection element
        // until save or cancel is pressed
        $('#'+baseUiComponent.id).unbind();
        switch (vm.currentSelection()) {
            case 'text':
                vm.addTextElement(rectangle);
                break;
            case 'table':
                vm.addTableElement(rectangle);
                break;
            case 'picture':
                vm.addPictureElement(rectangle);
                break;
            case 'regex':
                vm.addRegexElement(rectangle);
                break;
            case 'pattern':
                vm.addPatternElement(rectangle);
                break;
        }

        vm.subElementSelectionInProgress(true);
        $('div#'+rectangle.id+'.mainElement').css('cursor','crosshair');
        // new selection is initialized to select WITHIN the currently selected element
        selectionInitializer('div#'+rectangle.id+'.mainElement',drawingRouter);
    }
    else{
        //This is the end of the work flow for elements other than the table

        var response  = getSubExtraction(rectangle,vm.currentSelection());
        rectangle.relevantData = response.extractedData;
        //The table selection allows to select an infinite amount of sub elements within the main table rectangle
        if (vm.currentSelection() === 'table') {
            rectangle.elementId = baseUiComponent.id;
            //Hide earlier meta for deco
            $(".subElementDecoMeta").hide();
            vm.addSubElement(rectangle)
            $('#'+rectangle.id).css('cursor','default');
            $('#'+baseUiComponent.id).css('cursor','crosshair');
            selectionInitializer('#'+baseUiComponent.id+'.mainElement',drawingRouter,rectangle.id);
        }
        else if(vm.currentSelection() === 'regex'){
            if(vm.currentProcessingSubElement()==''){   // if regex drawing start element
                rectangle.elementId = baseUiComponent.id;
                //Hide earlier meta for deco
                $(".subElementDecoMeta").hide();
                vm.addSubElement(rectangle)
                $('#'+rectangle.id).css('cursor','default');
                $('#'+baseUiComponent.id).css('cursor','crosshair');
                selectionInitializer('#'+baseUiComponent.id+'.mainElement',drawingRouter,rectangle.id);
            }
            else if(vm.currentProcessingSubElement() == 'NE'){  // if start element set to Normal element
                rectangle.elementId = baseUiComponent.id;
                // ######################## get extraction without drawing element
                effectiveController = 'MarkUpTemplateRegionController';
                var responseData = getMainExtraction(rectangle, 'text');

                // set extracted data
                vm.currentProcessingSubElement('');
            }
            else if(vm.currentProcessingSubElement() == 'RE'){  // if start element set to Normal element
                rectangle.elementId = baseUiComponent.id;
                //Hide earlier meta for deco
                $(".subElementDecoMeta").hide();
                vm.addSubElement(rectangle)
                $('#'+rectangle.id).css('cursor','default');
                $('#'+baseUiComponent.id).css('cursor','crosshair');
                selectionInitializer('#'+baseUiComponent.id+'.mainElement',drawingRouter,rectangle.id);
            }
            else if(vm.currentProcessingSubElement() == 'NNE'){  // if start element set to Normal element
                rectangle.elementId = baseUiComponent.id;
                //Hide earlier meta for deco
                $(".subElementDecoMeta").hide();
                vm.addSubElement(rectangle)
                $('#'+rectangle.id).css('cursor','default');
                $('#'+baseUiComponent.id).css('cursor','crosshair');
                selectionInitializer('#'+baseUiComponent.id+'.mainElement',drawingRouter,rectangle.id);
            }

        }
        else if(vm.currentSelection() === 'pattern'){
            rectangle.elementId = baseUiComponent.id;
            //Hide earlier meta for deco
            $(".subElementDecoMeta").hide();
            vm.addSubElement(rectangle)
            $('#'+rectangle.id).css('cursor','default');
            $('#'+baseUiComponent.id).css('cursor','crosshair');
            selectionInitializer('#'+baseUiComponent.id+'.mainElement',drawingRouter,rectangle.id);
        }
        else{
            rectangle.elementId = baseUiComponent.id;
            vm.addSubElement(rectangle)
            $('#'+baseUiComponent.id).unbind();
            selectionComplete();
        }
    }

})


//// for div draggability and resizability
var reDrawingRouter = (function (baseUiComponent, selection,removedElement){

    if (selection.width<10 || selection.height<10){
        return;
    }
    currentWorkingImg = baseUiComponent;
    $("*").css('cursor','default');
    var rectangle = reDrawRectangle(baseUiComponent, selection);
    rectangle.pageNumber =  vm.currentPage();

    //If this is the very first selction after either of the  [text/image/table] buttons were selected
    // then it enters into this flow

    if(!vm.subElementEditingInProgress()){
        var response = getMainExtraction(rectangle,vm.currentSelection());
        rectangle.extractedData = response.extractedData;
        rectangle.elementId = rectangle.id;
        rectangle.metaName = removedElement.metaName();
        rectangle.selectedDictionaryItem = removedElement.selectedDictionaryItem();

        //Releases the imageAreaSelect binding on the image
        // because the rest of the selection will be done inside the current selection element
        // until save or cancel is pressed
        $('#'+baseUiComponent.id).unbind();
        switch (vm.currentSelection()) {
            case 'text':
                vm.addTextElement(rectangle);
                break;
            case 'table':
                vm.addTableElement(rectangle);
                break;
            case 'picture':
                vm.addPictureElement(rectangle);
                break;
        }

        vm.subElementEditingInProgress(true);
        $('div#'+rectangle.id+'.mainElement').css('cursor','crosshair');
        // new selection is initialized to select WITHIN the currently selected element
        editComplete();
        selectionInitializer('div#'+rectangle.id+'.mainElement',reDrawingRouter);
    }
    else{
        //This is the end of the work flow for elements other than the table

        var response  = getSubExtraction(rectangle,vm.currentSelection());
        rectangle.relevantData = response.extractedData;
        //The table selection allows to select an infinite amount of sub elements within the main table rectangle
        if (vm.currentSelection() === 'table') {
            rectangle.elementId = baseUiComponent.id;
            vm.addSubElement(rectangle)
            $('#'+rectangle.id).css('cursor','default');
            $('#'+baseUiComponent.id).css('cursor','crosshair');
            selectionInitializer('#'+baseUiComponent.id+'.mainElement', reDrawingRouter, rectangle.id);
        }
        else{
            rectangle.elementId = baseUiComponent.id;
            vm.addSubElement(rectangle)
            $('#'+baseUiComponent.id).unbind();
        }
    }

})




//Granular function to draw rectangle on the html DOM
var drawRectangle = (function(baseUiComponent, selection){
    var rectangle = {}
    rectangle.baseUiComponentStartX     = $('#'+baseUiComponent.id +'.baseUI' ).offset().left;
    rectangle.baseUiComponentStartY     = $('#'+baseUiComponent.id+'.baseUI').offset().top;
    rectangle.baseUiComponentHeight     =baseUiComponent.clientHeight;
    rectangle.baseUiComponentWidth      =baseUiComponent.clientWidth;

    rectangle.startX                    = selection.x1+1 ;
    rectangle.startY                    = selection.y1+1;
    rectangle.width                     = selection.width + 4;
    rectangle.height                    = selection.height + 3;

    rectangle.id                        = rectangle.startX +"px"+ rectangle.startY+"px"+rectangle.width+"px"+rectangle.height+"px";
    rectangle.elementId                 = baseUiComponent.id;

    rectangle.elementType       = vm.currentSelection();
    rectangle.page = vm.currentPage();

    return rectangle;
})

//Granular function to re draw rectangle on the html DOM when dragging or resizing
var reDrawRectangle = (function(baseUiComponent, selection){
    var rectangle = {}
    rectangle.baseUiComponentStartX     = $('#'+baseUiComponent.id +'.baseUI' ).offset().left;
    rectangle.baseUiComponentStartY     = $('#'+baseUiComponent.id+'.baseUI').offset().top;
    rectangle.baseUiComponentHeight     =baseUiComponent.clientHeight;
    rectangle.baseUiComponentWidth      =baseUiComponent.clientWidth;

    rectangle.startX                    = selection.x1;
    rectangle.startY                    = selection.y1;
    rectangle.width                     =selection.width;
    rectangle.height                    = selection.height;

    rectangle.id                        = rectangle.startX +"px"+ rectangle.startY+"px"+rectangle.width+"px"+rectangle.height+"px";
    rectangle.elementType       = vm.currentSelection();

    return rectangle;
})


//Governs event hanler for disappearing elements
function disappearDecos(elementId){
    var elementDecoExtracted =  $("div#"+elementId+".elementDecoExtracted");
    var elementDecoMeta = $("div#"+elementId+".elementDecoMeta");
    var relevantElement = $("div#"+elementId+".mainElement");
    var subElementDecoMeta = $("div#"+elementId+".subElementDecoMeta");
    var subElement = $("div#"+elementId+".subElement");

    elementDecoMeta.hide();
    subElementDecoMeta.hide();
    elementDecoExtracted.hide();
    if(vm.currentSelection() === 'table') {
        relevantElement
            .click(function () {
                elementDecoExtracted.toggle();
                elementDecoMeta.toggle();
                subElementDecoMeta.toggle();
            })
            .mouseout(function () {
                elementDecoExtracted.hide();
                elementDecoMeta.hide();
                subElementDecoMeta.hide();
                $("div.subElement").css('background-color','rgba(0, 0, 0,0)');
            });
    }
    else{
        relevantElement
            .click(function () {
                elementDecoExtracted.toggle();
                elementDecoMeta.toggle();


            })
            .mouseout(function () {
                elementDecoExtracted.hide();
                elementDecoMeta.hide();
            });
    }
}


function disappearSubDecos(previousElementId){
    var elementDecoMeta = $("div#"+previousElementId+".elementDecoMeta");
    var subElementDecoMeta = $("div#"+previousElementId+".subElementDecoMeta");
    var relevantElement = $("div#"+previousElementId+".subElement");

    if(vm.currentSelection() === 'table'){
        relevantElement
            .click(function() {
                elementDecoMeta.toggle();
                subElementDecoMeta.toggle();

            })
            .mouseout(function() {
                elementDecoMeta.hide();
                subElementDecoMeta.hide();
            });
    }




}

function bindSubElementAppearing(elementId){
    var elementDecoExtracted =  $("div#"+elementId+".elementDecoExtracted");
    var elementDecoMeta = $("div#"+elementId+".elementDecoMeta");
    var relevantElement = $("div#"+elementId+".mainElement");
    relevantElement
        .click(function() {
            elementDecoExtracted.toggle();
            elementDecoMeta.toggle();

        })
        .mouseout(function() {
            elementDecoExtracted.hide();
            elementDecoMeta.hide();
        });
}


function resetEnvironment(){
    var currentElement = vm.elementBuffer;
    $("button#textSelect").attr('disabled', false);

    $("button#tableSelect").attr('disabled', false);
    $("button#pictureSelect").attr('disabled', false);
    $("button#textSelect").removeClass("active");
    $("button#tableSelect").removeClass("active");
    $("button#pictureSelect").removeClass("active");

    $("#runningInstructions").text('Select Element Type');
    $("div.subElement").css('background-color','rgba(0, 0, 0,0)');


    $("button#cancelSelection").attr('disabled', true);
    $("button#saveSelection").attr('disabled', true);
    $("button#enableEditableDivs").attr('disabled', false);

    $("button#persist").attr('disabled', false);
    $("button.removeElement").css('visibility','visible');
    $("button.removeSubElement").css('visibility','hidden');
    $("button.selectSubElement").css('visibility','hidden');




     $("div.mainElement").css( "zIndex", 1 );
    $('*').css('cursor','default');
    $("img").unbind();

    if ($('.editableDiv').data('draggable')) {
        $('.editableDiv').draggable('disable')
    }

    initBindings();

    vm.currentSelection = ko.observable();
    vm.selectionInProgress(false);
    vm.subElementSelectionInProgress(false);

    vm.editingInProgress(false);
    vm.subElementEditingInProgress(false)
   
}

function selectionStarted(currentElement){
    $("button.removeElement").css('visibility','hidden');
    $("button#textSelect").attr('disabled', true);  
    $("button#tableSelect").attr('disabled', true);
    $("button#pictureSelect").attr('disabled', true);  
    $("button#tableSelect").attr('disabled', true);
    $("button#enableEditableDivs").attr('disabled', true);

    $("button#cancelSelection").attr('disabled', false);
    $("button#saveSelection").attr('disabled', false);
    $("button#persist").attr('disabled', true);

    $('img').css('cursor','crosshair');
    $('img').unbind();
    $("button.removeElement").css('visibility','hidden');
    $("button.removeSubElement").css('visibility','hidden');
    vm.selectionInProgress(true);
    
}



function editStarted(currentElement){

    $("button.removeElement").css('visibility','hidden');
    $("button#textSelect").attr('disabled', true);
    $("button#tableSelect").attr('disabled', true);
    $("button#pictureSelect").attr('disabled', true);
    $("button#tableSelect").attr('disabled', true);
    $("button#enableEditableDivs").attr('disabled', true);

    $("button#cancelSelection").attr('disabled', true);
    $("button#saveSelection").attr('disabled', false);
    $("button#persist").attr('disabled', true);

    $("#runningInstructions").text('Edit Existing Elements');
    $(".editableDiv").css('cursor','grab');
    $('img').unbind();
    $("button.removeElement").css('visibility','hidden');
    $("button.removeSubElement").css('visibility','hidden');

    vm.editingInProgress(true);

}

function selectionComplete(currentElement){
    $("#runningInstructions").text('Click On Save Or Cancel');
    $("*").css('cursor','default');
}

function editComplete(){
    $("#runningInstructions").text('Click On Save to Complete');
    $('.editableDiv').each(function(item){
        if ($(this).data('ui-draggable')) {
            $(this).draggable('destroy')
        }
        if ($(this).data('ui-resizable')) {
            $(this).resizable('destroy')
        }
    });

    $("*").css('cursor','default');
}

function draggableActivator(){

    var currentWorkingImgX = $('img#'+vm.currentPage()+'.baseUI' )[0];

    $('.editableDiv').each(
        function(){
                $(this).draggable({
                stop: function( event, ui ) {
                    var dragableDiv = $(this)[0];
                    var bounds = dragableDiv.getBoundingClientRect();
                    var imgBounds = currentWorkingImgX.getBoundingClientRect();
                    var selection = {
                        height:bounds.height,
                        width:bounds.width,
                        x1:bounds.left - imgBounds.left,
                        x2:imgBounds.right - bounds.right ,
                        y1:bounds.top - imgBounds.top,
                        y2:imgBounds.bottom - bounds.bottom
                    };

                    var removedElement = vm.removeElementUsingDomElement(dragableDiv);
                    reDrawingRouter(currentWorkingImgX,selection,removedElement);
                    $(this).next('button').click();
                }
            });
            $(this).resizable({
                start:function(){
                    $(".editableDiv").css('cursor','grabbing');
                },
                stop: function( event, ui ) {
                    $(".editableDiv").css('cursor','grab');
                    var dragableDiv = $(this)[0];
                    var bounds = dragableDiv.getBoundingClientRect();
                    var imgBounds = currentWorkingImgX.getBoundingClientRect();
                    var selection = {
                        height:bounds.height,
                        width:bounds.width,
                        x1:bounds.left - imgBounds.left,
                        x2:imgBounds.right - bounds.right ,
                        y1:bounds.top - imgBounds.top,
                        y2:imgBounds.bottom - bounds.bottom
                    };

                    var removedElement = vm.removeElementUsingDomElement(dragableDiv);
                    reDrawingRouter(currentWorkingImgX,selection,removedElement);
                    $(this).next('button').click();
                }
            });
        }
    );
}

//PLUGINS AND TWEEKS
//Knockout Tweeks
ko.bindingHandlers.id = {
    init: function(element, valueAccessor) {
        
        var value = ko.unwrap(valueAccessor()); 
        $(element).attr("id",value);
    },
    update: function(element, valueAccessor) {
        var value = ko.unwrap(valueAccessor()); 
        $(element).attr("id",value);
    }
};

$('img').on('dragstart', function(event) { event.preventDefault(); });

$(".btn-group > .btn").click(function(){
    $(".btn-group > .btn").removeClass("active");
    $(this).addClass("active");
});

var selectionStartedCallBack = function (previousElementId){
    $('#'+previousElementId).css('cursor','crosshair');
    if(vm.subElementSelectionInProgress() === true && previousElementId !== undefined){
        disappearSubDecos(previousElementId);
    }
}

function preview(img, selection) {
    if (!selection.width || !selection.height)
        return;
    $('#starting').text(selection.x1 +" "+ selection.y1);
    $('#ending').text(selection.x2 +" "+ selection.y2);
}

$("button.selectSubElement").click(function (event ) {
    event.stopPropagation();
});

$("button.removeSubElement").click(function (event ) {
    event.stopPropagation();
});



/////////////////////////////////////////////////////////////////////////////
/////////////////////////    js tree functions    ///////////////////////////
/////////////////////////////////////////////////////////////////////////////

var initTrees = function(){
    var treeObj;
    var data={ 'request' : "getAllNodes"};
    $.ajax({
        type: 'POST', url: 'ManageCategoriesController',
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        data: JSON.stringify(data),
        success: function(data, textStatus, jqXHR) {
            treeObj = JSON.parse(jqXHR.responseText);
            $('.treeView')
                .jstree({
                    'plugins': ["search", "state", "types", "wholerow"],
                    'core' : {'data' : treeObj.nodes},
                    "types" : {
                        "default" : { "icon" : "glyphicon glyphicon-folder-open" },
                        "leaf": {"icon" : "glyphicon glyphicon-list-alt" }
                    }
                })
                .on("changed.jstree", function (e, data) {
                    if(data.selected.length) {
                        selectedNodeRow = data.instance.get_node(data.selected[0]);
                        selectedNodeChildRow = data.instance.get_node(selectedNodeRow.children[0]);
                        selectedNodeParentRow = data.instance.get_node(selectedNodeRow.parent);
                        vm.setCurrentSelectedTreeNode(selectedNodeRow);
                        vm.currentNodeParent(selectedNodeParentRow);
                        vm.setIsSelectedTemplate();
                    }
                })
                .on("ready.jstree", function (e, data) {
                    var x = data;
                });
        }
    });
};

//Clicks two pages so that images are properly intialized
var resetLongPage = function(){
    $('a#PageAn2').click();
    $('a#PageAn1').click();
}

var stopEventPropergation = function(event){
    event.stopPropagation();
}