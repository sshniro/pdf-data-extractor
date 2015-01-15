<%--
  Created by IntelliJ IDEA.
  User: niro273
  Date: 1/15/15
  Time: 4:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Pattern Finder</title>

    <!-- jQuery -->
    <script type="text/javascript" src="assets/js/jquery-1.10.2.js"></script>
    <!-- bootstrap -->
    <script type="text/javascript" src="assets/js/bootstrap.js"></script>
    <link rel="stylesheet" type="text/css" href="assets/css/bootstrap.css">

</head>

<script>
    var headerTag=0;
</script>

<body>


<div id="dataForm" class="col-md-12">
    <h1>Template Upload Form</h1>
    <fieldset>

        <legend>Upload File</legend>
        <form >

            <label for="headerName1">Add a header Name</label>
            <input id="headerName1" name="headerName1" type="text" value="Vendor Name:"/> <br/>

            <label for="headerStart1">Add Starting Tag</label>
            <input id="headerStart1" name="headerStart1" type="text" value="Vendor Name:"/> <br/>

            <label for="headerEnd1">Add Ending Tag</label>
            <input id="headerEnd1" name="headerEnd1" type="text" value="Address:"/> <br/>

            <label for="headerName2">Add a header Name</label>
            <input id="headerName2" name="headerName2" type="text" value="Address:"/> <br/>

            <label for="headerStart2">Add Starting Tag</label>
            <input id="headerStart2" name="headerStart2" type="text" value="Address:"/> <br/>

            <label for="headerEnd2">Add Ending Tag</label>
            <input id="headerEnd2" name="headerEnd2" type="text" value="Ship To:"/> <br/>

            <label for="pdfFile">Select File: </label>
            <input id="pdfFile" type="file" name="pdfFile" size="30" required/><br/>


            <input type="button" id="ajaxStart" value="Upload" onclick="upload()"/>
        </form>
    </fieldset>
</div>

<div id="extractedDataDiv">

</div>

<script>
    var client = new XMLHttpRequest();
    function upload()
    {
        var file = document.getElementById("pdfFile");
        var fileName = $("#pdfFile").val();
        /* alert the user to select a File */
        if(fileName==""){
            alert("Select a PDF File");
            return false;
        }

        /* Create a FormData instance */
        var formData = new FormData();
        /* Add the file */
        formData.append("pdfFile", file.files[0]);

        client.open("post", "PatternControllerServlet", true);
        client.send(formData);  /* Send to server */

    }

    client.onload = function() {
        if (client.status == 200) {
            var jsonData = JSON.parse(client.responseText);
            sendFormInfo(jsonData.id);
        }
        /*Enable the upload button after receivin a extractedData from the server */
        //$("#ajaxStart").attr("disabled", false);
    }

    function sendFormInfo(id){
        var headerData={data:[]};
        var i;

        var headerName = "headerName";
        var headerStart = "headerStart";
        var headerEnd = "headerEnd";


        for(i=1 ; i < 100; i++){
            if (!document.getElementById(headerName+i)) {
                alert("Header Name is required to the" + i + "form element");
                break;
            }
            if (!document.getElementById(headerStart+i)) {
                alert("Header Start Tag is required to the" + i + "form element");
                break;
            }
            if (!document.getElementById(headerEnd+i)) {
                alert("Header End Tag is required to the" + i + "form element");
                break;
            }

            var headerNameVal = $("#"+headerName+i).val();
            var headerStartVal = $("#"+headerStart+i).val();
            var headerEndVal = $("#"+headerEnd+i).val();

            headerData.data.push({headerName : headerNameVal , startTag : headerStartVal , endTag : headerEndVal});

        }
        var data = { extractedId : id,headerDataBeanList : headerData.data};

        $.ajax({
            type: 'POST', url: 'PatternControllerServlet',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            data: JSON.stringify(data),
            success: function(data, textStatus, jqXHR) {
                result = JSON.parse(jqXHR.responseText);
                //alert(JSON.stringify(result.extractedData));
                $("#extractedDataDiv").append(result.extractedData);
            }
        });
    }

</script>

</body>
</html>
