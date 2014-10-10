<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Upload Template</title>

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
                    <li ><a id="templateExtract" href="#">Extract Doc</a></li>
                    <li class="active"><a id="templateUpload" href="#">Create Template</a></li>
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
        <h1>Template Upload Form</h1>
        <fieldset>

        <legend>Upload File</legend>
            <form >

                <label for="showMC">Select a Main Category</label>
                <select id="showMC" name="showMC" onchange="getSC(this)">
                </select>   </br>

                <label for="showSC">Select a Sub Category</label>
                <select id="showSC" name="showSC" >
                <option value="" disabled selected> Select a Sub Category </option>
                </select>   </br>

                <label for="templateName">Type the TemplateName</label>
                <input id="templateName" name="templateName" type="text" value="template1"/> <br/>

                <label for="pdfFile">Select File: </label>
                <input id="pdfFile" type="file" name="pdfFile" size="30" required/><br/>

                <input type="button" id="ajaxStart" value="Upload" onclick="upload()"/>
            </form>
        </fieldset>
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


        var templateName = document.getElementById("templateName").value;
        var file = document.getElementById("pdfFile");

        /* alert the user to input a value to the Document ID*/
        if(templateName==""){
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
        formData.append("pdfFile", file.files[0]);

        client.open("post", "TemplateUploadController", true);
        client.send(formData);  /* Send to server */

        /*Set the upload button to be disabled */
        $("#ajaxStart").attr("disabled", true);
    }

    client.onload = function() {
        if (client.status == 200) {
            if(client.responseText=="success") {
                var successUrl = "MarkUpTemplateRegions.jsp";
                window.location.href = successUrl;
            }else{
                alert(client.responseText);
            }
        }
        /*Enable the upload button after receivin a extractedData from the server */
        $("#ajaxStart").attr("disabled", false);
    }

</script>




</body>
</html>
