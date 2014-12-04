//Global Variables
//Starting pixel coordinates of the ui component drawn upon
var uiElementStartingX;
var uiElementStartingY;




//current imgareSelection instance
var selectedImgAreInstance = undefined;


//Bufers
var currentElement = undefined;

//Jquery Event handlers
//Radio Buttons behaviour enhanced
var imageNaturalHeight;
var imageNaturalWidth;

$( window ).load(function() {

});
$( window ).ready(function() {

    setTimeout(resizeImage, 400);
});
$( window ).resize(function() {
    resizeImage();
});

var resizeImage = function(){
    var images = $('img.templatingImage');
    for(var image in images){
        var htmlImg = images[image];
        var id = htmlImg.id;
        $('img#'+id+'.templatingImage').css('minWidth',htmlImg.naturalWidth);
    }
}

$('img#templatingImage').load(function() {
    imageNaturalHeight = $('img#templatingImage')[0].naturalHeight;
    imageNaturalWidth = $('img#templatingImage')[0].naturalWidth;
    $('img').css('minWidth' ,imageNaturalWidth);
    $('img').css('minHeight', imageNaturalHeight);
});

$('img#templatingImage').ready(function() {
    $('img').css('minWidth' ,imageNaturalWidth);
    $('img').css('minHeight', imageNaturalHeight);
});

$(document).ready(function(){

    initBindings();
    $("button#cancelSelection").attr('disabled', true);
    $("button#saveSelection").attr('disabled', true);        
});

var initBindings =  (function(){
        //TEXT BUTTON
    $("button#textSelect").click(function(){
        vm.currentSelection("text");
        selectionStarted();
        selectionInitializer("img",drawingRouter);
        $("#runningInstructions").text('Select Text Element');


    });

    //TABLE BUTTON
    $("button#tableSelect").click(function(){
        vm.currentSelection("table");
        selectionStarted();
        selectionInitializer("img",drawingRouter);
        $("#runningInstructions").text('Select Table Element');

    });

    //PICTURE BUTTON
    $("button#pictureSelect").click(function(){
        vm.currentSelection("picture");
        selectionStarted();
        selectionInitializer("img",drawingRouter);
        $("#runningInstructions").text('Select Picure Element');


    });

    //CANCEL BUTTON
    $("button#cancelSelection").click(function(){
        vm.cancelSelection();
        resetEnvironment();

    });


    $("button#saveSelection").click(function(){
        if(vm.elementBuffer !== undefined){
            $("div#"+vm.elementBuffer.id+".mainElement").unbind();
            disappearDecos(vm.elementBuffer.id)//Should be called before saveSelection because the buffer is cleared
        }
        resetEnvironment();
        vm.saveSelection();
    });

    $("img").bind('mousedown',function(){
        alert("Select Element Type");
    });

});



// This activates the imgAreaSelect on a given element and is the main plugin which drives the element selection
var selectionInitializer = (function (element,onSelectEndCb){

    selectedImgAreInstance = $(element).imgAreaSelect({
        onSelectChange: preview,
        onSelectEnd: onSelectEndCb,
        onSelectStart: selectionStartedCallBack,
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

    $("*").css('cursor','default');
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
            vm.addSubElement(rectangle)
            $('#'+rectangle.id).css('cursor','default');
            $('#'+baseUiComponent.id).css('cursor','crosshair');
            selectionInitializer('#'+baseUiComponent.id+'.mainElement',drawingRouter);
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
var reDrawingRouter = (function (baseUiComponent, selection, editDiv){

    drawingRouter(baseUiComponent, selection);
    //vm.removeElement(editDiv);

})



//Granular function to draw rectangle on the html DOM
var drawRectangle = (function(baseUiComponent, selection){
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




//Mouse over on element
function disappearDecos(elementId){
    var elementDecoExtracted =  $("div#"+elementId+".elementDecoExtracted");
    var elementDecoMeta = $("div#"+elementId+".elementDecoMeta");
    var relevantElement = $("div#"+elementId+".mainElement");
    elementDecoMeta.hide();
    elementDecoExtracted.hide();
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

    $("button#cancelSelection").attr('disabled', true);
    $("button#saveSelection").attr('disabled', true);
    $("button#persist").attr('disabled', false);

    $("button.removeElement").css('visibility','visible');
    $("button.removeSubElement").css('visibility','hidden');


     $("div.mainElement").css( "zIndex", 1 );
    $('*').css('cursor','default');
    $("img").unbind();
    initBindings();

    vm.currentSelection = ko.observable();
    vm.selectionInProgress(false);
    vm.subElementSelectionInProgress(false)
   
}

function selectionStarted(currentElement){
    $("button.removeElement").css('visibility','hidden');
    $("button#textSelect").attr('disabled', true);  
    $("button#tableSelect").attr('disabled', true);
    $("button#pictureSelect").attr('disabled', true);  
    $("button#tableSelect").attr('disabled', true);


    $("button#cancelSelection").attr('disabled', false);
    $("button#saveSelection").attr('disabled', false);
    $("button#persist").attr('disabled', true);

    $('img').css('cursor','crosshair');
    $('img').unbind();
    $("button.removeElement").css('visibility','hidden');
    $("button.removeSubElement").css('visibility','hidden');
    vm.selectionInProgress(true);
    
}

function selectionComplete(currentElement){
    $("#runningInstructions").text('Click On Save Or Cancel');
    $("*").css('cursor','default');
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

var selectionStartedCallBack = function (){
    $("*").css('cursor','crosshair');
}

function preview(img, selection) {
    if (!selection.width || !selection.height)
        return;
    $('#starting').text(selection.x1 +" "+ selection.y1);
    $('#ending').text(selection.x2 +" "+ selection.y2);
}



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
            $('#treeViewDiv')
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