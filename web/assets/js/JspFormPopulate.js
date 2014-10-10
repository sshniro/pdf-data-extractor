
/*
Scripts populates the select form fields for MainCategory, SubCategory and Templates
 */

function getMC() {

    var data={ request : "getMainCategories"};
    $.ajax({
        type: "POST",
        url: "FormPopulateController",
        data: JSON.stringify(data),

        success: function(data, textStatus, jqXHR) {

            var messages = JSON.parse(jqXHR.responseText);
            // Empty the current select options and append disabled selected option 'Select Main Category'
            $('#showMC').empty().append('<option value="" disabled selected> Select a Main Category </option>');
            $('#showMC2').empty().append('<option value="" disabled selected> Select a Main Category </option>');
            $('#showMC3').empty().append('<option value="" disabled selected> Select a Main Category </option>');

            // For Each Category append the value if it is not null
            for(var i=0;i<messages.mainCategories.length;i++){
                if(!(messages.mainCategories[i]===null))
                $('#showMC').append('<option value="' + messages.mainCategories[i] + '">' + messages.mainCategories[i] + '</option>');
                $('#showMC2').append('<option value="' + messages.mainCategories[i] + '">' + messages.mainCategories[i] + '</option>');
                $('#showMC3').append('<option value="' + messages.mainCategories[i] + '">' + messages.mainCategories[i] + '</option>');
            }
        }
    });
}

function getSC() {
    var dropDownMC = document.getElementById("showMC");
    var dbMCName = dropDownMC.options[dropDownMC.selectedIndex].value;

    var CreateSCData = { request: "getSubCategories",
        selectedMC: dbMCName };
    $.ajax({
        type: 'POST', url: 'FormPopulateController',
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        data: JSON.stringify(CreateSCData),
        success: function (data, textStatus, jqXHR) {
            var messages = JSON.parse(jqXHR.responseText);
            // Empty the current select options and append disabled selected option 'Select Sub Category'
            $('#showSC').empty().append('<option value="" disabled selected> Select a Sub Category </option>');
            $('#showSC2').empty().append('<option value="" disabled selected> Select a Sub Category </option>');

            // For Each Category append the value if it is not null
            for(var i=0;i<messages.subCategories.length;i++){
                if(!(messages.subCategories[i]===null))
                $('#showSC').append('<option value="' + messages.subCategories[i] + '">' + messages.subCategories[i] + '</option>');
                $('#showSC2').append('<option value="' + messages.subCategories[i] + '">' + messages.subCategories[i] + '</option>');
            }
        }
    });
}

function getTemplates() {

    var dropDownMC = document.getElementById("showMC");
    var dbMCName = dropDownMC.options[dropDownMC.selectedIndex].value;

    var dropDownSC = document.getElementById("showSC");
    var dbSCName = dropDownSC.options[dropDownSC.selectedIndex].value;

    var CreateSCData = { request: "getTemplates",
        selectedMC: dbMCName,
        selectedSC: dbSCName};
    $.ajax({
        type: 'POST', url: 'FormPopulateController',
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        data: JSON.stringify(CreateSCData),
        success: function (data, textStatus, jqXHR) {
            var messages = JSON.parse(jqXHR.responseText);

            $('#showTemplates').empty().append('<option value="" disabled selected> Select a Template </option>');

            for(var i=0;i<messages.templateNames.length;i++){
                if(!(messages.templateNames[i]===null))
                    $('#showTemplates').append('<option value="' + messages.templateNames[i] + '">' + messages.templateNames[i] + '</option>');
            }
        }
    });
}

/*
Clean up unwanted code for getMClist , getSCList , getTemplList
 */
function getMCList() {

    var data={ request : "getMainCategories"};
    $.ajax({
        type: "POST",
        url: "FormPopulateController",
        data: JSON.stringify(data),

        success: function(data, textStatus, jqXHR) {

            var messages = JSON.parse(jqXHR.responseText);

            $('#showMCList').empty();
            // If no record exists set the default text
            if(messages.mainCategories[0]===null){
                $('#showSCList').append('<option value="" disabled selected> MainCategory is Empty </option>');
                return false;
            }
            //var mcDropDown = document.getElementById("showMCList");
            //mcDropDown.size = 20;

            // For Each Category append the value if it is not null
            for(var i=0;i<messages.mainCategories.length;i++){
                $('#showMCList').append('<option value="' + messages.mainCategories[i] + '">' + messages.mainCategories[i] + '</option>');
            }
        }
    });
}

function getSCList() {

    var dropDownMC = document.getElementById("showMCList");
    var dbMCName = dropDownMC.options[dropDownMC.selectedIndex].value;

    //var scDropDown = document.getElementById("showSCList");

    var CreateSCData = { request: "getSubCategories",
                        selectedMC: dbMCName };

    $.ajax({
        type: 'POST', url: 'FormPopulateController',
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        data: JSON.stringify(CreateSCData),

        success: function (data, textStatus, jqXHR) {
            var messages = JSON.parse(jqXHR.responseText);
            // Empty the current select options and append disabled selected option 'Select Sub Category'

            $('#showSCList').empty();

            //If no record exists set the default empty message
            if(messages.subCategories[0]===null){
                $('#showSCList').append('<option value="" disabled selected> SubCategory is Empty </option>');
                $('#showTempList').empty();
                $('#showTempList').append('<option value="" disabled selected> Template is Empty </option>');
            //    scDropDown.size = 1;
                return false;
            }

            //scDropDown.size = messages.subCategories.length;

            // For Each Category append the value if it is not null
            for(var i=0;i<messages.subCategories.length;i++){
                    $('#showSCList').append('<option value="' + messages.subCategories[i] + '">' + messages.subCategories[i] + '</option>');
            }
            $('#showTempList').empty();
            $('#showTempList').append('<option value="" disabled selected> Template List</option>');

        }
    });

    getTempList();
}

function getTempList() {

    var dropDownMC = document.getElementById("showMCList");
    var dbMCName = dropDownMC.options[dropDownMC.selectedIndex].value;

    var dropDownSC = document.getElementById("showSCList");
    var dbSCName = dropDownSC.options[dropDownSC.selectedIndex].value;

    //var tempDropDown = document.getElementById("showTempList");

    //if(dbSCName==""){
    //    $('#showTempList').empty();
    //    // TO DO TEST THIS FUNCTIONALITY
    //    $('#showTempList').append('<option value="" disabled selected> Template is Empty </option>');
    //    tempDropDown.size = 1;
    //}

    var getTempsData = { request: "getTemplates",selectedMC: dbMCName, selectedSC : dbSCName };

    $.ajax({
        type: 'POST', url: 'FormPopulateController',
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        data: JSON.stringify(getTempsData),

        success: function (data, textStatus, jqXHR) {

            var messages = JSON.parse(jqXHR.responseText);

            // Empty the current select options
            $('#showTempList').empty();
            //If no record exists set the default empty message
            if( messages.templateNames.length==0){
                $('#showTempList').append('<option value="" disabled selected> Template is Empty </option>');
                return false;
            }

            //tempDropDown.size = messages.templateNames.length;

            // For Each Category append the value if it is not null
            for(var i=0;i<messages.templateNames.length;i++){
                $('#showTempList').append('<option value="' + messages.templateNames[i] + '">' + messages.templateNames[i] + '</option>');
            }
        }
    });
}
