var form = document.getElementById('file-form');
var fileSelect = document.getElementById('file-select');
var uploadButton = document.getElementById('upload-button');
var files = [];
var xhr = new XMLHttpRequest();
form.onsubmit = function(event) {
    event.preventDefault();
    var files = fileSelect.files;

    // Create a new FormData object.
    var formData = new FormData();
    var file = files[0];
    formData.append('excelFile', file);

    xhr.open('POST', 'http://localhost:36305/file/extract', false);
    xhr.send(formData);
}

var response;
xhr.onload = function () {
    if (xhr.status === 200) {
        response =  xhr.responseText;
        console.log(response);

    } else {
        alert('An error occurred!');
    }
};
