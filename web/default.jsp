<%--
  User: K D K Madusanka
  Date: 10/26/2014
  Time: 10:51 AM
  ----------------------------------
  Log:
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home</title>

    <!-- jQuery -->
    <script type="text/javascript" src="assets/js/jquery-1.10.2.js"></script>
    <!-- bootstrap -->
    <script type="text/javascript" src="assets/js/bootstrap.js"></script>
    <link rel="stylesheet" type="text/css" href="assets/css/bootstrap.css">
    <!-- jstree -->
    <script type="text/javascript" src="assets/ex-libraries/jstree/jstree.js"></script>
    <link rel="stylesheet" href="assets/ex-libraries/jstree/themes/default/style.css" />

    <!-- custom css -->
    <style type="text/css">
        body{
            font-family: "calibri";
        }
        .card-heading{
            background-color: #EEE;
            border-bottom: 1px solid #696969;
            padding: 5px;
            margin: 0 0 20px 0;
            font-size: 1.5em;
            color: #696969;
            cursor: pointer;
        }
    </style>
</head>
<body>

<!-- nav bar -->
<header>
    <nav class="navbar navbar-default" role="navigation">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="navbar_collapse">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="/default.jsp">Enhan<i>z</i>er</a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="navbar_collapse">
                <ul class="nav navbar-nav">
                    <li><a id="templateExtract" href="/ExtractPdf.jsp">Extract Doc</a></li>
                </ul>
                <ul class="nav navbar-nav">
                    <li><a href="/data-dictionary.jsp">Data Dictionary</a></li>
                </ul>
                <ul class="nav navbar-nav">
                    <li><a href="/user-management.jsp">User Management</a></li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li style="margin: 15px 15px 0 0">Login as: <b>administrator</b></li>
                    <li><a data-bind="click:logout" class="btn btn-default" style="padding: 5px; margin-top: 10px; max-width: 100px;">Logout <span class="glyphicon glyphicon-log-out"></span></a></li>
                </ul>
            </div><!-- /.navbar-collapse -->
        </div><!-- /.container-fluid -->
    </nav>
</header>

<!-- container -->
<div class="row" style="padding: 0 20px 0 20px; margin: 0">

    <!-- list -->
    <div class="col-sm-3">
        <span>- Select to create category or template -</span>
        <br/>
        <input class="form-control" type="text" id="treeSearch" placeholder="search tree" />
        <br/>
        <!-- root node -->
        <p data-bind="click:setRootAsCurrentSelectedTreeNode" style="cursor:pointer; font-size: large"><span class="glyphicon glyphicon-tree-conifer"></span>&nbsp;&nbsp;<i>Root</i></p>
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
                    <span class="caret"></span><br/><span class="caret"></span><br/><span class="caret"></span>
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
        <div class="row">
            <!-- heading -->
            <div class="col-sm-12 row card-heading" onclick="$(this).next('div').toggle(800)">
                <span class="pull-left">Edit & Remove</span>
                <button type="button" class="btn btn-default pull-right ">
                    <span class="caret"></span><br/><span class="caret"></span><br/><span class="caret"></span>
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
        </div>

    </div>
</div>

<!-- overlay div -->
<div id="overlay" style="position: absolute; top: 0; left: 0; width: 100vw; height: 100vh; display: none;">
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

    initTrees();

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
