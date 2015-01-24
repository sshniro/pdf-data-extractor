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
    var dicObj;

    <% String uploadResponse=(String) session.getAttribute("uploadJsonResponse");%>
    var jsonObj = '<% out.print(uploadResponse);%>';
    responseObj = JSON.parse(jsonObj);

/*
        var data={ 'request' : "getAllDicItems"};
        self.overlayNotification('loading...');
        $("#overlay").css("display","block");
        $.ajax({
            type: 'POST', url: 'DictionaryController',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            data: JSON.stringify(data),
            success: function(data, textStatus, jqXHR) {
                dicObj = JSON.parse(jqXHR.responseText);
                self.currentDic([]);
                for(item in dicObj) {
                    self.currentDic.push(new Keyword(dicObj[item]));
                }
                $('#dicFormName').focus();
            }
        });
*/


</script>

<style>
    body{
        font-family: "calibri";
    }
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
    ul.knockoutIterable
    {
        list-style-type: none;
    }
    .vertical-text {
        -ms-transform: rotate(90deg); /* IE 9 */
        -webkit-transform: rotate(90deg); /* Chrome, Safari, Opera */
        transform: rotate(90deg);
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
                    <li><a style="cursor: pointer" onclick="$('#nav-toolbar').show('500')">Tools&nbsp;<small class="glyphicon glyphicon-chevron-down"></small></a></li>
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

<!-- tool bar -->
<nav id="nav-toolbar" class="navbar navbar-default" role="navigation" style="margin-top: -20px; border-radius: 0; background-color: transparent; border: #eee solid 1px; padding-top: 3px">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="toolbar_collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" style="cursor: pointer" onclick="$('#nav-toolbar').hide('500')"><span class="glyphicon glyphicon-chevron-up"></span></a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="toolbar_collapse" style="padding-top: 6px;">
            <ul class="nav navbar-nav" style="margin: 0 0 0 10px">
                <li>
                    <table><tbody>
                    <tr><td>
                        <div id="elementTypeSelector" class="btn-group">
                            <button id ="textSelect"  data-bind="click:textButton"  type="button" class="btn btn-default"><span class="glyphicon glyphicon-text-width"></span>ext</button>
                            <button id ="tableSelect"  data-bind="click:tableButton"  type="button" class="btn btn-default"><span class="glyphicon glyphicon-list-alt"></span>&nbsp;Table</button>
                            <button id ="pictureSelect" data-bind="click:pictureButton" type="button" class="btn btn-default"><span class="glyphicon glyphicon-picture"></span>&nbsp;Picture</button>
                            <button id ="regexSelect" data-bind="click:regexButton" type="button" class="btn btn-default"><span class="glyphicon glyphicon-list-alt"></span>&nbsp;Regex</button>
                            <button id ="patternSelect" data-bind="click:patternButton" type="button" class="btn btn-default"><span class="glyphicon glyphicon-list-alt"></span>&nbsp;Pattern</button>
                        </div>
                    </td></tr>

                    </tbody></table>
                </li>
                <li style="top: -7px;"><span style="margin-left: 15vw; font-size: xx-large; color: gray">[<span id="runningInstructions">Select Element Type</span>]</span></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <table><tbody>
                    <tr><td>
                        <div class="btn-group">
                            <button id ="enableEditableDivs" data-bind="click:editButton" type="button" class="btn btn-default"><span class="glyphicon glyphicon-move"></span>&nbsp;Edit</button>
                            <button id ="cancelSelection" data-bind="click:cancelButton" type="button" class="btn btn-danger"><span class="glyphicon glyphicon-floppy-remove"></span>&nbsp;Cancel</button>
                            <button id ="saveSelection" data-bind="click:saveButton" type="button" class="btn btn-warning"><span class="glyphicon glyphicon-floppy-disk"></span>&nbsp;Save</button>
                        </div>
                    </td></tr>

                    </tbody></table>
                </li>
                <li>
                    <div class="btn-group" style="margin-left: 10px">
                        <button data-bind="click:sendJson" id ="persist" type="button" class="btn btn-success"><span class="glyphicon glyphicon-floppy-saved"></span>&nbsp;Save Template</button>
                    </div>
                </li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>

<%--<div id="Map" class="well well-sm">
    Starting Coordinate <span id="starting"></span>
    <br>
    Ending Coordinate <span id="ending"></span>
    <br>
</div>--%>

<div data-bind="template:{name:'rectangleTemplate', foreach:dataElements()() }"></div>
<%--<div data-bind="template:{name:'subRectangleTemplate', foreach:subDataElements()() }"></div>--%>

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
        <img class='baseUI templatingImage' style="margin-top:10px; max-width:100%; height:auto"  data-bind="attr:{id:pageNumber,src: imagePath}"/>
    </div>
</script>



<!-- final json results -->
<p data-bind="text:sendingJson"></p><br>
<hr>
<p data-bind="text:sendingJsonFinal"></p>


<!-- selected main element -->
<script type="text/html" id="rectangleTemplate">

    <div data-bind="id:id, style:{left:uiData.metaStartX, top:uiData.metaStartY}" style="position:absolute;min-width:110px; padding:7px; z-index:1" >
        <!-- Meta/Dic elements-->

        <!-- Dic element -->
        <div class="bs-docs-section elementDecoMeta" data-bind="id:id" style="position: relative; padding: 7px; float:left">
            <legend style="margin-bottom: 10px; font-size: 15px">Meta</legend>
            <div style="display:flex">
                <input type="text" class="form-control" data-bind="value:metaName" style="margin:1px"/>
                <select data-bind="options:elementViseCurrentDic, optionsText:'name', value:selectedDictionaryItem" id="dictionaryMapping" class="form-control" style="margin:1px"></select>
            </div>
        </div>

        <!--Meta Tag Appears only for Table elements-->
        <div class="bs-docs-section subElementDecoMeta" data-bind="id:id, visible:(elementType() == 'table' && id() !== undefined)" style="position:relative; float:left;min-width:110px; padding:7px;" >
            <legend style="margin-bottom: 10px; font-size: 15px">Sub-Meta <span data-bind="text:currentSubElement().index">1</span></legend>
            <div style="display:flex">
                <input type="text" class="form-control" data-bind="value:currentSubElement().metaName" style="margin:1px"/>
                <select data-bind="options:currentSubElement().elementViseCurrentDic, optionsText:'name', value: currentSubElement().selectedDictionaryItem" id="dictionaryMapping" class="form-control" style="margin:1px"></select>
            </div>
        </div>

    </div>

    <!--Main Rectangle element and inner subelement-->
    <div class="mainElement baseUI editableDiv" style="position:absolute; border-style:solid; border-color:#2980b9; border-width: 3px;" data-bind="id:id, style:uiData.elementMap(),  click:$root.selectRectangle">
        <ul class="knockoutIterable" style="padding: 0" data-bind="foreach:$data.subElements()">
            <li data-bind="id:id">
                <!-- selected element -->
                <div class="subElement baseUI" style="position: absolute; border-style:solid; border-color:#2980b9; border-width: 3px;" data-bind="click:$parent.showNewSubElement,id:id, style:{width:uiData.width,height:uiData.height,left:uiData.startX, top:uiData.startY}">
                </div>

                <!-- element type select -->
                <div style="position: absolute" class="btn-group-vertical" role="group" data-bind="id:id, visible:((elementType() == 'regex' || elementType() == 'pattern') && !(isSubElementTypeSelected())), style:{left: (uiData.removeX()+15), top:uiData.removeY }">
                    <button type="button" class="btn btn-primary" data-bind="click:selectElementType.bind($data, 'NE')">normal element</button>
                    <button type="button" class="btn btn-primary" data-bind="click:selectElementType.bind($data, 'RE')">repeating element</button>
                    <button type="button" class="btn btn-primary" data-bind="click:selectElementType.bind($data, 'NNE')">non-ending element</button>
                </div>

                <!-- element type tag -->
                <div style="position: absolute;" data-bind="id:id, visible:((elementType() == 'regex' || elementType() == 'pattern') && (isSubElementTypeSelected())), style:{left: (uiData.removeX()+15), top:uiData.removeY }">
                    <button type="button" class="btn btn-warning" data-bind="click:changeElementType"><span data-bind="text:subElementType"></span></button>
                </div>

                <!-- ending tag element -->
                <div style="position: absolute;" data-bind="id:id, visible:((elementType() == 'regex' || elementType() == 'pattern') && (isHavingEndTag())), style:{left: (uiData.removeX()+65), top:uiData.removeY }">
                    <span class="btn btn-default">ending: <span>selected 2nd tag data</span></span>
                </div>

                <!-- selected element close button -->
                <button  style="position: absolute; visibility:visible; margin-top:-11; height:24; width:25; border-radius: 50%" data-bind="id:id, click:$root.removeElement, style:{left: uiData.removeX, top:uiData.removeY}" type="button" class="btn btn-default btn-xs removeSubElement">
                    <span class="glyphicon glyphicon-remove-circle"></span>
                </button>
                <!-- selected element index -->
                <button  style="position: absolute; visibility:visible; margin-top:-11; height:24; width:25; border-radius: 50%" data-bind="id:id, visible:(elementType() == 'table'),id:id, click:$parent.setCurrentSubElement, style:{left: uiData.removeX, top:(uiData.removeY() + uiData.height) }" type="button" class="btn btn-default btn-xs selectSubElement">
                    <span data-bind="text:$index">1</span>
                </button>

                <!-- ending tag close button -->
                <button  style="position: absolute; visibility:visible; margin-top:-11; height:24; width:25; border-radius: 50%" data-bind="id:id, visible:((elementType() == 'regex' || elementType() == 'pattern') && (isHavingEndTag())), style:{left: (uiData.removeX()+63), top:uiData.removeY}" type="button" class="btn btn-default btn-xs removeSubElement">
                    <span class="glyphicon glyphicon-remove-circle"></span>
                </button>

            </li>
        </ul>

        <!--Main element remove button-->
        <button  style="position:absolute; margin-top:-11; height:24; width:25; border-radius: 50px; z-index:1" data-bind="id:id, click:$parent.removeElement, style:{left: uiData.removeX, top:uiData.removeY}" type="button" class="btn btn-default btn-xs removeElement">
            <span class="glyphicon glyphicon-remove-circle"></span>
        </button>

    </div>

    <%--    <button  style="position:absolute; margin-top:-11; height:24; width:25; border-radius: 50px; z-index:1" data-bind="id:id, click:$parent.removeElement, style:{left: uiData.removeX, top:uiData.removeY}" type="button" class="btn btn-default btn-xs removeElement">
            <span class="glyphicon glyphicon-remove-circle"></span>
        </button>--%>

    <div class="bs-docs-section elementDecoExtracted" style="position: absolute; min-width: 175;z-index:2" data-bind="style:{top:uiData.extractedY, left:uiData.extractedX, maxWidth:width}, id:id" >

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
            <img class='extractedImage' style="margin-top: 20;" data-bind="visible:(elementType() == 'picture'),attr:{src: extractedData}"/>
        </div>
    </div>
    <div class="elementDecoTableExtract" style="position: absolute;" data-bind="style:{top:(uiData.extractedY() - 40), left:uiData.removeX()}, visible:(elementType() == 'table'), id:id" >
        <button id ="extractTable pull-right"  data-bind="click:$parent.extractTable" type="button" class="btn btn-default">Extract Table</button>
    </div>

</script>


<script type="text/html" id="subRectangleTemplate">
    <%--
        <div class="subElement" style=" position:absolute; border-style:solid; border-color:#2980b9; border-width: 3px;" data-bind="style:uiData.elementMap(), id:id"></div>
        <button  style="position: absolute; visibility:visible; margin-top:-11; height:24; width:25; border-radius: 50px" data-bind="id:id, click:$parent.removeElement, style:{left: uiData.removeX, top:uiData.removeY}" type="button" class="btn btn-default btn-xs removeSubElement">
            <span class="glyphicon glyphicon-remove-circle"></span>
        </button>
        --%>
</script>


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

<script type="text/javascript" src="assets/js/markUpTemplateRegionsScripts/messsageBroker.js"> </script>
<script type="text/javascript" src="assets/js/markUpTemplateRegionsScripts/models.js"> </script>
<script type="text/javascript" src="assets/js/markUpTemplateRegionsScripts/viewModel.js"> </script>
<script type="text/javascript" src="assets/js/markUpTemplateRegionsScripts/uiFunctions.js"> </script>
<script type="text/javascript" src="assets/js/markUpTemplateRegionsScripts/initScript.js"> </script>

<!-- UI behaviors -->
<script type="text/javascript">

</script>

</body>
</html>
