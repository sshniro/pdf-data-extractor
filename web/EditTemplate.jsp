<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <!-- jQuery -->
    <script type="text/javascript" src="assets/js/jquery-1.10.2.js"></script>
    <!-- bootstrap -->
    <script type="text/javascript" src="assets/js/bootstrap.js"></script>
    <link rel="stylesheet" type="text/css" href="assets/css/bootstrap.css">

    <script type="text/javascript" src="assets/js/JspFormPopulate.js"></script>
    <script type="text/javascript" src="assets/js/PageNavigator.js"></script>

</head>
<body>



<script>
    getTemplateInfo();
    function getTemplateInfo() {

        var CreateSCData = { id : localStorage.getItem('selectedTemplateId')};
        $.ajax({
            type: 'POST', url: 'EditTemplateController',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            data: JSON.stringify(CreateSCData),
            success: function (data, textStatus, jqXHR) {
                var messages = JSON.parse(jqXHR.responseText);
                alert(JSON.stringify(messages));
            }
        });
    }
</script>

</body>
</html>
