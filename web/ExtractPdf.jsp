<%--
  User: K D K Madusanka
  Date: 12/03/2014
  Time: 09:21 AM
  ----------------------------------
  Log:
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>PDF Extract</title>

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
                // TO DO call the extraction for the particular data. (ExtractEditTempController)
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
                    <li><a data-bind="click:logout" class="btn btn-default" style="padding: 5px; margin-top: 10px; max-width: 100px;">Logout <span class="glyphicon glyphicon-log-out"></span></a></li>
                </ul>
            </div><!-- /.navbar-collapse -->
        </div><!-- /.container-fluid -->
    </nav>
</header>


<!-- container -->
<div class="container row" style="margin: 0 auto;">
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

        <br/><br/>
        <!-- preview of template -->
        <div style="width: 100%">
            <iframe id="pdfRenderer" style="width: 100%"></iframe>
        </div>
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
