/**
 * Created by niro273 on 9/9/14.
 */
/* Post AJAX DATA if any Navigation Anchor tag is pressed */
$(document).ready(function() {
    $('#home').click(function() {
        var data={redirect:"index"};
        ajaxPost(data);

    });
    $('#manageCategories').click(function() {
        var data={redirect:"ManageCategories"};
        ajaxPost(data);
    });
    $('#templateExtract').click(function() {
        var data={redirect:"ExtractPdf"};
        ajaxPost(data);

    });
    $('#templateUpload').click(function() {
        var data={redirect:"TemplateUpload"};
        ajaxPost(data);
    });
    $('#remove').click(function() {
        var data={redirect:"Remove"};
        ajaxPost(data);
    });
    $('#view').click(function() {
        var data={redirect:"View"};
        ajaxPost(data);
    });
});
/* Function to redirect the page for Navigation controll*/
function ajaxPost(data){

    $.ajax({
        type: 'POST', url: 'NavigationController',
        contentType: 'application/json; charset=utf-8',
        //dataType: 'json',
        data: JSON.stringify(data),
        success: function(data, textStatus, jqXHR) {
            window.location.href = jqXHR.responseText;
        },
        error: function(jqXHR, textStatus, errorThrown) {
            if(jqXHR.status == 400) {
                var messages = JSON.parse(jqXHR.responseText);
                $('#messages').empty();
                $.each(messages, function(i, v) {
                    var item = $('<li>').append(v);
                    $('#messages').append(item);
                });
            } else {
                alert('Unexpected server error.');
            }
        }
    });
}