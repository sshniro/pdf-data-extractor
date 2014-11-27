<%--
  User: K D K Madusanka
  Date: 10/26/2014
  Time: 10:51 AM
  ----------------------------------
  Log:
  October 26, 2014 -    - K D K Madusanka    - Creating basic UI wireframe
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <!-------------------------------- CSS Files------------------------------------>
    <link rel="stylesheet" type="text/css" href="assets/css/bootstrap.css">

    <!-------------------------------- JS Files------------------------------------>
    <script type="text/javascript" src="assets/js/jquery-1.10.2.js"></script>
    <script type="text/javascript" src="assets/ex-libraries/jstree/jstree.js"></script>
    <script type="text/javascript" src="assets/js/bootstrap.js"></script>

    <!-- custom css -->
    <style type="text/css">
        body{
            font-family: "Calibri";
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
                <a class="navbar-brand" href="#">Enhan<i>z</i>er</a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="navbar_collapse">
                <ul class="nav navbar-nav">
                    <li><a id="templateExtract" href="#">Extract Doc</a></li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li style="margin: 15px 15px 0 0">Login as: <b>administrator</b></li>
                    <li><a class="btn btn-default" style="padding: 5px; margin-top: 10px; max-width: 100px;">Logout <span class="glyphicon glyphicon-log-out"></span></a></li>
                </ul>
            </div><!-- /.navbar-collapse -->
        </div><!-- /.container-fluid -->
    </nav>
</header>

<!-- container -->
<div style="padding: 0 20px 0 20px">

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
            <!-- list -->
            <div class="col-sm-3">
                <span>- Select to create category or template -</span>
                <br/>
                <!-- tree -->
                <div id="treeViewDiv">
                </div>
            </div>
            <!-- new category -->
            <div class="col-sm-4">
                <form class="form-horizontal" role="form">
                    <fieldset>
                        <legend>
                            New (Sub) Category
                        </legend>
                    </fieldset>
                    <!-- parent -->
                    <div class="form-group">
                        <label class="control-label col-sm-3">Parent</label>
                        <label class="col-sm-9">root</label>
                    </div>
                    <!-- category -->
                    <div class="form-group">
                        <label class="control-label col-sm-3">(Sub) Category Name</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" />
                        </div>
                    </div>
                    <!-- create button -->
                    <div class="form-group">
                        <div class="col-sm-offset-3 col-sm-9">
                            <button class="btn btn-default">Create <span class="glyphicon glyphicon-check"></span></button>
                        </div>
                    </div>
                    <!-- notification -->
                    <div class="form-group">
                        <div class="col-sm-12">
                            <label class="pull-right">Completed...</label>
                        </div>
                    </div>
                </form>
            </div>
            <!-- new template -->
            <div class="col-sm-5">
                <form class="form-horizontal" role="form">
                    <fieldset>
                        <legend>
                            New Template
                        </legend>
                    </fieldset>
                    <!-- parent -->
                    <div class="form-group">
                        <label class="control-label col-sm-3">Parent</label>
                        <label class="col-sm-9">root</label>
                    </div>
                    <!-- category -->
                    <div class="form-group">
                        <label class="control-label col-sm-3">Category Name</label>
                        <label class="col-sm-9">Cat_1</label>
                    </div>
                    <!-- template name -->
                    <div class="form-group">
                        <label class="control-label col-sm-3">Template Name</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" />
                        </div>
                    </div>
                    <!-- file input -->
                    <div class="form-group">
                        <label class="control-label col-sm-3">Template File</label>
                        <div class="col-sm-9">
                            <input type="file" typeof=".pdf" />
                        </div>
                    </div>
                    <!-- create button -->
                    <div class="form-group">
                        <div class="col-sm-offset-3 col-sm-9">
                            <button class="btn btn-default">Create <span class="glyphicon glyphicon-check"></span></button>
                        </div>
                    </div>
                    <!-- progress -->
                    <div class="form-group">
                        <div class="col-sm-12">
                            <progress style="width: 100%; height: 10px;" value="50" max="100"></progress>
                        </div>
                    </div>
                    <!-- notification -->
                    <div class="form-group">
                        <div class="col-sm-12">
                            <label class="pull-right">Completed...</label>
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
            <span class="pull-left">Summary <small>(Remove)</small></span>
            <button type="button" class="btn btn-default pull-right ">
                <span class="caret"></span><br/><span class="caret"></span><br/><span class="caret"></span>
            </button>
        </div>
        <!-- content -->
        <div class="col-sm-12 row card-content">
            <!-- list -->
            <div class="col-sm-3">
                <span>- Select to create category or template -</span>
            </div>
            <!-- template view and remove functions -->
            <div class="col-sm-9">
                <!-- selected elements -->
                <div class="row">
                    <div class="col-sm-4">
                        <b><span>Root</span></b><br/>
                        <i><span>root_name</span></i>&nbsp;&nbsp;<span class="glyphicon glyphicon-trash" style="cursor: pointer"></span>
                    </div>
                    <div class="col-sm-4">
                        <b><span>Category</span></b><br/>
                        <i><span>cat_name</span></i>&nbsp;&nbsp;<span class="glyphicon glyphicon-trash" style="cursor: pointer"></span>
                    </div>
                    <div class="col-sm-4">
                        <b><span>Template</span></b><br/>
                        <i><span>temp_name</span></i>&nbsp;&nbsp;<span class="glyphicon glyphicon-trash" style="cursor: pointer"></span>
                    </div>
                </div>
                <br />
                <!-- template preview -->
                <div class="row">
                    <h4>Preview</h4>
                </div>
            </div>
        </div>
    </div>
</div>


<!-- js tree script -->
<script type="text/javascript">
    $('#treeViewDiv').jstree({ 'core' : {
        'data' : [
            { "id" : "data1", "parent" : "#", "text" : "Root node 1" },
            { "id" : "data2", "parent" : "#", "text" : "Root node 2" },
            { "id" : "data3", "parent" : "data2", "text" : "Child 1" },
            { "id" : "data4", "parent" : "data2", "text" : "Child 2" }
        ]
    } });
</script>

</body>
</html>
