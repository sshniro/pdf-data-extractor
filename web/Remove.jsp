
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <!-------------------------------- CSS Files------------------------------------>
    <link rel="stylesheet" type="text/css" href="assets/css/bootstrap.css">

    <!-------------------------------- JS Files------------------------------------>
    <script type="text/javascript" src="assets/js/jquery-1.10.2.js"></script>
    <script type="text/javascript" src="assets/js/bootstrap.js"></script>
    <script type="text/javascript" src="assets/js/JspFormPopulate.js"></script>
    <script type="text/javascript" src="assets/js/PageNavigator.js"></script>

    <script>
        var isMCValid = true;
        window.onload = onLoadFuncs();
        function onLoadFuncs(){
            getMC();
        }
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
                    <li><a id="templateUpload" href="#">Create Template</a></li>
                    <li class="dropdown active">
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
        <h3>Div tag Delete a Template</h3>

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

        <input id="ajaxRmvTemp" type="button" value="remove" onclick="deleteTemp()"/>
    </div>


    <div class="col-md-12">
        <h3>Div tag to Delete a Sub Category</h3> </br>
        <label for="showMC2">Select a Main Category</label>
        <select id="showMC2" name="showMC2" onchange="getSC(this);">
        </select>   </br>

        <label for="showSC2">Select a Sub Category</label>
        <select id="showSC2" name="showSC2" >
            <option value="" disabled selected> Select a Sub Category </option>
        </select>   </br>

        <input id="ajaxRmvSC" type="button" value="remove" onclick="deleteSC()"/>
    </div>

    <div class="col-md-12">
        <h3>Div tag to Delete a Main Category</h3> </br>
        <label for="showMC3">Select a Main Category</label>
        <select id="showMC3" name="showMC2" >
        </select>   </br>

        <input id="ajaxRmvMC" type="button" value="remove" onclick="deleteMC()"/>
    </div>

</div>

<script>
    function deleteTemp() {
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

        var deleteTempData = { request: "deleteTemp",mainCategory:mainCategory,
                            subCategory: subCategory, templateName : templateName};

        $.ajax({
            type: 'POST', url: 'ManageCategoriesController',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            data: JSON.stringify(deleteTempData),
            success: function(data, textStatus, jqXHR) {
                var messages = JSON.parse(jqXHR.responseText);
                /* If the status is success refresh the Main Categories Select Options  */
                if(messages.status===true){
                    getTemplates();
                }
                alert(messages.statusMessage);
            }
        });
    }

    function deleteSC(){
        var dropDownMC = document.getElementById("showMC2");
        var mainCategory = dropDownMC.options[dropDownMC.selectedIndex].value;
        /* alert the user to select a main category before upload  */
        if(mainCategory==""){
            alert("Select a Main Category");
            return false;
        }

        var dropDownSC = document.getElementById("showSC2");
        var subCategory = dropDownSC.options[dropDownSC.selectedIndex].value;
        /* alert the user to select a subcategory before upload */
        if(subCategory=="" || subCategory=="null"){
            alert("Select a Sub Category");
            return false;
        }

        var deleteSCData = { request: "deleteSubCat",mainCategory:mainCategory,subCategory: subCategory};

        $.ajax({
            type: 'POST', url: 'ManageCategoriesController',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            data: JSON.stringify(deleteSCData),
            success: function(data, textStatus, jqXHR) {
                var messages = JSON.parse(jqXHR.responseText);
                /* If the status is success refresh the Main Categories Select Options  */
                if(messages.status===true){
                    getSC();
                }
                alert(messages.statusMessage);
            }
        });
    }

    function deleteMC(){
        var dropDownMC = document.getElementById("showMC3");
        var mainCategory = dropDownMC.options[dropDownMC.selectedIndex].value;
        /* alert the user to select a main category before upload  */
        if(mainCategory==""){
            alert("Select a Main Category");
            return false;
        }


        var deleteSCData = { request: "deleteMainCat",mainCategory:mainCategory};

        $.ajax({
            type: 'POST', url: 'ManageCategoriesController',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            data: JSON.stringify(deleteSCData),
            success: function(data, textStatus, jqXHR) {
                var messages = JSON.parse(jqXHR.responseText);
                /* If the status is success refresh the Main Categories Select Options  */
                if(messages.status===true){
                    getMC();
                }
                alert(messages.statusMessage);
            }
        });
    }
</script>


</body>
</html>
