<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Mark Up Regions</title>
    <script type="text/javascript" src="assets/js/jquery.min.js"></script>

    <%--link href="assets/css/bootstrap.min.css" rel="stylesheet"--%>
    <script type="text/javascript" src="assets/js/bootstrap.min.js"></script>
    <link href="assets/css/bootstrap.min.css" rel="stylesheet">
    <%--link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css"--%>

    <link rel="stylesheet" type="text/css" href="assets/css/imgareaselect-default.css" />
    <script type="text/javascript" src="assets/js/jquery.imgareaselect.pack.js"></script>

    <script>
        var responseObj = null;
        var initDataJSON;
        var initData;

        window.onload = assignSessionAttributes;
        function assignSessionAttributes() {
            <% String uploadResponse=(String) session.getAttribute("uploadJsonResponse");%>
            var jsonObj='<% out.print(uploadResponse);%>';
            responseObj = JSON.parse(jsonObj);


            if(responseObj === null){
                initDataJSON = '{"mainCategory":"Sales Order","subCategory":"Supplier 10","templateName":"template1","imageRelativePaths":["assets/img/pdfimage1.jpg","assets/img/pdfimage1.jpg"]}'
                initData = JSON.parse(initDataJSON);
            }
            else{
                initData = responseObj;
                var imageRelativePaths = initData.imageRelativePaths;
                var newImageRelativePaths = [];
                for(var key in imageRelativePaths){
                    var imageRelativePath = imageRelativePaths[key];
                    imageRelativePath = atob(imageRelativePath);
                    imageRelativePath = imageRelativePath.split("\\").join("/");
                    newImageRelativePaths.push(imageRelativePath);
                }
                initData.imageRelativePaths = newImageRelativePaths;
            }
            vm.initExtractionPages();
        }
    </script>
    <style>
        span {
            word-wrap:break-word;
        }

        .bs-callout {
            padding: 5px;
            margin: 12px;
            border: 1px solid #eee;
            border-left-width: 5px;
            border-radius: 3px;
            border-left-color: #5bc0de;
            margin-top: -5px;
            background-color: #fff;
        }
        .bs-callout h4 {
            margin: 5 5 0 5px;

            color: #5bc0de;
        }
        .bs-callout p{
            margin: 5 5 5px;
        }




        .bs-callout p:last-child {
            margin-bottom: 0;
        }

        .bs-docs-section{
            position:absolute;
            background-color:#fff ;
            border:1px solid #eee;
            padding-top:20px;
            border-radius: 10px;

        }

        img{
            margin: 70 6 5 5;
            border: 1px solid #eee;
            max-width: 100%;

        }






    </style>

</head>
<body>
<nav class="navbar navbar-default" style="position:fixed; width:100%; z-index: 2;" role="navigation">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navbar-collapse-1">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        &nbsp;
        <div id="elementTypeSelector" class="btn-group btn-group-lg" style="padding:5 5 0 0px; margin: 0 0 5 0px">
            <button id ="textSelect"    type="button" class="btn btn-default">Text</button>
            <button id ="tableSelect"    type="button" class="btn btn-default">Table</button>
            <button id ="pictureSelect"  type="button" class="btn btn-default">Picture</button>
        </div>
        <button id ="cancelSelection" style="width:75px; margin: 0 5 0 0px; height: 40" type="button" class="btn btn-danger  btn-default">Cancel</button>
        <button id ="saveSelection" style=" margin: 0 5 0 0px; height: 40" type="button" class="btn btn-warning  btn-default">Save Element</button>
    </div>


    <!--CENTRE-->
    <div  href="#"><span style="margin:0 0 0 250px; font-size:25px" id="runningInstructions">Select Element Type</span></div>
    <div class="navbar-right">
        <button data-bind="click:sendJson" id ="persist" style="margin: -28 10 5 5px; height: 40" type="button" class="btn btn-success btn-default"><strong>Save Template</strong></button>
    </div>
</nav>
<br>
<br>
<br>
<div id="Map">
    Starting Coordinate <span id="starting"></span>
    <br>
    Ending Coordinate <span id="ending"></span>
    <br>
</div>
<div data-bind="template:{name:'rectangleTemplate', foreach:dataElements()() }"></div>
<div data-bind="template:{name:'subRectangleTemplate', foreach:subDataElements()() }"></div>





<div class="bs-example">
    <ul class="nav nav-tabs" data-bind="foreach:pagesData">
        <li  data-bind="attr:{id:pageIdLi, class:activeStatus}">
            <a data-bind="attr:{id:pageIdAn ,href:pageNumberHref},text:pageNumberName, click:$parent.changePage.bind(pageNumber())" data-toggle="tab"></a>
        </li>
    </ul>
    <div data-bind="template:{name:'pageTemplate', foreach:pagesData() }" class="tab-content">

    </div>
</div>
<script type="text/html" id="pageTemplate">
    <div data-bind="attr:{id:pageId}"   class="tab-pane fade in active imageContainer">
        <img class='baseUI templatingImage' style="margin-top:10px"  data-bind="attr:{id:pageNumber,src: imagePath}"/>
    </div>

</script>





<p data-bind="text:sendingJson"></p>
<br>
<hr>
<p data-bind="text:sendingJsonFinal"></p>

<script type="text/html" id="rectangleTemplate">


    <div class="bs-docs-section elementDecoMeta" data-bind="id:id, style:{left:uiData.metaStartX, top:uiData.metaStartY}" style="position:absolute;min-width:220px; padding:10px" >
        <legend style="margin-bottom: 10px">Meta Name</legend>
        <input type="text" class="form-control" data-bind="value:metaName"/>
    </div>

    <div class="mainElement baseUI" style="position:absolute; border-style:solid; border-color:#2980b9; border-width: 3px;" data-bind="style:uiData.elementMap(), id:id"></div>

    <button  style="position:absolute; margin-top:-11; height:24; width:25; border-radius: 50px; z-index:1" data-bind="id:id, click:$parent.removeElement, style:{left: uiData.removeX, top:uiData.removeY}" type="button" class="btn btn-default btn-xs removeElement">
        <span class="glyphicon glyphicon-remove-circle"></span>
    </button>

    <div class="bs-docs-section elementDecoExtracted" style="position: absolute; min-width: 175;" data-bind="style:{top:uiData.extractedY, left:uiData.extractedX, maxWidth:width}, id:id" >

        <!--Appears only for text and table elements-->
        <div class="bs-callout" data-bind="visible:(elementType() != 'picture')">

            <h4>Extracted Data: </h4>
            <p data-bind="text:extractedData">

            </p>
        </div>

        <!--Appears only for text elements-->
        <div class="bs-callout" data-bind="visible:(elementType() == 'text')" >
            <h4>Extracted Filtered Data: </h4>
            <p data-bind="text:relevantData">

            </p>

        </div>

        <!--Appears only for image elements-->
        <div class="bs-callout" style="padding-right: 10;" data-bind="visible:(elementType() == 'picture')" >
            <h4>Extracted Data: </h4>
            <img class='extractedImage' style="margin-top: 20;" data-bind="attr:{src: extractedData}"/>
        </div>

    </div>

    <div class="elementDecoTableExtract" style="position: absolute;" data-bind="style:{top:(uiData.extractedY() - 40), left:(uiData.removeX() - 98)}, visible:(elementType() == 'table'), id:id" >
        <button id ="extractTable pull-right"  data-bind="click:$parent.extractTable" type="button" class="btn btn-default">Extract Table</button>
    </div>



</script>

<script type="text/html" id="subRectangleTemplate">
    <div class="subElement" style=" position:absolute; border-style:solid; border-color:#2980b9; border-width: 3px;" data-bind="style:uiData.elementMap(), id:id">

    </div>
    <button  style="position: absolute; visibility:visible; margin-top:-11; height:24; width:25; border-radius: 50px" data-bind="id:id, click:$parent.removeElement, style:{left: uiData.removeX, top:uiData.removeY}" type="button" class="btn btn-default btn-xs removeSubElement">
        <span class="glyphicon glyphicon-remove-circle"></span>
    </button>

</script>




<%--script type="text/javascript" src="assets/js/knockout-3.2.0.js" ></script>
<script type="text/javascript" src="assets/js/ExtractorUIVerFunctions.js"> </script>
<script type="text/javascript" src="assets/js/ExtractorUiVer2Models.js"> </script>
<script type="text/javascript" src="assets/js/ExtractorUIVer2ViewModel.js"> </script>
<script type="text/javascript" src="assets/js/messsageBroker.js"> </script--%>

<script type="text/javascript" src="assets/js/knockout-3.2.0.js" ></script>
<script type="text/javascript" src="assets/js/markUpTemplateRegionsScripts/uiFunctions.js"> </script>
<script type="text/javascript" src="assets/js/markUpTemplateRegionsScripts/models.js"> </script>
<script type="text/javascript" src="assets/js/markUpTemplateRegionsScripts/viewModel.js"> </script>
<script type="text/javascript" src="assets/js/markUpTemplateRegionsScripts/messsageBroker.js"> </script>

</body>


</html>
