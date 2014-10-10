<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <!-------------------------------- CSS Files------------------------------------>
    <link rel="stylesheet" type="text/css" href="assets/css/bootstrap.css">

    <!-------------------------------- JS Files------------------------------------>
    <script type="text/javascript" src="assets/js/jquery-1.8.2.js"></script>
    <script type="text/javascript" src="assets/js/bootstrap.js"></script>

</head>
<body>

<a href="#" id="home">Home Page</a> </br>
<a href="#" id="templateExtract">Extract Template</a> </br>
<a href="#" id="templateUpload">Create Template</a> </br>

<script>
    $(document).ready(function() {
        $('#home').click(function() {
            var data={redirect:"home"};
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
    });

    function ajaxPost(data){

        $.ajax({
            type: 'POST', url: 'TestServlet',
            contentType: 'application/json; charset=utf-8',
            //dataType: 'json',
            data: JSON.stringify(data),
            success: function(data, textStatus, jqXHR) {
                alert(jqXHR.responseText);
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
</script>

</body>
</html>
