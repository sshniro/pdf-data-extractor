
function ViewModel(){
    
    var self = this;

    self.uploadAndGet = function () {

        var pageData = ko.to

        var form = document.getElementById('file-form');
        var fileSelect = document.getElementById('file-select');
        var files = [];
        var files = fileSelect.files;

        var formData = new FormData();
        var file = files[0];
        formData.append('excelFile', file);

        xhr.open('POST', 'http://localhost:36305/file/extract', false);
        xhr.send(formData);

    }

    self.setExtractedData = function (data) {


    }

}

var vm = new ViewModel();

$(window).ready(function(){

    excelVm = new ViewModel();
    ko.applyBindings(vm);

    setTimeout(resetLongPage, 400);

})

var resetLongPage = function(){
    $('a#PageAn2').click();
    $('a#PageAn1').click();
}