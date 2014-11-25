<%--
  Created by IntelliJ IDEA.
  User: Dehan De Croos
  Date: 10/11/2014
  Time: 1:03 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<form id="file-form">
    <input type="file" id="file-select" name="photos[]" />
    <button data-bind="click:uploadAndGet" id="upload-button">Upload</button>
</form>


<script type="text/javascript" src="assets/js/knockout-3.2.0.js" ></script>
<script type="text/javascript" src="assets/js/excelExtractScripts/excelExtractUiFunctions.js.js"> </script>
<script type="text/javascript" src="assets/js/excelExtractScripts/excelExtractModels.js"> </script>
<script type="text/javascript" src="assets/js/excelExtractScripts/excelExtractViewModel.js"> </script>
<script type="text/javascript" src="assets/js/excelExtractScripts/excelExtractMessageBroker.js"> </script>
    </body>
</html>
