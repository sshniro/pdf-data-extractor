<%--
  Created by IntelliJ IDEA.
  User: K D K Madusanka
  Date: 1/26/2015
  Time: 12:21 AM
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Xractor/User Management</title>

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
    <div class="navbar container-header">
        <div class="col-md-3">
            <img class="menu-logo" src="assets/img/images/logo.png" alt="" />
        </div>
        <div class="col-md-9">
            <ul class="list-inline text-left header-main-menu">
                <li class="menu-width text-center">
                    <a href="/index.jsp">
                        <img class="menu-icon" src="assets/img/images/home.png" alt="" /> <br>
                        <span class="menu-span text-center">Home</span>
                    </a>
                </li>

                <li class="menu-width text-center">
                    <a href="/default.jsp">
                        <img class="menu-icon" src="assets/img/images/m1.png" alt="" /> <br>
                        <span class="menu-span text-center">Manage Nodes</span>
                    </a>
                </li>

                <li class="menu-width text-center">
                    <a href="/data-dictionary.jsp">
                        <img class="menu-icon" src="assets/img/images/m2.png" alt="" /> <br>
                        <span class="menu-span text-center">Data Dictionary</span>
                    </a>
                </li>

                <li class="menu-width text-center">
                    <a href="/ExtractPdf.jsp">
                        <img class="menu-icon" src="assets/img/images/m3.png" alt="" /> <br>
                        <span class="menu-span text-center">Extract Doc</span>
                    </a>
                </li>

                <li class="menu-width text-center">
                    <a href="/user-management.jsp">
                        <img class="menu-icon active" src="assets/img/images/m4.png" alt="" /> <br>
                        <span class="menu-span text-center">User Man.</span>
                    </a>
                </li>

                <li class="menu-width text-center">
                    <a href="#">
                        <img class="menu-icon" src="assets/img/images/m6.png" alt="" /> <br>
                        <span class="menu-span text-center">Help</span>
                    </a>
                </li>

                <li class="menu-dwidth row">
                    <div class="col-sm-10">
                        <span class="username-text">Administrator</span> <br/>
                        <span class="username-text role">admin</span>
                    </div>
                    <div class="col-sm-2">
                        <a href="#" onclick="logout()">
                            <img class="menu-icon" src="assets/img/images/logout.png" alt="" /> <br>
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
                        <li><a href="default.jsp">Manage Catogory/Templates</a></li>
                        <li><a href="data-dictionary.jsp">Data Dictionary</a></li>
                        <li><a href="/ExtractPdf.jsp">Extract Doc</a></li>
                        <li class="active"><a href="/user-management.jsp">User Management</a></li>
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
    <div class="sign-cont-body" style="padding: 20px 20px 20px 70px; margin: 20px 10%; min-height: 500px">
        <!-- page headings -->
        <div class="row">
            <div class="col-sm-3 row">
                <h3>Brandix</h3>
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
                                <input data-bind="value:newUserBuffer().username, event:{blur:validateUserName}" type="text" class="form-control" />
                            </div>
                            <div class="col-sm-1">
                                <span data-bind="visible:isUsernameValid" class="glyphicon glyphicon-check" style="font-size: 20px"></span>
                                <span data-bind="visible:(!isUsernameValid())" class="glyphicon glyphicon-info-sign" style="font-size: 20px; color:red"></span>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">Full Name</label>
                            <div class="col-sm-9">
                                <input data-bind="value:newUserBuffer().fullname" type="text" class="form-control" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">Role</label>
                            <div class="col-sm-9">
                                <select data-bind="value:newUserBuffer().role" type="text" class="form-control">
                                    <option>Developer</option>
                                    <option>Editor</option>
                                    <option>QA</option>
                                    <option>Admin</option>
                                </select>
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
                                        <select data-bind="options:usersCollection, optionsText:'username', value:selectedUserInHierachyMan, event:{change:setSelectedUserValues}" class="form-control"></select>
                                    </div>
                                    <div class="col-sm-4">
                                        <a href="#" data-bind="click:setShowingDiv.bind($data,'createUser')">Create a new user</a><br/>
                                        or <a href="#" data-bind="click:removeUser" style="color: indianred">Delete user</a>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">Username</label>
                                    <div class="col-sm-6">
                                        <label data-bind="text:selectedUserInHierachyManCopy().username" class="form-control" style="border: 0">dummy username</label>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">Full Name</label>
                                    <div class="col-sm-6">
                                        <label data-bind="text:selectedUserInHierachyManCopy().fullname" class="col-sm-6 form-control" style="border: 0">Dummy full name</label>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">Company</label>
                                    <div class="col-sm-6">
                                        <label class="col-sm-10 form-control" style="border: 0">Brandix</label>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">Role</label>
                                    <div class="col-sm-6">
                                        <label data-bind="text:selectedUserInHierachyManCopy().role" class="col-sm-6 form-control" style="border: 0"></label>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">Assigned Categories</label>
                                    <div class="col-sm-6">
                                        <ul data-bind="foreach:selectedUserInHierachyManCopy().nodes" class="list-group">
                                            <li class="list-group-item" style="border: 0">
                                                <span data-bind="text:$data.text"></span>&nbsp;&nbsp;&nbsp;
                                                <span data-bind="click:$parent.removeUserFromNode" class="badge"><span class="glyphicon glyphicon-remove"></span></span>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">Selected Category</label>
                                    <div class="col-sm-6">
                                        <label data-bind="text:currentSelectedTreeNode().text()" class="form-control" style="border: 0">selected name</label>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-sm-offset-2 col-sm-6">
                                        <button data-bind="click:assignUserToCategory" class="btn btn-default">Assign</button>
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
</div>




<footer>
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
</footer>


<!-- overlay div -->
<div id="overlay" style="position: absolute; top: 0; left: 0; width: 133vw; height: 133vh; display: none;">
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
<script type="text/javascript" src="assets/js/userManagementScripts/security.js"> </script>

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

    initTrees('getAllNodes');
    userVM.getAllUsers();

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