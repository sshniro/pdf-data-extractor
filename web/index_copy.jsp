<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Enhanzer Extractor</title>
    <title></title>
    <!-------------------------------- CSS Files------------------------------------>
    <link rel="stylesheet" type="text/css" href="assets/css/bootstrap.css">

    <!-------------------------------- JS Files------------------------------------>
    <script type="text/javascript" src="assets/js/jquery-1.10.2.js"></script>
    <script type="text/javascript" src="assets/js/markUpTemplateRegionsScripts/customBundle.js"></script>

</head>
<body>

<div class="container">
    <div class="well well-lg" style="max-width: 500px; margin:10% auto;">
        <form class="form-horizontal" role="form">
            <fieldset>
                <legend>Data Extractor</legend>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="userName">Username</label>
                    <div class="col-sm-10">
                        <input class="form-control" id="userName" name="userName" type="text" placeholder="username" autofocus="autofocus" required />
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="pass">Password</label>
                    <div class="col-sm-10">
                        <input class="form-control" id="pass" name="pass" type="password" placeholder="password" required />
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button class="btn btn-default" id="submit" type="button">SIGN IN &nbsp;<span class="glyphicon glyphicon-log-in"></span></button>
                    </div>
                </div>
            </fieldset>
        </form>
    </div>
</div>








<script>
    $(document).ready(function() {
        var data=null;
        $('#submit').click(function() {

            var userName= document.getElementById("userName").value;
            if(userName==""){
                alert("User Name is Required");
                return false;
            }

            var pass= document.getElementById("pass").value;
            if(pass==""){
                alert("PassWord is Required");
                return false;
            }

            data={ userName : $('#userName').val(),  pass  : $('#pass').val() , request : "login" };

            $.ajax({
                type: 'POST', url: 'SessionController',
                contentType: 'application/json; charset=utf-8',
                dataType: 'json',
                data: JSON.stringify(data),

                success: function(data, textStatus, jqXHR) {
                    var responseObj = JSON.parse(jqXHR.responseText);
                    if(responseObj.isAuthenticated===true){
                        window.location.href = "default.jsp";
                    }else{
                        alert(responseObj.errorCause);
                    }
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
        });
    });
</script>

</body>
</html>
