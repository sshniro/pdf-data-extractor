//Global Variables
//Starting pixel coordinates of the ui component drawn upon
var uiCompStartingX;
var uiCompStartingY;

//current imgareSelection instance
var selectedImgAreInstance = undefined;

//selectionBooleans
//Table//Text//Picture
var currentSelection = ko.observable("text");

//selection in progress variable
var selectionInProgress = ko.observable(false);
var mainRectSelectionDone = ko.observable(false);
var labelSelectionDone = ko.observable(false);

//Bufers
var currentElement = undefined;

// Models
function DataElement(data){
    var self = this;
    
    self.elementId  = ko.observable(data.elementId);
    self.rectId = ko.observable(data.rectId);
    self.elementType = ko.observable(data.elementType);

    self.leftStart = ko.observable(data.leftStart);
    self.topStart= ko.observable(data.topStart);
    self.width = ko.observable(data.width);
    self.height = ko.observable(data.height);
    self.extractedData = ko.observable(data.extractedData);
    self.labelExtractedData = ko.observable(data.labelExtractedData);
    self.relevantData = ko.observable(data.relevantData);
    self.metaName = ko.observable(data.metaName);

    self.labelElement = data.labelElement ?(new DataElement(data.labelElement)):undefined;

    //ui Data

    self.metaStart = ko.computed(function(){
        return self.topStart() - 75
    });
    self.bottom = ko.computed(function(){
        return self.topStart() + self.height()
    });

    self.right = ko.computed(function(){
        return self.leftStart() + self.width()
    });

    self.removeLeft = ko.computed(function(){
        return self.right() - 15
    });

    self.removeTop = ko.computed(function(){
        return self.topStart() - 15
    });


    self.elementMap = ko.computed(function(){
        var styleObject = {
            left    : self.leftStart(),
            top     : self.topStart(),
            width   : self.width() ,
            height  : self.height()
        }
        return styleObject;
    });

    self.labelMap = ko.computed(function(){
        if(self.labelElement){
            var styleObject = {
                left    : self.labelElement.leftStart() - self.leftStart(),
                top     : self.labelElement.topStart() - self.topStart(),
                width   : self.labelElement.width() ,
                height  : self.labelElement.height()
            }
            return styleObject;
        }
        else{
            return undefined;
        }
    });



    
}



function ViewModel(){
    var self = this;

    self.dataElements = ko.observableArray([]);

    self.addElement = function (data){
        var element = new DataElement(data);
        self.dataElements.push(element);

    }

    self.removeElement = function (data){
        var removedElement = self.dataElements.remove(function(item) { return item.elementId() ==  ko.utils.unwrapObservable(data.elementId) })
        return removedElement[0];
    }

    self.selectCurrentType = function(element,selection){
        currentSelection(selection);
        selectionInProgress(true);
        $("div#elementTypeSelector :button").attr('disabled', true);
    }
    self.cancelSelection = function(){
        selectionInProgress(false);
        $("div#elementTypeSelector :button").attr('disabled', false);
    }

    self.saveElement = function(){
        currentSelection(undefined);
        selectionInProgress(false);
        mainRectSelectionDone(false);
        labelSelectionDone(false);
        disappearDecos(currentElement);
        resetEnvironment(currentElement);
        currentElement = undefined;
        selectedDivInstance.setOptions({disable:true, hide:true});
        selectedDivInstance.update();
        selectedDivInstance = undefined;
    }
}

var vm = new ViewModel();
ko.applyBindings(vm);

//Basic Functionality
$(document).ready(function () {
//Function Flow
    //Select a data type
    $("button#textSelect").click(function(){
        currentSelection("text");
        selectionInProgress(true);
        $("button#tableSelect").attr('disabled', true); 
        $("button#pictureSelect").attr('disabled', true);
        $("#runningInstructions").text('Select Text Element');

    })

    $("button#tableSelect").click(function(){
        currentSelection("table");
        selectionInProgress(true);
        $("button#pictureSelect").attr('disabled', true);
        $("button#textSelect").attr('disabled', true);
        $("#runningInstructions").text('Select Table Element');        
    })

    $("button#pictureSelect").click(function(){
        currentSelection("picture");
        selectionInProgress(true);
        $("button#textSelect").attr('disabled', true);  
        $("button#tableSelect").attr('disabled', true);
        $("#runningInstructions").text('Select Picture Element');  

    })

    $("button#cancelSelection").click(function(){
        currentSelection(undefined);
        selectionInProgress(false);
        resetEnvironment(currentElement);
        if (selectedImgAreInstance) {
            selectedImgAreInstance.cancelSelection()
            selectedImgAreInstance = undefined;
            mainRectSelectionDone(false)
            $("#runningInstructions").attr('text', 'Select Data Type');
            
        };
        vm.removeElement(currentElement);
    })


    $("button.SaveButton").click(function(){

        
     
    })             




    selectedImgAreInstance = $('img#image').imgAreaSelect({
        onSelectChange: preview,
        onSelectEnd: drawRectangle,
        instance:true,
        autoHide: true
    });


    /*$("img#image").mousedown(function(){
        if (!selectionInProgress()) {
            alert("Select Data type");
            if(selectedImgAreInstance){
                selectedImgAreInstance.update();
            }
        }
    });*/

    function drawRectangle(baseUiComponent, selection){

        if (!selection.width || !selection.height)
        return;

        var uiCompStartingX = $('#'+baseUiComponent.id).offset().left;
        var uiCompStartingY = $('#'+baseUiComponent.id).offset().top;

        var rect = {}
        rect.width =selection.width-2;
        rect.height = selection.height-2;        
        

        if(selectionInProgress() == true && mainRectSelectionDone() ==  false){
        //Selection of initial element


            rect.leftStart= selection.x1 + uiCompStartingX+1;
            rect.topStart = selection.y1 + uiCompStartingY+1;
            var extractedData;
            $.ajax({
              url: "extract",///////////////////INSERT EXTRACTION URL  HERE
              data: rect,
              async:false
            }) .fail(function() {
              extractedData="ajax extraction failed";
              console.log("ajax extraction failed");
            })
            .done(function(data) {
              extractedData =data;
            });

            rect.extractedData=extractedData;
            rect.labelExtractedData = "Label Not Selected";
            rect.relevantData = "Label Not Selected";


            rect.rectId = rect.leftStart +"px"+ rect.topStart+"px"+rect.width+"px"+rect.height+"px";
            rect.elementId = rect.rectId;

            rect.elementType = currentSelection();

            
       
            
            vm.addElement(rect);
            mainRectSelectionDone(true)
            currentElement = rect;

            //disappearDecos()

            //Select inner element now
            selectedDivInstance = $('div#'+currentElement.elementId+'.element').imgAreaSelect({
                onSelectChange: preview,
                onSelectEnd: drawRectangle,
                instance:true,
                autoHide: true
            });


        }
        else if(selectionInProgress() == true && mainRectSelectionDone() == true){
        //Selection of inner element
            rect.elementId = currentElement.elementId;
            rect.leftStart = selection.x1 +currentElement.leftStart +1;
            rect.topStart = selection.y1 + currentElement.topStart +1;


            var extractedData;
            $.ajax({
              url: "extract",///////////////////INSERT EXTRACTION URL  HERE
              data: rect,
              async:false
            }) .fail(function() {
              extractedData="ajax extraction failed";
              console.log("ajax extraction failed");
            })
            .done(function(data) {
              extractedData =data;
            });

            rect.rectId = rect.leftStart +"px"+ rect.topStart+"px"+rect.width+"px"+rect.height+"px";
            currentElement.labelElement = rect;
            currentElement.labelExtractedData = extractedData;
            currentElement.relevantData = extractedData;              
            
            labelObject = new DataElement(currentElement);

            var currentRemovedElement =  vm.removeElement(currentElement);
            vm.addElement(currentElement);
            labelSelectionDone(true);
        }
        

    }

   function preview(img, selection) {
        if (!selection.width || !selection.height)
            return;

        $('#starting').text(selection.x1 +" "+ selection.y1);
        $('#ending').text(selection.x2 +" "+ selection.y2);
    }
    


});


//Ui Plugins
//Radio Buttons behaviour enhanced
$(".btn-group > .btn").click(function(){
    $(".btn-group > .btn").removeClass("active");
    $(this).addClass("active");
});

//Mouse over on element
function disappearDecos(currentElement){

    var id = currentElement.elementId;
    var metaid = id + "Meta";
    var removeid =id + "Remove";
    var extractid =id + "Extracted";

      $("div#"+metaid+".elementDeco").fadeOut(200)
      $("div#"+extractid+".elementDeco").fadeOut(200)

    $( "div#"+currentElement.elementId+".element")
    .mouseover(function() {
      $("div#"+metaid+".elementDeco").fadeIn(200)
      $("div#"+extractid+".elementDeco").fadeIn(200)
    })
    .mouseout(function() {
      $("div#"+metaid+".elementDeco").fadeOut(200)
      $("div#"+extractid+".elementDeco").fadeOut(200)
    });


}


function resetEnvironment(currentElement){
    $("button#textSelect").attr('disabled', false);
    $("button#tableSelect").attr('disabled', false);
    $("button#pictureSelect").attr('disabled', false);
    $("button#textSelect").removeClass("active");
    $("button#tableSelect").removeClass("active");
    $("button#pictureSelect").removeClass("active");
    $("#runningInstructions").text('Select Element Type');
    var id = currentElement.elementId;

    var closeDivId = id+"Remove";
    var saveButtonId = id + "SaveButton";

    $("button#"+saveButtonId).css('visibility', 'hidden');
    $("div#"+closeDivId).css('visibility', 'visible');    
}

$('img').on('dragstart', function(event) { event.preventDefault(); });
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

/*
    self.init = function(){
        for (var i = 0; i <500; i+=100) {
            var rect = {}
            rect.elementRectId = i+"px"+i+"px"
            rect.elementLeftStart=i;
            rect.elementTopStart =i;
            rect.elementWidth =i+100;
            rect.elementHeight = i+100;

            self.addElement(rect);
        }
    }
*/