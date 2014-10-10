<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Mark Up Regions</title>
    <title></title>
    <!-------------------------------- CSS Files------------------------------------>
    <link rel="stylesheet" type="text/css" href="assets/css/bootstrap.css">

    <!-------------------------------- JS Files------------------------------------>
    <script type="text/javascript" src="assets/js/jquery-1.10.2.js"></script>
    <script type="text/javascript" src="assets/js/bootstrap.js"></script>

</head>
<body>

<h1>Sign In to Continue</h1>


<label for="userName">userName</label>
<input id="userName" name="userName" type="text" placeholder="user name"/> <br/>

<label for="pass">pass </label>
<input id="pass" name="pass" type="password" placeholder="password"/> <br/>

<input id="submit" type="button" value="Sign In">

<script>
    $(document).ready(function() {
        var data=null;
        $('#submit').click(function() {

            var userName= document.getElementById("userName").value;
            if(userName==""){
                alert("User Name is Required");
                return false;
            }

            var pass= document.getElementById("userName").value;
            if(pass==""){
                alert("PassWord is Required");
                return false;
            }

            data={ userName : $('#userName').val(),  pass  : $('#pass').val()  };

            $.ajax({
                type: 'POST', url: 'InitController',
                contentType: 'application/json; charset=utf-8',
                dataType: 'json',
                data: JSON.stringify(data),

                success: function(data, textStatus, jqXHR) {
                    var responseObj = JSON.parse(jqXHR.responseText);
                    if(responseObj.isAuthenticated===true){
                        window.location.href = responseObj.redirectUrl;
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
