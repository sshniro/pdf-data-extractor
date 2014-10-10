<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <!-------------------------------- CSS Files------------------------------------>
    <link rel="stylesheet" type="text/css" href="assets/css/bootstrap.css">

    <!-------------------------------- JS Files------------------------------------>
    <script type="text/javascript" src="assets/js/jquery-1.10.2.js"></script>
    <script type="text/javascript" src="assets/js/bootstrap.js"></script>
    <script type="text/javascript" src="assets/js/PageNavigator.js"></script>
    <script type="text/javascript" src="assets/js/JspFormPopulate.js"></script>

    <script>
        var isMCValid = true;
        window.onload = getMC;
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
                    <li class="active"><a id="templateExtract" href="#">Extract Doc</a></li>
                    <li><a id="templateUpload" href="#">Create Template</a></li>
                    <li class="dropdown">
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
<h1> Extract  PDF  Form</h1>

<fieldset>
    <legend>Upload File</legend>
    <form >


        <label for="showMC">Select a Main Category</label>
        <select id="showMC" name="showMC" onchange="getSC(this); getTemplates();">
        </select>   </br>

        <label for="showSC">Select a Sub Category</label>
        <select id="showSC" name="showSC" onchange="getTemplates(this)">
            <option value="" disabled selected> Select a Sub Category </option>
        </select>   </br>

        <label for="showTemplates">Select A Template</label>
        <select id="showTemplates" name="showTemplates" >
            <option value="" disabled selected> Select a Template </option>
        </select>   </br>

        <label for="documentId">Type the Document ID</label>
        <input id="documentId" name="documentId" type="text" value="doc1"/> <br/>

        <label for="pdfFile">Select File: </label>
        <input id="pdfFile" type="file" name="pdfFile" size="30" required/><br/>

        <input id="ajaxStart" type="button" value="Upload" onclick="upload()"/>
    </form>
</fieldset>
</div>

    <div class="col-md-12" id="extractedText">

    </div>


</div>
<script>
    var client = new XMLHttpRequest();
    function upload()
    {
        var dropDownMC = document.getElementById("showMC");
        var mainCategory = dropDownMC.options[dropDownMC.selectedIndex].value;
        /* alert the user to select a main category before upload  */
        if(mainCategory==""){
            alert("Select a Main Category");
            return false;
        }

        var dropDownSC = document.getElementById("showSC");
        var subCategory = dropDownSC.options[dropDownSC.selectedIndex].value;
        /* alert the user to select a subcategory before upload */
        if(subCategory=="" || subCategory=="null"){
            alert("Select a Sub Category");
            return false;
        }

        var dropDownTemp = document.getElementById("showTemplates");
        var templateName = dropDownTemp.options[dropDownTemp.selectedIndex].value;
        /* alert the user to select a subcategory before upload */
        if(templateName=="" || templateName=="null"){
            alert("Select a Template");
            return false;
        }

        var documentId = document.getElementById("documentId").value;
        var file = document.getElementById("pdfFile");

        /* alert the user to input a value to the Document ID*/
        if(documentId==""){
            alert("Document ID is required");
            return false;
        }

        var fileName = $("#pdfFile").val();
        /* alert the user to select a File */
        if(fileName==""){
            alert("Select a PDF File");
            return false;
        }

        /* Create a FormData instance */
        var formData = new FormData();
        /* Add the file */
        formData.append("mainCategory", mainCategory);
        formData.append("subCategory", subCategory);
        formData.append("templateName", templateName);
        formData.append("documentId", documentId);
        formData.append("pdfFile", file.files[0]);

        client.open("post", "ExtractPdfController", true);
        client.send(formData);  /* Send to server */

        /*Set the upload button to be disabled */
        $("#ajaxStart").attr("disabled", true);
    }

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
