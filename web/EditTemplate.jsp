<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <!-------------------------------- CSS Files------------------------------------>
    <link rel="stylesheet" type="text/css" href="assets/css/bootstrap.css">

    <!-------------------------------- JS Files------------------------------------>
    <script type="text/javascript" src="assets/js/jquery-1.10.2.js"></script>
    <script type="text/javascript" src="assets/js/bootstrap.js"></script>
    <script type="text/javascript" src="assets/js/JspFormPopulate.js"></script>
    <script type="text/javascript" src="assets/js/PageNavigator.js"></script>

</head>
<body>



<script>
    getTemplateInfo();
    function getTemplateInfo() {

        var CreateSCData = { id : '32'};
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
