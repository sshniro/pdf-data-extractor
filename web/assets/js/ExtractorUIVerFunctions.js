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

    });

    //TABLE BUTTON
    $("button#tableSelect").click(function(){
        vm.currentSelection("table");
        selectionStarted();
        selectionInitializer("img",drawingRouter);       
    });

    //PICTURE BUTTON
    $("button#pictureSelect").click(function(){
        vm.currentSelection("picture");
        selectionStarted();
        selectionInitializer("img",drawingRouter);

    });

    //CANCEL BUTTON
    $("button#cancelSelection").click(function(){
        vm.cancelSelection();
        resetEnvironment();

    });


    $("button#saveSelection").click(function(){
        if(vm.elementBuffer !== undefined){
            $("div#"+vm.elementBuffer.id).unbind();
            disappearDecos(vm.elementBuffer.id)//Should be called before saveSelection because the buffer is cleared
        }
        resetEnvironment();
        vm.saveSelection();
    });

    $("img").bind('mousedown',function(){
        alert("Select Element Type");
    });

});




var selectionInitializer = (function (element,onSelectEndCb){

    selectedImgAreInstance = $(element).imgAreaSelect({
        onSelectChange: preview,
        onSelectEnd: onSelectEndCb,
        onSelectStart: selectionStartedCallBack,
        instance:true,
        autoHide: true   
    })

});


        

var drawingRouter = (function (baseUiComponent, selection){

    if (selection.width<10 || selection.height<10){
        return;
    }
    $("*").css('cursor','default');
    var rectangle = drawRectangle(baseUiComponent, selection);
    rectangle.pageNumber =  vm.currentPage();

    if(!vm.subElementSelectionInProgress()){
        var response = getMainExtraction(rectangle,vm.currentSelection());
        rectangle.extractedData = response.extractedData;
        rectangle.elementId = rectangle.id;

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
        selectionInitializer('div#'+rectangle.id+'.mainElement',drawingRouter);
    }
    else{//If table allow selection of multiple sub elements
        var response  = getSubExtraction(rectangle,vm.currentSelection());
        rectangle.relevantData = response.extractedData;
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






//Granular function to draw rectangle on the html DOM
var drawRectangle = (function(baseUiComponent, selection){



    var rectangle = {}
    rectangle.baseUiComponentStartX = $('#'+baseUiComponent.id +'.baseUI' ).offset().left;
    rectangle.baseUiComponentStartY = $('#'+baseUiComponent.id+'.baseUI').offset().top;
    rectangle.baseUiComponentHeight =baseUiComponent.height;
    rectangle.baseUiComponentWidth =baseUiComponent.width;

    rectangle.startX = selection.x1;
    rectangle.startY = selection.y1;
    rectangle.width =selection.width;
    rectangle.height = selection.height;

    rectangle.id = rectangle.startX +"px"+ rectangle.startY+"px"+rectangle.width+"px"+rectangle.height+"px";
    rectangle.elementType = vm.currentSelection();

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
    .mouseover(function() {
        elementDecoExtracted.fadeIn(600);
        elementDecoMeta.fadeIn(600);

    })
    .mouseout(function() {
        elementDecoExtracted.fadeOut(200);
            elementDecoMeta.fadeOut(600);
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

    $("#runningInstructions").attr('text', 'Select Data Type');
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

    $("#runningInstructions").text('Select Picture Element');  
    $('img').css('cursor','crosshair');
    $('img').unbind();
    $("button.removeElement").css('visibility','hidden');
    $("button.removeSubElement").css('visibility','hidden');
    vm.selectionInProgress(true);
    
}

function selectionComplete(currentElement){
    $("#runningInstructions").text('Click On Save Or Cancel');
    $("*").css('cursor','default');
    //var id = currentElement.elementId;

    //var closeDivId = id+"Remove";
    //var saveButtonId = id + "SaveButton";

    //$("button#"+saveButtonId).css('visibility', 'hidden');
    //$("div#"+closeDivId).css('visibility', 'visible');    
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
/*
    self.init = function(){
        for (var i = 0; i <500; i+=100) {
            var rectangle = {}
            rectangle.elementRectId = i+"px"+i+"px"
            rectangle.elementLeftStart=i;
            rectangle.elementTopStart =i;
            rectangle.elementWidth =i+100;
            rectangle.elementHeight = i+100;

            self.addElement(rectangle);
        }
    }
*/

function preview(img, selection) {
    if (!selection.width || !selection.height)
        return;

    $('#starting').text(selection.x1 +" "+ selection.y1);
    $('#ending').text(selection.x2 +" "+ selection.y2);
}