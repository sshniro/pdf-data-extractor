<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Mark Up Regions</title>

    <!-- jQuery -->
    <script type="text/javascript" src="assets/js/jquery.min.js"></script>
    <!-- bootstrap -->
    <script type="text/javascript" src="assets/js/bootstrap.min.js"></script>
    <link href="assets/css/bootstrap.min.css" rel="stylesheet">
    <!-- image select -->
    <link rel="stylesheet" type="text/css" href="assets/css/imgareaselect-default.css" />
    <script type="text/javascript" src="assets/js/jquery.imgareaselect.pack.js"></script>
    <!-- jQuery UI -->
    <script type="text/javascript" src="assets/ex-libraries/jquery-ui-1.11.2/jquery-ui.js"></script>
    <link type="text/css" rel="stylesheet" href="assets/ex-libraries/jquery-ui-1.11.2/jquery-ui.css" />
    <link type="text/css" rel="stylesheet" href="assets/ex-libraries/jquery-ui-1.11.2/jquery-ui.structure.css" />
    <link type="text/css" rel="stylesheet" href="assets/ex-libraries/jquery-ui-1.11.2/jquery-ui.theme.css" />

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

<!-- tool bar -->
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
            <button id ="enableEditableDivs" type="button">edit areas</button>
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



<!-- template previewing area -->
<div class="bs-example">
    <!-- tabular navigation -->
    <ul class="nav nav-tabs" data-bind="foreach:pagesData">
        <li  data-bind="attr:{id:pageIdLi, class:activeStatus}">
            <a data-bind="attr:{id:pageIdAn ,href:pageNumberHref},text:pageNumberName, click:$parent.changePage.bind(pageNumber())" data-toggle="tab"></a>
        </li>
    </ul>
    <!-- template holding area -->
    <div data-bind="template:{name:'pageTemplate', foreach:pagesData() }" class="tab-content"></div>
</div>

<!-- template for "template holding area" -->
<script type="text/html" id="pageTemplate">
    <div data-bind="attr:{id:pageId}"   class="tab-pane fade in active imageContainer">
        <img class='baseUI templatingImage' style="margin-top:10px"  data-bind="attr:{id:pageNumber,src: imagePath}"/>
    </div>
</script>



<!-- final json results -->
<p data-bind="text:sendingJson"></p><br>
<hr>
<p data-bind="text:sendingJsonFinal"></p>


<!-- selected main element -->
<script type="text/html" id="rectangleTemplate">

    <div class="bs-docs-section elementDecoMeta" data-bind="id:id, style:{left:uiData.metaStartX, top:uiData.metaStartY}" style="position:absolute;min-width:220px; padding:10px" >
        <legend style="margin-bottom: 10px">Meta Name</legend>
        <input type="text" class="form-control" data-bind="value:metaName"/>
    </div>

    <div class="mainElement baseUI editableDiv" style="position:absolute; border-style:solid; border-color:#2980b9; border-width: 3px;" data-bind="style:uiData.elementMap(), id:id"></div>

    <button  style="position:absolute; margin-top:-11; height:24; width:25; border-radius: 50px; z-index:1" data-bind="id:id, click:$parent.removeElement, style:{left: uiData.removeX, top:uiData.removeY}" type="button" class="btn btn-default btn-xs removeElement">
        <span class="glyphicon glyphicon-remove-circle"></span>
    </button>

    <div class="bs-docs-section elementDecoExtracted" style="position: absolute; min-width: 175;" data-bind="style:{top:uiData.extractedY, left:uiData.extractedX, maxWidth:width}, id:id" >

        <!--Appears only for text and table elements-->
        <div class="bs-callout" data-bind="visible:(elementType() != 'picture')">
            <h4>Extracted Data: </h4>
            <p data-bind="text:extractedData"></p>
        </div>

        <!--Appears only for text elements-->
        <div class="bs-callout" data-bind="visible:(elementType() == 'text')" >
            <h4>Extracted Filtered Data: </h4>
            <p data-bind="text:relevantData"></p>
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
    <div class="subElement" style=" position:absolute; border-style:solid; border-color:#2980b9; border-width: 3px;" data-bind="style:uiData.elementMap(), id:id"></div>
    <button  style="position: absolute; visibility:visible; margin-top:-11; height:24; width:25; border-radius: 50px" data-bind="id:id, click:$parent.removeElement, style:{left: uiData.removeX, top:uiData.removeY}" type="button" class="btn btn-default btn-xs removeSubElement">
        <span class="glyphicon glyphicon-remove-circle"></span>
    </button>
</script>


<!-- importing libraries -->
<script type="text/javascript" src="assets/js/knockout-3.2.0.js" ></script>
<script type="text/javascript" src="assets/js/markUpTemplateRegionsScripts/uiFunctions.js"> </script>
<script type="text/javascript" src="assets/js/markUpTemplateRegionsScripts/models.js"> </script>
<script type="text/javascript" src="assets/js/markUpTemplateRegionsScripts/viewModel.js"> </script>
<script type="text/javascript" src="assets/js/markUpTemplateRegionsScripts/messsageBroker.js"> </script>

<!-- UI behaviors -->
<script type="text/javascript">
    $('#enableEditableDivs').click(
            function(){
                $('.editableDiv').each(
                        function(){
                            $(this).draggable({
                                stop: function( event, ui ) {
                                    var dragableDiv = $(this)[0];
                                    var bounds = dragableDiv.getBoundingClientRect();
                                    var imgBounds = currentWorkingImg.getBoundingClientRect();
                                    var selection = {
                                        height:bounds.height,
                                        width:bounds.width,
                                        x1:bounds.left - imgBounds.left,
                                        x2:bounds.right - imgBounds.right,
                                        y1:bounds.top - imgBounds.top,
                                        y2:bounds.bottom - imgBounds.bottom
                                    };

                                    vm.currentSelection('text');
                                    reDrawingRouter(currentWorkingImg,selection,$(this)[0]);
                                    $(this).next('button').click();
                                }
                            });
                            $(this).resizable({
                                stop: function( event, ui ) {
                                    var dragableDiv = $(this)[0];
                                    var bounds = dragableDiv.getBoundingClientRect();
                                    var imgBounds = currentWorkingImg.getBoundingClientRect();
                                    var selection = {
                                        height:bounds.height,
                                        width:bounds.width,
                                        x1:bounds.left - imgBounds.left,
                                        x2:bounds.right - imgBounds.right,
                                        y1:bounds.top - imgBounds.top,
                                        y2:bounds.bottom - imgBounds.bottom
                                    };

                                    vm.currentSelection('text');
                                    reDrawingRouter(currentWorkingImg,selection,$(this)[0]);
                                    $(this).next('button').click();
                                }
                            });
                        }
                );
            }
    );
</script>

</body>
</html>
