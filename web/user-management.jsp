<%--
  Created by IntelliJ IDEA.
  User: K D K Madusanka
  Date: 1/26/2015
  Time: 12:21 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

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
            font-family: "Calibri";
        }
        .box{
            border: 1px solid #a0a0a0;
            height: 85vh;
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
                <ul class="nav navbar-nav navbar-right">
                    <li style="margin: 15px 15px 0 0">Login as: <b>administrator</b></li>
                    <li><a onclick="window.location.href = 'index.jsp'" class="btn btn-default" style="padding: 5px; margin-top: 10px; max-width: 100px;">Logout <span class="glyphicon glyphicon-log-out"></span></a></li>
                </ul>
            </div><!-- /.navbar-collapse -->
        </div><!-- /.container-fluid -->
    </nav>
</header>

<!-- container -->
<div style="padding: 0 20px 0 20px">
    <!-- page headings -->
    <div class="row">
        <div class="col-sm-3 row">
            <div class="col-sm-4"><img src="assets/img/logo.png" style="width:100px; height: 100px" /></div>
            <div class="col-sm-8"><span>Brandix</span></div>
        </div>
        <div class="col-sm-9" style="text-align: center">
            <h2>Admin Portal - User Management</h2>
        </div>
    </div>
    <!-- contents -->
    <div class="row">
        <!-- left side navigation menu -->
        <div class="col-sm-2">
            <div class="list-group">
                <!--a href="#" data-bind="click:setShowingDiv('company')" class="list-group-item company">Company<span class="glyphicon glyphicon-chevron-right pull-right"></span></a-->
                <a href="#" data-bind="click:setShowingDiv.bind($data,'createUser')" class="list-group-item active createUser">Create User<span class="glyphicon glyphicon-chevron-right pull-right"></span></a>
                <a href="#" data-bind="click:setShowingDiv.bind($data,'hiMan')" class="list-group-item hiMan">Hierachy Management<span class="glyphicon glyphicon-chevron-right pull-right"></span></a>
            </div>
        </div>
        <!-- selected contents -->
        <div class="col-sm-10" style="border-left: 1px solid #000000; padding-left: 20px;">

            <!-- company details -->
            <div data-bind="visible:(showingDiv()=='company')"></div>

            <!-- Create user -->
            <div data-bind="visible:(showingDiv()=='createUser')">
                <form class="form-horizontal" role="form" style="max-width: 800px;">
                    <fieldset>
                        <legend>
                            Create Company User
                        </legend>
                    </fieldset>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">Company Name</label>
                        <label class="col-sm-9 control-label" style="text-align: left">Brandix</label>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">User Name</label>
                        <div class="col-sm-9">
                            <input data-bind="value:newUserBuffer().username" type="text" class="form-control" />
                        </div>
                        <div class="col-sm-1">
                            <span class="glyphicon glyphicon-check" style="font-size: 20px"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">Full Name</label>
                        <div class="col-sm-9">
                            <input data-bind="value:newUserBuffer().fullname" type="text" class="form-control" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">Password</label>
                        <div class="col-sm-9">
                            <input data-bind="value:newUserBuffer().password" type="password" class="form-control" id="closePass" />
                            <input data-bind="value:newUserBuffer().password" type="text" class="form-control" style="display: none" id="openPass" />
                        </div>
                        <div class="col-sm-1">
                            <button class="btn btn-default eyeOpen" type="button" onclick="$('#openPass').show(); $('#closePass').hide(); $(this).hide(); $('.eyeClose').show()"><span class="glyphicon glyphicon-eye-open"></span></button>
                            <button class="btn btn-default eyeClose" type="button" style="display: none" onclick="$('#openPass').hide(); $('#closePass').show(); $(this).hide(); $('.eyeOpen').show()"><span class="glyphicon glyphicon-eye-close"></span></button>
                        </div>
                    </div>
                    <div class="col-sm-offset-2 col-sm-9"><hr/></div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-9 btn-group">
                            <button data-bind="click:createNewUser" class="btn btn-default">Create</button>
                            <input type="reset" class="btn btn-default" />
                        </div>
                    </div>
                </form>
            </div>

            <!-- Hierachy Management -->
            <div data-bind="visible:(showingDiv()=='hiMan')">
                <form>
                    <fieldset>
                        <legend>Hierachy Management</legend>
                    </fieldset>
                </form>
                <div class="row">
                    <!-- tree structure -->
                    <div class="col-sm-4" style="border-right: 1px solid #000000">
                        <!-- list -->
                        <div>
                            <h4>- Select relevant category -</h4>
                            <br/>
                            <input class="form-control" type="text" id="treeSearch" placeholder="search tree" />
                            <br/>
                            <!-- root node -->
                            <p data-bind="click:setRootAsCurrentSelectedTreeNode" style="cursor:pointer; font-size: large"><span class="glyphicon glyphicon-tree-conifer"></span>&nbsp;&nbsp;<i>Root</i></p>
                            <!-- tree -->
                            <div class="treeView"></div>
                            <br/>
                            <a href="default.jsp">Create New Category</a>
                            <br/>
                        </div>
                    </div>
                    <!-- Assign User -->
                    <div data-bind="visible:!(isSelectedTemplate())" class="col-sm-8" style="border-left: 1px solid #000000">
                        <h4>- Assign User -</h4>
                        <br/>
                        <form class="form-horizontal" role="form">
                            <div class="form-group">
                                <label class="col-sm-2 control-label">Select user</label>
                                <div class="col-sm-6">
                                    <select class="form-control">
                                        <option>dummy</option>
                                    </select>
                                </div>
                                <div class="col-sm-4">or <a>Create a new user</a></div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">Username</label>
                                <label class="col-sm-10">Dummy name</label>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">Full Name</label>
                                <label class="col-sm-10">Dummy full name</label>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">Company</label>
                                <label class="col-sm-10">Brandix</label>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">Selected Category</label>
                                <label class="col-sm-10">selected name</label>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-10">
                                    <button class="btn btn-default">Assign</button>
                                </div>
                            </div>
                        </form>
                    </div>
                    <!-- warning of selecting template -->
                    <div data-bind="visible:isSelectedTemplate" class="col-sm-8" style="border-left: 1px solid #000000; color: orangered">
                        <p>You have selected a template.<br/> A template cannot be assign to a user!<br/>Please try a category node.</p>
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
        <label><span>Loading...</span></label>
    </div>
    <div style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; background-color: #eee; opacity: 0.7; z-index: 100"></div>
</div>




<!-- importing libraries -->
<script type="text/javascript" src="assets/js/knockout-3.2.0.js" ></script>
<script type="text/javascript" src="assets/js/userManagementScripts/userManagementVM.js"> </script>
<script type="text/javascript" src="assets/js/userManagementScripts/security.js"> </script>
<script type="text/javascript" src="assets/js/userManagementScripts/uiFunc.js"> </script>

<!-- custom js -->
<script type="text/javascript">
    $(document).ajaxStart(function(){
        $("#overlay").css("display","block");
    });
    $(document).ajaxComplete(function(){
        $("#overlay").css("display","none");
    });

    var selectedNodeRow = undefined;
    var selectedNodeChildRow = undefined;
    var selectedNodeParentRow = undefined;

    initTrees();
    getAllUsers();

    // search tree
    var to = false;
    $('#treeSearch').keyup(function () {
        if(to) { clearTimeout(to); }
        to = setTimeout(function () {
            var v = $('#treeSearch').val();
            $('.treeView').jstree(true).search(v);
        }, 250);
    });

</script>


</body>
</html>