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
            getMCList();
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
        <h3>Div tag to Create a New Main Category</h3>
        <form>
            <label for="createMainCategory">Main Category</label>
            <input id="createMainCategory" type="text" name="createMainCategory" placeholder="Main Category Name" required />
            <input type="button" value="create" onclick="createMC()"/>
        </form>
    </div>

    <div class="col-md-12">
        <h3>Div tag to Create a New Sub CateGory</h3>
        <form >
            <select id="showMC" name="showMC" >
            </select>

            <label for="createSC">Sub Category</label>

            <input id="createSC" type="text" name="createSC" placeholder="Sub Category Name" required />
            <input type="button" value="create" onclick="createSubCat()"/>

        </form>
    </div>

    <script>
        function createMC() {
            var mcName= document.getElementById("createMainCategory").value;
            var CreateMCData={ request : "createMainCategory",
                        mainCategory: mcName};

            if(mcName==""){
                alert("Main Category Name is Required");
                return;
            }

            $.ajax({
                type: 'POST', url: 'ManageCategoriesController',
                contentType: 'application/json; charset=utf-8',
                dataType: 'json',
                data: JSON.stringify(CreateMCData),
                success: function(data, textStatus, jqXHR) {
                    var messages = JSON.parse(jqXHR.responseText);
                    /* If the status is success refresh the Main Categories Select Options  */
                    if(messages.status===true){
                        getMC();
                        getMCList();
                    }
                    alert(messages.statusMessage);
                }
            });

        }

        function createSubCat() {

            var dropDownMC = document.getElementById("showMC");
            var dbMCName = dropDownMC.options[dropDownMC.selectedIndex].value;

            if(dbMCName== ""){
                alert("Select a Main Category");
                return false;
            }

            var scName = document.getElementById("createSC").value;

            if(scName== ""){
                alert("Enter a Sub Category Name");
                return false;
            }

            var CreateSCData = { request: "createSubCategory",
                mainCategory:dbMCName,
                subCategory: scName};
            $.ajax({
                type: 'POST', url: 'ManageCategoriesController',
                contentType: 'application/json; charset=utf-8',
                dataType: 'json',
                data: JSON.stringify(CreateSCData),
                success: function (data, textStatus, jqXHR) {

                    var messages = JSON.parse(jqXHR.responseText);
                    alert(messages.statusMessage);
                }
            });
        }
    </script>

</div>
</body>
</html>
