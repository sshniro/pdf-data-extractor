var xhr = new XMLHttpRequest();

var SheetResponse;
xhr.onload = function () {
    if (xhr.status === 200) {
        SheetResponse =  xhr.responseText;
        console.log(SheetResponse);

    } else {
        alert('An error occurred!');
    }
};
