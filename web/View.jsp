
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

    <script>
        var isMCValid = true;
        window.onload = onLoadFuncs();
        function onLoadFuncs(){
            getMCList();
        }
    </script>
</head>
<body>
<div class="row">

    <nav class="navbar navbar-default" role="navigation">
        <div class="container-fluid">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span>
                </button>
                <a class="navbar-brand" href="#">Enhanzer</a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
                    <li ><a id="templateExtract" href="#">Extract Doc</a></li>
                    <li><a id="templateUpload" href="#">Create Template</a></li>
                    <li class="dropdown active">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">Manage Categories <span class="caret"></span></a>
                        <ul class="dropdown-menu" role="menu">
                            <li class="divider"></li>
                            <li><a id="view" href="#">Summary</a></li>
                            <li class="divider"></li>
                            <li><a id="manageCategories" href="#">Create</a></li>
                            <li class="divider"></li>
                            <li><a id="remove" href="#">Remove</a></li>
                        </ul>
                    </li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="#">Logout</a></li>
                </ul>
            </div><!-- /.navbar-collapse -->
        </div><!-- /.container-fluid -->
    </nav>

<div class="col-md-12">
    <h3>Div tag to View All The Main and Relevant Sub Categories and TemplateNames</h3>
    <form >

        <select id="showMCList" name="showMCList" onchange="getSCList(this)" size="20" style="width: 300px;">
        </select>


        <select id="showSCList" name="showSCList" onchange="getTempList(this)" size="20" style="width: 300px;">
        <option value="" disabled selected> Sub Category List </option>
        </select>


        <select id="showTempList" name="showTempList" size="20" style="width: 300px;">
            <option value="" disabled selected> Template List </option>
        </select>

    </form>
</div>
</div>
</body>
</html>
