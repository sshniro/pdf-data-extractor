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
    <title>Xractor/Manage Catogories n Templates</title>

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

    <!-- custom css -->
    <style type="text/css">
        body{
            font-family: "calibri";
        }
        .card-heading{
            background-color: #EEE;
            border-bottom: 1px solid #696969;
            padding: 5px;
            margin: 0;
            font-size: 1.5em;
            color: #696969;
            cursor: pointer;
        }
        .card-content{
            margin: 0;
            background-color: #ffffff;
            padding: 20px;
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
                        <img class="menu-icon" src="assets/img/images/m2.png" alt="" /> <br>
                        <span class="menu-span text-center">Data Dictionary</span>
                    </a>
                </li>

                <li class="menu-width text-center">
                    <a href="/default.jsp">
                        <img class="menu-icon active" src="assets/img/images/m4.png" alt="" /> <br>
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
                        <li class="active"><a href="default.jsp">Manage Templates</a></li>
                        <li><a href="data-dictionary.jsp">Data Dictionary</a></li>
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
<div class="container-new row" style="padding: 30px 20px 0 20px; margin: 0; min-height: 560px">

    <!-- list -->
    <div class="col-sm-3">
        <span>- Select to create category or template -</span>
        <br/>
        <input class="form-control" type="text" id="treeSearch" placeholder="search tree" />
        <br/>
        <!-- root node -->
        <p data-bind="click:setRootAsCurrentSelectedTreeNode" style="cursor:pointer; font-size: large"><span class="glyphicon glyphicon-tree-conifer"></span>&nbsp;&nbsp;<i>Templates</i></p>
        <!-- tree -->
        <div class="treeView"></div>
    </div>

    <!-- content -->
    <div class="col-sm-9" style="border-left: 3px solid #eee">
        <!-- Create New -->
        <div class="row">
            <!-- heading -->
            <div class="col-sm-12 row card-heading" onclick="$(this).next('div').toggle(800)">
                <span class="pull-left">Create New [Category / Template]</span>
                <button type="button" class="btn btn-default pull-right ">
                    <span class="caret"></span>
                </button>
            </div>
            <!-- content -->
            <div class="col-sm-12 row card-content">
                <!-- new category -->
                <div class="col-sm-6">
                    <form class="form-horizontal" role="form">
                        <fieldset>
                            <legend>
                                New (Sub) Category
                            </legend>
                        </fieldset>
                        <!-- parent -->
                        <div class="form-group">
                            <label class="control-label col-sm-3">Parent</label>
                            <label data-bind="visible:!isSelectedTemplate(), text:currentSelectedTreeNode().text()" class="col-sm-9">root</label>
                            <label data-bind="visible:isSelectedTemplate()" style="color: red">Node you selected is a Template!</label>
                        </div>
                        <!-- category -->
                        <div class="form-group">
                            <label class="control-label col-sm-3">(Sub) Category Name</label>
                            <div class="col-sm-9">
                                <input data-bind="value:newSubCategoryName" type="text" class="form-control" />
                            </div>
                        </div>
                        <!-- create button -->
                        <div class="form-group">
                            <div class="col-sm-offset-3 col-sm-9">
                                <button data-bind="click:createNewSubCategory" class="btn btn-default">Create <span class="glyphicon glyphicon-check"></span></button>
                            </div>
                        </div>
                        <!-- notification -->
                        <div class="form-group">
                            <div class="col-sm-12">
                                <label data-bind="text:notification_createNewSubCategory" class="pull-right">...</label>
                            </div>
                        </div>
                    </form>
                </div>
                <!-- new template -->
                <div class="col-sm-6">
                    <form class="form-horizontal" role="form">
                        <fieldset>
                            <legend>
                                New Template
                            </legend>
                        </fieldset>
                        <!-- category -->
                        <div class="form-group">
                            <label class="control-label col-sm-3">Category Name</label>
                            <label data-bind="visible:!isSelectedTemplate(), text:currentSelectedTreeNode().text()" class="col-sm-9">Cat_1</label>
                            <label data-bind="visible:isSelectedTemplate()" style="color: red">Node you selected is a Template!</label>
                        </div>
                        <!-- template name -->
                        <div class="form-group">
                            <label class="control-label col-sm-3">Template Name</label>
                            <div class="col-sm-9">
                                <input data-bind="value:newTemplateName" type="text" class="form-control" />
                            </div>
                        </div>
                        <!-- file input -->
                        <div class="form-group">
                            <label class="control-label col-sm-3">Template File</label>
                            <div class="col-sm-9">
                                <input id="templateFile" type="file" typeof=".pdf" />
                            </div>
                        </div>
                        <!-- create button -->
                        <div class="form-group">
                            <div class="col-sm-offset-3 col-sm-9">
                                <button data-bind="click:uploadNewTemplate" id="btnUpload" class="btn btn-default">Upload&nbsp;<span class="glyphicon glyphicon-cloud-upload"></span> | Goto template edit&nbsp;<span class="glyphicon glyphicon-circle-arrow-right"></span></button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <!-- Summary -->
        <%-- div class="row">
            <!-- heading -->
            <div class="col-sm-12 row card-heading" onclick="$(this).next('div').toggle(800)">
                <span class="pull-left">Edit & Remove</span>
                <button type="button" class="btn btn-default pull-right ">
                    <span class="caret"></span>
                </button>
            </div>
            <!-- content -->
            <div class="col-sm-12 row card-content" style="display: block">
                <!-- tools -->
                <div class="col-sm-12" style="text-align: right">
                    <div class="btn-group">
                        <button class="btn btn-default"><span class="glyphicon glyphicon-trash"></span>&nbsp;Delete Parent</button>
                        <button class="btn btn-default"><span class="glyphicon glyphicon-trash"></span>&nbsp;Delete Selected</button>
                    </div>
                    <div class="btn-group" data-bind="visible:isSelectedTemplate()">
                        <button data-bind="click:redirectToEditTemplate" class="btn btn-default"><span class="glyphicon glyphicon-edit"></span>&nbsp;Edit Template</button>
                    </div>
                </div>
                <!-- template view and remove functions -->
                <div class="col-sm-12">

                    <!-- selected elements -->
                    <div class="row">
                        <div class="col-sm-4">
                            <b><span>Parent</span></b><br/>
                            <i><span data-bind="text:currentNodeParent().text">parent_name</span></i>
                        </div>
                        <div class="col-sm-4">
                            <b><span>Selected</span></b><br/>
                            <i><span data-bind="text:currentSelectedTreeNode().text()">selected_name</span></i>
                        </div>
                        <div class="col-sm-4">
                            <label data-bind="visible:isSelectedTemplate()" style="color: darksalmon">Node you selected is a Template!</label>
                        </div>
                    </div>
                    <br />
                    <!-- template preview -->
                    <div class="row" data-bind="visible:isSelectedTemplate()">
                        <h4>Preview</h4>
                        <!-- preview of template -->
                        <div style="width: 100%">
                            <iframe id="pdfRenderer" style="width: 100%"></iframe>
                        </div>
                    </div>
                </div>
            </div>
        </div --%>

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
    <div style="positionor: #eee; opacity: 0.7; z-index: 100"></div>
</div>




<!-- importing libraries -->
<script type="text/javascript" src="assets/js/knockout-3.2.0.js" ></script>
<script type="text/javascript" src="assets/js/markUpTemplateRegionsScripts/uiFunctions.js"> </script>
<script type="text/javascript" src="assets/js/markUpTemplateRegionsScripts/models.js"> </script>
<script type="text/javascript" src="assets/js/markUpTemplateRegionsScripts/viewModel.js"> </script>
<script type="text/javascript" src="assets/js/markUpTemplateRegionsScripts/messsageBroker.js"> </script>
<script type="text/javascript" src="assets/js/userManagementScripts/security.js"> </script>

<!-- js tree script -->
<script type="text/javascript">

    // tree view script
    //init viewmodel
    vm = new ViewModel();
    ko.applyBindings(vm);
    var client = new XMLHttpRequest();

    var selectedNodeRow = undefined;
    var selectedNodeChildRow = undefined;
    var selectedNodeParentRow = undefined;

    initTrees('getUserNodes');

    // search tree
    var to = false;
    $('#treeSearch').keyup(function () {
        if(to) { clearTimeout(to); }
        to = setTimeout(function () {
            var v = $('#treeSearch').val();
            $('.treeView').jstree(true).search(v);
        }, 250);
    });

    client.onload = function() {
        if (client.status == 200) {
            if(client.responseText=="success") {
                vm.newTemplateName('');
                document.getElementById('templateFile').value= null;
                var successUrl = "MarkUpTemplateRegions.jsp";
                window.location.href = successUrl;
            }else{
                alert(client.responseText);
            }
        }
        /*Enable the upload button after receivin a extractedData from the server */
        $("#btnUpload").attr("disabled", false);
    }

</script>

</body>
</html>
