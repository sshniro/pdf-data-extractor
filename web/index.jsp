<%--
  User: K D K Madusanka
  Date: 10/26/2014
  Time: 10:51 AM
  ----------------------------------
  Log:
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Xractor</title>

    <!-- jQuery -->
    <script type="text/javascript" src="assets/js/jquery-1.10.2.js"></script>
    <!-- bootstrap -->
    <script type="text/javascript" src="assets/js/bootstrap.js"></script>
    <link rel="stylesheet" type="text/css" href="assets/css/bootstrap.css">
    <!-- jstree -->
    <script type="text/javascript" src="assets/ex-libraries/jstree/jstree.js"></script>
    <link rel="stylesheet" href="assets/ex-libraries/jstree/themes/default/style.css" />

    <!-- added -->
    <link href="assets/css/style.css" rel="stylesheet">
    <link href="assets/css/checkbox-x.css" rel="stylesheet">
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>
<body>

<header>
    <div class="navbar container-header">
        <div class="col-md-3">
            <img class="menu-logo" src="assets/img/images/logo.png" alt="" />
        </div>
        <div class="col-md-9">
            <%--<ul class="list-inline text-left header-main-menu pull-right">--%>
            <%--<li class="menu-width text-center">--%>
            <%--<a href="#">--%>
            <%--<img class="menu-icon" src="assets/img/images/m6.png" alt="" /> <br>--%>
            <%--<span class="menu-span text-center">Help</span>--%>
            <%--</a>--%>
            <%--</li>--%>

            <%--</ul>--%>
        </div>

        <!-- mobile menu -->

        <nav class="navbar navbar-default" role="navigation">
            <div class="container-fluid">
                <!-- Brand and toggle get grouped for better mobile display -->
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="#"></a>
                </div>

                <!-- Collect the nav links, forms, and other content for toggling -->
                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <ul class="nav navbar-nav">
                        <li><a href="#">Help</a></li>
                    </ul>
                </div><!-- /.navbar-collapse -->
            </div><!-- /.container-fluid -->
        </nav>
    </div>
    <div class="row"></div>
</header>

<div class="container-login">
    <div class="row">
        <div class="col-md-1"></div>
        <div class="col-md-4">
            <div class="well well-lg sign-cont-body" style="width: 500px; margin-top: 40%; margin-left:10%;   ">
                <form class="form-horizontal" role="form">
                    <fieldset>
                        <%--<legend>Data Extractor</legend>--%>
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
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" id="rememberMe" name="rememberMe"> Remember me
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-10">
                                <button class="btn btn-warning" id="submit" type="button">SIGN IN &nbsp;<span class="glyphicon glyphicon-log-in"></span></button>
                            </div>
                        </div>
                    </fieldset>
                </form>
            </div>
        </div>
        <div class="col-md-2"></div>
        <div class="col-md-5">
            <div  style="max-width: 800px; margin:10% auto;">
                <img style="width: 800px;" src="assets/img/login_logo.png" alt="" />

            </div>
        </div>
        <div class="col-md-1"></div>
    </div>


</div>

<footer>
    <div class="navbar container-footer">
        <div class="col-md-12">
            <div class="col-md-8">
                <%--<img class="menu-logo" style="width: 150px;  " src="assets/img/images/enhanzer_innovation.png" alt=""/></a>--%>
                <p class="copyright footer-h5">COPYRIGHT &copy; 2015 XTRACTOR</p>
            </div>
            <div class="col-md-4">
                <ul class="list-inline text-center">
                    <li class="text-center footer-img-li">
                    </li>
                    <li class="text-center footer-img-li">
                        <a href="https://www.facebook.com/pages/Enhanzer-Innovation/584253195050668"><img  class="footer-img" src="assets/img/images/facebook.png" style="width: 25px; height: 25px;" alt="" /> <br></a>
                    </li>
                    <li class="text-center footer-img-li">
                        <a href="https://twitter.com/enzinnov"><img class="footer-img"  style="width: 25px; height: 25px;" src="assets/img/images/twitter.png" alt="" /> <br></a>
                    </li>
                </ul>
            </div>
        </div>

    </div>
</footer>


<!-- importing libraries -->
<script type="text/javascript" src="assets/js/knockout-3.2.0.js" ></script>
<script type="text/javascript" src="assets/js/userManagementScripts/security.js" ></script>

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
            if(localStorage.getItem('xtractor_lastPage') === null){
                var loginReturn = login({username:userName, password:pass},$('#rememberMe').is(':checked'),localStorage.getItem('xtractor_lastPage'));
            }
            else{
                var loginReturn = login({username:userName, password:pass},$('#rememberMe').is(':checked'),'Home_Page.html');
            }

        });
    });
</script>

</body>
</html>
