<%--
  User: K D K Madusanka
  Date: 11/11/2014
  Time: 11:43 AM
  ----------------------------------
  Log:
  November 11, 2014 -    - K D K Madusanka    - Creating basic UI wireframe
  December 11, 2014 -    - K D K Madusanka    - Integrate with service / modify UI
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <!-- jQuery -->
    <script type="text/javascript" src="assets/js/jquery-1.10.2.js"></script>
    <!-- bootstrap -->
    <script type="text/javascript" src="assets/js/bootstrap.js"></script>
    <link rel="stylesheet" type="text/css" href="assets/css/bootstrap.css">

    <!-- custom css -->
    <style type="text/css">
        body{
            font-family: "Calibri";
        }
        .box{
            border: 1px solid #a0a0a0;
            height: 85vh;
            width: 100%;
            padding: 5px;
            overflow-y: auto;
        }
        .box header{
            font-size: 1.2em;
            font-weight: bolder;
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
                    <li><a onclick="window.location.href = 'index.jsp'" class="btn btn-default" style="padding: 5px; margin-top: 10px; max-width: 100px;">Logout <span class="glyphicon glyphicon-log-out"></span></a></li>
                </ul>
            </div><!-- /.navbar-collapse -->
        </div><!-- /.container-fluid -->
    </nav>
</header>

<!-- container -->
<div style="padding: 0 20px 0 20px">
    <div class="row">

        <!-- defining dictionary -->
        <div class="col-sm-3" style="padding: 5px 5px 5px 10px">
            <div class="box">
                <header>
                    <p>Defining Data Dictionary</p>
                    <hr />
                </header>
                <section style="padding: 10px">
                    <form id="dicForm" class="form-horizontal" role="form">
                        <div class="form-group">
                            <label class="col-sm-3">Name:</label>
                            <div class="col-sm-8">
                                <input data-bind="value:newData().name" id="dicFormName" type="text" class="form-control" autofocus="autofocus"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3">Type:</label>
                            <div class="col-sm-8">
                                <select data-bind="value:newData().type" class="form-control"></select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3">Description:</label>
                            <div class="col-sm-8">
                                <textarea data-bind="value:newData().description" class="form-control" rows="3"></textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3">Data Type:</label>
                            <div class="col-sm-8">
                                <select data-bind="value:newData().dataType" class="form-control">
                                    <option>int</option>
                                    <option>string</option>
                                    <option>char</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3">Length:</label>
                            <div class="col-sm-8">
                                <input data-bind="value:newData().length" type="number" min="0" class="form-control" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3">Default Value:</label>
                            <div class="col-sm-8">
                                <input data-bind="value:newData().defaultValues" type="text" class="form-control" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3">Allowed Values:</label>
                            <div class="col-sm-8">
                                <input data-bind="value:newData().allowedValues" type="text" class="form-control" />
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-3 col-sm-8">
                                <input data-bind="click:addNew" type="submit" class="btn btn-default" value=" Add " />
                            </div>
                        </div>
                    </form>
                </section>
            </div>
        </div>

        <!-- current dictionary -->
        <div class="col-sm-9" style="padding: 5px 10px 5px 5px">
            <div class="box">
                <header>
                    <p>Current Data Dictionary</p>
                    <hr />
                </header>
                <section>
                    <table class="table table-responsive">
                        <thead>
                            <tr>
                                <th>Name</th><th>Type</th><th>Description</th><th>Data Type</th><th>Length</th><th>Default Value</th><th>Allowed Values</th><th></th>
                            </tr>
                        </thead>
                        <tbody data-bind="foreach:currentDic">
                            <tr>
                                <td data-bind="text:name">Customer</td>
                                <td data-bind="text:type">int</td>
                                <td data-bind="text:description" style="width: 15%; overflow: auto;">Test Desc.</td>
                                <td data-bind="text:dataType">6</td>
                                <td data-bind="text:length">5</td>
                                <td data-bind="text:defaultValues">0</td>
                                <td data-bind="text:allowedValues">x=>x<999999&x>0</td>
                                <td style="max-width: 40px"><button data-bind="click:$parent.removeDicItem" class="btn btn-default" style="color:red"><span class="glyphicon glyphicon-trash"></span></button></td>
                            </tr>
                        </tbody>
                    </table>
                </section>
            </div>
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
<script type="text/javascript" src="assets/js/dataDictionaryScripts/dataDictionaryVM.js"> </script>

<!-- custom js -->
<script type="text/javascript">
    $(document).ajaxStart(function(){
        $("#overlay").css("display","block");
    });
    $(document).ajaxComplete(function(){
        $("#overlay").css("display","none");
        dataDicVM.overlayNotification('');
    });
</script>


</body>
</html>