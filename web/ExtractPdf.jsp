<%--
  User: K D K Madusanka
  Date: 12/03/2014
  Time: 09:21 AM
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
    <title>Xractor/Extract Documents</title>

    <!-- jQuery -->
    <script type="text/javascript" src="assets/js/jquery-1.10.2.js"></script>
    <!-- bootstrap -->
    <script type="text/javascript" src="assets/js/bootstrap.js"></script>
    <link rel="stylesheet" type="text/css" href="assets/css/bootstrap.css">
    <!-- jstree -->
    <script type="text/javascript" src="assets/ex-libraries/jstree/jstree.js"></script>
    <link rel="stylesheet" href="assets/ex-libraries/jstree/themes/default/style.css" />

    <!-- custom -->
    <script type="text/javascript" src="assets/js/PageNavigator.js"></script>
    <script type="text/javascript" src="assets/js/JspFormPopulate.js"></script>

    <!-- added -->
    <link href="assets/css/style.css" rel="stylesheet">
    <link href="assets/css/checkbox-x.css" rel="stylesheet">
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

    <script>
        //var isMCValid = true;
        //window.onload = getMC;

        var responseObj = null;
        var initDataJSON;
        var initData;

        document.ready = assignSessionAttributes;
        function assignSessionAttributes() {
            <% String editResponse=(String) session.getAttribute("editJsonResponse");%>
            var jsonObj = '<% out.print(editResponse);%>';
            responseObj = JSON.parse(jsonObj);

            if(responseObj.templateEditStatus == "true"){

                var data ={id:responseObj.id,parent : responseObj.parent}
                var result = null;
                var extractedData = null;
                var parent = null; // The extracted Template's ID
                var id = null; // The extracted current document's ID
                $.ajax({
                    type: 'POST', url: 'ExtractEditTempController',
                    contentType: 'application/json; charset=utf-8',
                    dataType: 'json',
                    data: JSON.stringify(data),
                    success: function(data, textStatus, jqXHR) {
                        result = JSON.parse(jqXHR.responseText);
                        /* If the status is success refresh the Main Categories Select Options  */
                        extractedData = result.extractedData;
                        parent = result.parent;
                        id = result.id;
                        alert(extractedData);
                    }
                });
            }
        }


    </script>

    <style>
        body{
            font-family: "calibri";
        }
    </style>
</head>
<body>

<!-- nav bar -->
<header>
    <div class="navbar container-header">
        <div class="col-md-5">
            <img class="menu-logo" src="assets/img/images/logo.png" alt="" />
        </div>
        <div class="col-md-7">
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
                        <img class="menu-icon active" src="assets/img/images/m3.png" alt="" /> <br>
                        <span class="menu-span text-center">Extract Doc</span>
                    </a>
                </li>

                <li class="menu-width text-center">
                    <a href="/user-management.jsp">
                        <img class="menu-icon" src="assets/img/images/m4.png" alt="" /> <br>
                        <span class="menu-span text-center">User Man.</span>
                    </a>
                </li>

                <li class="menu-width text-center">
                    <a href="#">
                        <img class="menu-icon" src="assets/img/images/m6.png" alt="" /> <br>
                        <span class="menu-span text-center">Help</span>
                    </a>
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
                        <li class="active"><a href="/ExtractPdf.jsp">Extract Doc</a></li>
                        <li><a href="/user-management.jsp">User Management</a></li>
                        <li><a href="#">Help</a></li>

                    </ul>
                </div><!-- /.navbar-collapse -->
            </div><!-- /.container-fluid -->
        </nav>
    </div>
    <div class="row"></div>
</header>


<!-- container -->
<div class="container-new">
    <div class="container row sign-cont-body" style="margin: 20px auto; padding-top: 40px; min-height: 500px">
        <!-- tree view -->
        <div class="col-sm-4">
            <fieldset>
                <legend>
                    Select a Template<br/>
                    <input class="form-control" type="text" id="treeSearch" placeholder="search tree" style="margin-top: 8px" />
                </legend>
            </fieldset>

            <br/>
            <!-- root node -->
            <p data-bind="click:setRootAsCurrentSelectedTreeNode" style="cursor:pointer; font-size: large"><span class="glyphicon glyphicon-tree-conifer"></span>&nbsp;&nbsp;<i>Root</i></p>
            <!-- tree -->
            <div class="treeView">
            </div>

            <%-- <br/><br/>
            <!-- preview of template -->
            <div style="width: 100%">
                <iframe id="pdfRenderer" style="width: 100%"></iframe>
            </div> --%>
            <hr/>
        </div>

        <!-- extract form and data -->
        <div class="col-sm-8" style="border-left: 1px solid #ffffff">
            <!-- form -->
            <form class="form-horizontal" role="form">
                <fieldset>
                    <legend>
                        Upload a PDF and extract<br/>
                        <h5 style="font-style: italic; color: darkgray">Selecting a Template (from the left) is required for extract a PDF file.<br/>If cannot see any template, goto <a href="/default.jsp">"Create a New Template"</a></h5>
                    </legend>
                </fieldset>
                <fieldset>
                    <table>
                        <tbody>
                        <tr>    <td>Selected Template&nbsp;&nbsp;&nbsp;&nbsp;</td>      <td>:&nbsp;<span data-bind="visible:isSelectedTemplate(), text:currentSelectedTreeNode().text()">$template_name</span><span data-bind="visible:!isSelectedTemplate()" style="color: red">Node you selected is not a Template!</span></td></tr>
                        <tr>    <td>Parent Category&nbsp;&nbsp;&nbsp;&nbsp;</td>        <td>:&nbsp;<span data-bind="text:currentNodeParent().text">$category_name</span></td></tr>
                        </tbody>
                    </table>
                </fieldset>
                <hr style="color: #eee" />
                <!-- form fields -->
                <div class="form-group">
                    <label class="control-label col-sm-4">Document Identification Name</label>
                    <div class="col-sm-8">
                        <input data-bind="value:selectedDocumentId" type="text" class="form-control" />
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-4">Select PDF file</label>
                    <div class="col-sm-8">
                        <input id="pdfFile" type="file" typeof=".pdf" datatype=".pdf" />
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-4 col-sm-8">
                        <button data-bind="click:uploadPdfFile" id="ajaxStart" class="btn btn-default">Upload&nbsp;<span class="glyphicon glyphicon-cloud-upload"></span> | Extract&nbsp;<span class="glyphicon glyphicon-check"></span></button>
                    </div>
                </div>
            </form>
            <!-- extracted data -->
            <div class="well well-sm row" style="margin-top: 20px">
               <div>
                    <h4 style="display: inline;">Extracted Data For Pdf ID <span id="extractedPdfId" data-bind="text:extractedPdfId"></span></h4>
                    <button id="editTemplate" class="form-control pull-right" data-bind="click:editTemplate" style=" width: 50px; display: none">Edit</button>
                </div>
                <div id="extractedText"></div>
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
<div id="overlay" style="position: absolute; top: 0; left: 0; width: 100vw; height: 100vh; display: none;">
    <div style="position: relative; margin: 40vh auto; max-width: 600px; height: 75px; text-align: center; background-color: #fff; border-radius: 10px; padding: 25px; box-shadow: 0 0 10px #303030; z-index: 110">
        <img src="assets/img/win-loader.gif" alt="loading" />&nbsp;&nbsp;&nbsp;
        <label><span data-bind="text:overlayNotification">Notification...</span></label>
    </div>
    <div style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; background-color: #eee; opacity: 0.7; z-index: 100"></div>
</div>





<!-- importing libraries -->
<script type="text/javascript" src="assets/js/knockout-3.2.0.js" ></script>
<script type="text/javascript" src="assets/js/markUpTemplateRegionsScripts/uiFunctions.js"> </script>
<script type="text/javascript" src="assets/js/markUpTemplateRegionsScripts/models.js"> </script>
<script type="text/javascript" src="assets/js/markUpTemplateRegionsScripts/viewModel.js"> </script>

<script>
    // tree view script
    //init viewmodel
    vm = new ViewModel();
    ko.applyBindings(vm);

    var selectedNodeRow = undefined;
    var selectedNodeChildRow = undefined;
    var selectedNodeParentRow = undefined;

    initTrees('getAllNodes');

    // search tree
    var to = false;
    $('#treeSearch').keyup(function () {
        if(to) { clearTimeout(to); }
        to = setTimeout(function () {
            var v = $('#treeSearch').val();
            $('.treeView').jstree(true).search(v);
        }, 250);
    });

    var client = new XMLHttpRequest();
    client.onload = function() {
        /*Enable the upload button after receiving a extractedData from the server */
        $("#ajaxStart").attr("disabled", false);
        if (client.status == 200) {
            var messages = JSON.parse(client.responseText);
            if(messages.status===true) {
                /*  replace all occurrence of next line character to </br> tag
                 *   g => global , so replaces all occurrences of the '\n'
                 *   */
                var str = messages.extractedData.replace( new RegExp('\n', 'g') , '</br>');
                $('#extractedText').html(str);
                $('button#editTemplate').css('display','block');
                vm.extractedPdfId(messages.id);
                $('#overlay').css('display', 'none');
            }else{
                alert(messages.errorCause);
            }
        }else{
            /* If no response from the server is received */
            alert("unexpected server error");
        }
    }
</script>
</body>
</html>
