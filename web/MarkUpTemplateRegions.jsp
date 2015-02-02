<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Xractor/Extract Documents/Markup Template Regions</title>

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
                        <img class="menu-icon active" src="assets/img/images/m1.png" alt="" /> <br>
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
                        <li class="active"><a href="default.jsp">Manage Catogory/Templates</a></li>
                        <li><a href="data-dictionary.jsp">Data Dictionary</a></li>
                        <li><a href="/ExtractPdf.jsp">Extract Doc</a></li>
                        <li><a href="/user-management.jsp">User Management</a></li>
                        <li><a href="#">Help</a></li>

                    </ul>
                </div><!-- /.navbar-collapse -->
            </div><!-- /.container-fluid -->
        </nav>
    </div>
    <div class="row"></div>
</header>

<!-- tool bar -->
<nav id="nav-toolbar" class="navbar navbar-default" role="navigation" style="margin-top: 0px; border-radius: 0; background-color: transparent; border: #eee solid 1px; padding-top: 3px">
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
                    <button type="button" class="btn btn-primary" data-bind="click:selectElementType.bind($data, 'RE')">regex element</button>
                    <button type="button" class="btn btn-primary" data-bind="visible:(elementType() == 'pattern'), click:selectElementType.bind($data, 'PE')">pattern element</button>
                    <button type="button" class="btn btn-primary" data-bind="click:selectElementType.bind($data, 'LEE')">line-ending element</button>
                </div>

                <!-- element type tag -->
                <div style="position: absolute;" data-bind="id:id, visible:((elementType() == 'regex' || elementType() == 'pattern') && (isSubElementTypeSelected())), style:{left: (uiData.removeX()+15), top:uiData.removeY }">
                    <button type="button" class="btn btn-warning"><span data-bind="text:subElementType"></span></button>
                </div>

                <!-- ending tag element -->
                <div style="position: absolute;" data-bind="id:id, visible:((elementType() == 'regex' || elementType() == 'pattern') && (isHavingEndTag())), style:{left: (uiData.removeX()+65), top:uiData.removeY }">
                    <span class="btn btn-default">ending: <span data-bind="text:subElementEndTag">selected 2nd tag data</span></span>
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

                <!-- form element for repeatingSubElements in pattern -->
                <div class="well well-sm" style="position: absolute; width:400px" data-bind="id:id, visible:((elementType()=='pattern') && (isHavingRepeatedHeaders())), style:{left: ($parent.uiData.removeX() + 12), top:$parent.uiData.removeY}">
                    <form class="form-horizontal" role="form">
                        <!-- added elements *** do foreach *** -->
                        <div data-bind="foreach:repeatingSubElements">
                            <div class="form-group">
                                <label data-bind="text:start" class="col-sm-4 control-label">text</label>
                                <label data-bind="text:end" class="col-sm-4 control-label">text</label>
                                <div class="col-sm-2"><a data-bind="click:$parent.removeRepeatingElement" class="btn btn-danger" style="border-radius: 50%; height:24px; width:24px; padding: 2 0"><span class="glyphicon glyphicon-remove-circle"></span></a></div>
                            </div>
                        </div>
                        <!-- form elements -->
                        <div class="form-group">
                            <label class="col-sm-2 control-label">Start</label>
                            <div class="col-sm-10">
                                <input data-bind="value:bufferedRepeatingElement_start" type="text" class="form-control sub" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">End</label>
                            <div class="col-sm-10">
                                <input data-bind="value:bufferedRepeatingElement_end" type="text" class="form-control sub" />
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-10 col-sm-2">
                                <a class="btn btn-default" data-bind="click:addRepeatingElement" onclick="$('.sub').focus()"><span class="glyphicon glyphicon-ok"></span></a>
                            </div>
                        </div>
                        <div class="form-group"><div class="col-sm-offset-2 col-sm-8"><button data-bind="click:completeElement" class="btn btn-default" style="background-color: green; color:#ffffff">complete pattern element</button></div></div>
                    </form>
                </div>

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
