<%--
  User: K D K Madusanka
  Date: 11/11/2014
  Time: 11:43 AM
  ----------------------------------
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Xractor/Data Dictionary</title>

    <!-- jQuery -->
    <script type="text/javascript" src="assets/js/jquery-1.10.2.js"></script>
    <!-- bootstrap -->
    <script type="text/javascript" src="assets/js/bootstrap.js"></script>
    <link rel="stylesheet" type="text/css" href="assets/css/bootstrap.css">

    <!-- added -->
    <link href="assets/css/style.css" rel="stylesheet">
    <link href="assets/css/checkbox-x.css" rel="stylesheet">
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

    <!-- custom css -->
    <style type="text/css">
        body{
            font-family: "Calibri";
        }
        .box{
            border: 1px solid #a0a0a0;
            height: 80vh;
            width: 100%;
            padding: 5px;
            overflow-y: auto;
        }
        .box header{
            font-size: 1.2em;
            font-weight: bolder;
        }
    </style>
</head>
<body>

<!-- nav bar -->
<header>
    <div class="navbar container-header">
        <div class="col-md-3">
            <img class="menu-logo" src="assets/img/images/logo.png" alt="" />
        </div>
        <div class="col-md-9">
            <ul class="list-inline text-left header-main-menu">
                <li class="menu-width text-center">
                    <a href="/Home_Page.html">
                        <img class="menu-icon" src="assets/img/images/m1.png" alt="" /> <br>
                        <span class="menu-span text-center">Home</span>
                    </a>
                </li>

                <li class="menu-width text-center">
                    <a href="/data-dictionary.jsp">
                        <img class="menu-icon active" src="assets/img/images/m2.png" alt="" /> <br>
                        <span class="menu-span text-center">Data Dictionary</span>
                    </a>
                </li>

                <li class="menu-width text-center">
                    <a href="/default.jsp">
                        <img class="menu-icon" src="assets/img/images/m4.png" alt="" /> <br>
                        <span class="menu-span text-center">Templates</span>
                    </a>
                </li>



                <li class="menu-width text-center">
                    <a href="/ExtractPdf.jsp">
                        <img class="menu-icon" src="assets/img/images/m3.png" alt="" /> <br>
                        <span class="menu-span text-center">Extract</span>
                    </a>
                </li>

                <li class="menu-width text-center">
                    <a href="/user-management.jsp">
                        <img class="menu-icon" src="assets/img/images/m5.png" alt="" /> <br>
                        <span class="menu-span text-center">Users</span>
                    </a>
                </li>

                <li class="menu-width text-center">
                    <a href="http://localhost:8080/assets/docs/User_Guide.pdf" target="_blank">
                        <img class="menu-icon" src="assets/img/images/m6.png" alt="" /> <br>
                        <span class="menu-span text-center">Help</span>
                    </a>
                </li>

                <li class="menu-dwidth row">
                    <div class="col-sm-10">

                    </div>
                    <div class="col-sm-2">
                        <a href="#" onclick="logout()">
                            <span class="menu-span text-center">Logout</span>
                        </a>
                    </div>
                </li>
            </ul>
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
                        <li><a href="/index.jsp">Home <span class="sr-only">(current)</span></a></li>
                        <li><a href="default.jsp">Manage Templates</a></li>
                        <li class="active"><a href="data-dictionary.jsp">Data Dictionary</a></li>
                        <li><a href="/ExtractPdf.jsp">Extract</a></li>
                        <li><a href="/user-management.jsp">Users</a></li>
                        <li><a href="#">Help</a></li>
                        <li><a href="#" onclick="logout()">Logout</a></li>
                    </ul>
                </div><!-- /.navbar-collapse -->
            </div><!-- /.container-fluid -->
        </nav>
    </div>
    <div class="row"></div>
</header>

<!-- container -->
<div class="container-new">
<div class="sign-cont-body" style="padding: 20px 20px 20px 20px">
    <div class="row">

        <!-- defining dictionary -->
        <div class="col-sm-3" style="padding: 5px 5px 5px 10px">
            <div class="box">
                <header>
                    <p>Defining Data Dictionary</p>
                    <hr />
                </header>
                <section style="padding: 10px">
                    <form id="dicForm" class="form-horizontal" role="form">
                        <div class="form-group">
                            <label class="col-sm-3">Name:</label>
                            <div class="col-sm-8">
                                <input data-bind="value:newData().name" id="dicFormName" type="text" class="form-control" autofocus="autofocus"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3">Type:</label>
                            <div class="col-sm-8">
                                <select data-bind="value:newData().type" class="form-control"></select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3">Description:</label>
                            <div class="col-sm-8">
                                <textarea data-bind="value:newData().description" class="form-control" rows="3"></textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3">Data Type:</label>
                            <div class="col-sm-8">
                                <select data-bind="value:newData().dataType" class="form-control">
                                    <option>int</option>
                                    <option>string</option>
                                    <option>char</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3">Length:</label>
                            <div class="col-sm-8">
                                <input data-bind="value:newData().length" type="number" min="0" class="form-control" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3">Default Value:</label>
                            <div class="col-sm-8">
                                <input data-bind="value:newData().defaultValues" type="text" class="form-control" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3">Allowed Values:</label>
                            <div class="col-sm-8">
                                <input data-bind="value:newData().allowedValues" type="text" class="form-control" />
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-3 col-sm-8">
                                <input data-bind="click:addNew" type="submit" class="btn btn-default" value=" Add " />
                            </div>
                        </div>
                    </form>
                </section>
            </div>
        </div>

        <!-- current dictionary -->
        <div class="col-sm-9" style="padding: 5px 10px 5px 5px">
            <div class="box">
                <header>
                    <p>Current Data Dictionary</p>
                    <hr />
                </header>
                <section>
                    <table class="table table-responsive">
                        <thead>
                            <tr>
                                <th>Name</th><th>Type</th><th>Description</th><th>Data Type</th><th>Length</th><th>Default Value</th><th>Allowed Values</th><th></th>
                            </tr>
                        </thead>
                        <tbody data-bind="foreach:currentDic">
                            <tr>
                                <td data-bind="text:name">Customer</td>
                                <td data-bind="text:type">int</td>
                                <td data-bind="text:description" style="width: 15%; overflow: auto;">Test Desc.</td>
                                <td data-bind="text:dataType">6</td>
                                <td data-bind="text:length">5</td>
                                <td data-bind="text:defaultValues">0</td>
                                <td data-bind="text:allowedValues">x=>x<999999&x>0</td>
                                <td style="max-width: 40px"><button data-bind="click:$parent.removeDicItem" class="btn btn-default" style="color:red"><span class="glyphicon glyphicon-trash"></span></button></td>
                            </tr>
                        </tbody>
                    </table>
                </section>
            </div>
        </div>
    </div>
</div>
</div>

<!--footer>
    <div class="navbar container-footer">
        <div class="col-md-12">
            <div class="col-md-7">
                <p class="copyright footer-h5">COPYRIGHT &copy; 2015 XTRACTOR</p>
            </div>
            <div class="col-md-5">
                <ul class="list-inline text-center">
                    <li class="text-center footer-img-li">
                        <a href=""><img class="footer-img" src="assets/img/images/1.png" alt="" /> <br></a>
                    </li>
                    <li class="text-center footer-img-li">
                        <a href=""><img class="footer-img" src="assets/img/images/2.png" alt="" /> <br></a>
                    </li>
                    <li class="text-center footer-img-li">
                        <a href=""><img class="footer-img" src="assets/img/images/3.png" alt="" /> <br></a>
                    </li>
                    <li class="text-center footer-img-li">
                        <a href=""><img class="footer-img" src="assets/img/images/4.png" alt="" /> <br></a>
                    </li>
                    <li class="text-center footer-img-li">
                        <a href=""><img class="footer-img" src="assets/img/images/5.png" alt="" /> <br></a>
                    </li>
                    <li class="text-center footer-img-li">
                        <a href=""><img class="footer-img" src="assets/img/images/6.png" alt="" /> <br></a>
                    </li>
                </ul>
            </div>
        </div>

    </div>
</footer-->










<!-- overlay div -->
<div id="overlay" style="position: absolute; top: 0; left: 0; width: 133vw; height: 133vh; display: none;">
    <div style="position: relative; margin: 40vh auto; max-width: 600px; height: 75px; text-align: center; background-color: #fff; border-radius: 10px; padding: 25px; box-shadow: 0 0 10px #303030; z-index: 110">
        <img src="assets/img/win-loader.gif" alt="loading" />&nbsp;&nbsp;&nbsp;
        <label><span data-bind="text:overlayNotification">Notification...</span></label>
    </div>
    <div style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; background-color: #eee; opacity: 0.7; z-index: 100"></div>
</div>




<!-- importing libraries -->
<script type="text/javascript" src="assets/js/knockout-3.2.0.js" ></script>
<script type="text/javascript" src="assets/js/dataDictionaryScripts/dataDictionaryVM.js"> </script>
<script type="text/javascript" src="assets/js/userManagementScripts/security.js" ></script>

<!-- custom js -->
<script type="text/javascript">
    $(document).ajaxStart(function(){
        $("#overlay").css("display","block");
    });
    $(document).ajaxComplete(function(){
        $("#overlay").css("display","none");
        dataDicVM.overlayNotification('');
    });
</script>


</body>
</html>