<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Mark Up Regions</title>
    <title></title>
    <!-------------------------------- CSS Files------------------------------------>
    <link rel="stylesheet" type="text/css" href="assets/css/bootstrap.css">
    <!-------------------------------- JS Files------------------------------------>
    <script type="text/javascript" src="assets/js/jquery-1.10.2.js"></script>
    <script type="text/javascript" src="assets/js/bootstrap.js"></script>
</head>
<body>

<div class="row">

    <div class="col-md-12">
        <h3>Div Tag For Text Extraction </h3></br>
        <a href="#" id="demo1">Sample 1 : Sub title: Measurements With Grading Rule [Page1] [metaRegion : Not Selected]</a> </br>
        <a href="#" id="demo2">Sample 2 : Sub title: Measurements With Grading Rule [Page1] [metaRegion : Sub title:]</a> </br>
        <a href="#" id="demo3">Sample 3 : Sub title: Measurements With Grading Rule Applied [Page2]</a> </br>
        <a href="#" id="demo4">Sample 4 : Prod Status / Lifecycle: 610 - Adopted / ADOPTION [Page2]</a> </br>

    </div>

    <div class="col-md-12">
        <h3>Div Tag For Text Insertion </h3></br>
        <a href="#" id="demo5">Sample 5 : Insert All the text Coordinates</a> </br>
    </div>

    <div class="col-md-12">
        <h3>Div Tag For Image Extraction </h3></br>
        <a href="#" id="demo6">Sample 6 : Extract Panty Image [Page 1]</a> </br>
    </div>

    <div class="col-md-12">
        <h3>Div Tag For Image Insertion </h3></br>
        <a href="#" id="demo7">Sample 7 : Insert Panty Image [Page 1]</a> </br>
    </div>

    <div class="col-md-12">
        <h3>Div Tag For Table Insertion </h3></br>
        <a href="#" id="demo8">Sample 8 : Insert Table [page 1]</a> </br>
    </div>

    <div class="col-md-12">
        <h3>Div Tag For Testing</h3></br>
        <a href="#" id="test">Sample 9 : Testing</a> </br>
    </div>

    <div class="col-md-12">
        <h3>Div Tag For Full Insert</h3></br>
        <a href="#" id="demo9">Sample 9 : Testing</a> </br>
    </div>
</div>
<script>
    /* Post AJAX DATA if any Navigation Anchor tag is pressed */
    $(document).ready(function() {
        /* Sample 1 : Sub title: Measurements With Grading Rule [Page1] [metaRegion : Not Selected] */
        $('#demo1').click(function() {
            var data = {
                mainCategory: $('#mainCategory').val(),
                subCategory: $('#subCategory').val(),
                templateName: $('#templateName').val(),
                status: "extract",
                dataType: "text",
                textDataElements: [
                    {metaId: "sub title pages 1 No Meta",
                        totalX1:63,totalY1: 75,totalWidth: 515,totalHeight: 105,
                        metaX1: -1, metaY1:  -1, metaWidth:  -1, metaHeight:  -1, pageNumber:1}
                ]};
            ajaxPost(data);

        });
        /* Sample 2 : Sub title: Measurements With Grading Rule [Page1] [metaRegion : Sub title:]*/
        $('#demo2').click(function() {
            var data = {
                mainCategory: $('#mainCategory').val(),
                subCategory: $('#subCategory').val(),
                templateName: $('#templateName').val(),
                status: "extract",
                dataType: "text",
                textDataElements: [
                    {metaId: "sub title pages 1 With Meta",
                        totalX1:63,totalY1: 75,totalWidth: 515,totalHeight: 105,
                        metaX1: 63, metaY1: 75, metaWidth: 168, metaHeight: 104, pageNumber:1}
                ]};
            ajaxPost(data);

        });
        /* Sample 3 : Sub title: Measurements With Grading Rule Applied [Page2]*/
        $('#demo3').click(function() {
            var data = {
                mainCategory: $('#mainCategory').val(),
                subCategory: $('#subCategory').val(),
                templateName: $('#templateName').val(),
                status: "extract",
                dataType: "text",
                textDataElements: [
                    {metaId: "sub title pages 2 Without Meta Tag Testing",
                        totalX1:63,totalY1: 75,totalWidth: 747,totalHeight: 120,
                        metaX1: -1, metaY1: -1, metaWidth: -1, metaHeight: -1, pageNumber:2}
                ]};
            ajaxPost(data);
        });
        /* Sample 4 : Prod Status / Lifecycle: 610 - Adopted / ADOPTION [Page2] */
        $('#demo4').click(function() {
            var data = {
                mainCategory: $('#mainCategory').val(),
                subCategory: $('#subCategory').val(),
                templateName: $('#templateName').val(),
                status: "extract",
                dataType: "text",
                textDataElements: [
                    {metaId: "sub title pages 2 No Meta",
                        totalX1:654,totalY1: 190,totalWidth: 1130,totalHeight: 215,
                        metaX1: -1, metaY1: -1, metaWidth: -1, metaHeight: -1, pageNumber:2}
                ]};
            ajaxPost(data);
        });
        /* Sample 5 : Insert All the Coordinates*/
        $('#demo5').click(function() {
            var data = {
                status: "insert",
                textDataParser : {
                    mainCategory: $('#mainCategory').val(),
                    subCategory: $('#subCategory').val(),
                    templateName: $('#templateName').val(),
                    status: "insert",
                    dataType: "text",
                    textDataElements: [
                        {metaId: "sub title pages 1 No Meta",
                            totalX1:63,totalY1: 75,totalWidth: 515,totalHeight: 105,
                            metaX1: -1, metaY1:  -1, metaWidth:  -1, metaHeight:  -1, pageNumber:1},
                        {metaId: "sub title pages 1 With Meta",
                            totalX1:63,totalY1: 75,totalWidth: 515,totalHeight: 105,
                            metaX1: 63, metaY1: 75, metaWidth: 168, metaHeight: 104, pageNumber:1},
                        {metaId: "sub title pages 2 Without Meta Tag Testing",
                            totalX1:63,totalY1: 75,totalWidth: 747,totalHeight: 120,
                            metaX1: -1, metaY1: -1, metaWidth: -1, metaHeight: -1, pageNumber:2},
                        {metaId: "sub title pages 2 No Meta",
                            totalX1:654,totalY1: 190,totalWidth: 1130,totalHeight: 215,
                            metaX1: -1, metaY1: -1, metaWidth: -1, metaHeight: -1, pageNumber:2}
                    ]}
            };
            ajaxPost(data);
        });
        /* Sample 6 : Extract Panty Image [Page 1] */
        $('#demo6').click(function() {
            var data = {
                mainCategory: $('#mainCategory').val(),
                subCategory: $('#subCategory').val(),
                templateName: $('#templateName').val(),
                status: "extract",
                dataType: "image",
                imageDataElements: [
                    {metaId: "Panty Page 1",
                        totalX1:1366,totalY1: 32,totalWidth: 1574,totalHeight: 190,
                        metaX1: -1, metaY1:  -1, metaWidth:  -1, metaHeight:  -1, pageNumber:4}
                ]};
            ajaxPost(data);

        });
        /* Sample 7 : Insert Panty Image [Page 1] */
        $('#demo7').click(function() {
            var data = {
                status: "insert",
                imageDataParser : {
                    mainCategory: $('#mainCategory').val(),
                    subCategory: $('#subCategory').val(),
                    templateName: $('#templateName').val(),
                    status: "insert",
                    dataType: "image",
                    imageDataElements: [
                        {metaId: "Panty Page 1",
                            totalX1:1366,totalY1: 32,totalWidth: 1574,totalHeight: 190,
                            metaX1: -1, metaY1: -1, metaWidth: -1, metaHeight: -1, pageNumber:1}
                    ]}
            };
            ajaxPost(data);
        });
        /* Insert Table Data*/
        $('#demo8').click(function() {
            var data = {
                status: "insert",
                tableDataParser : {
                    mainCategory: $('#mainCategory').val(),
                    subCategory: $('#subCategory').val(),
                    templateName: $('#templateName').val(),
                    status: "insert",
                    dataType: "table",
                    tableDataElements: [
                        {metaId: "first table",
                            totalX1: 14,totalY1: 390, totalWidth: 870,totalHeight: 715,pageNumber: 1,
                            columns:[{metaId: "Code Column",
                                metaX1: 15,
                                metaY1: 391,
                                metaWidth: 81,
                                metaHeight: 415},

                                {metaId: "Name Column",
                                    metaX1: 82,
                                    metaY1: 392,
                                    metaWidth: 467,
                                    metaHeight: 415},

                                {metaId: "Tol (+) Column",
                                    metaX1: 468,
                                    metaY1: 392,
                                    metaWidth: 530,
                                    metaHeight: 415},

                                {metaId: "Tol (-) Column",
                                    metaX1: 531,
                                    metaY1: 392,
                                    metaWidth: 590,
                                    metaHeight: 415}

                            ]
                        },
                        {metaId: "second table",
                            totalX1: 14,totalY1: 390, totalWidth: 870,totalHeight: 715,pageNumber: 2,
                            columns:[{metaId: "Code Column p2",
                                metaX1: 15,
                                metaY1: 391,
                                metaWidth: 81,
                                metaHeight: 415},

                                {metaId: "Name Column p2",
                                    metaX1: 82,
                                    metaY1: 392,
                                    metaWidth: 467,
                                    metaHeight: 415},

                                {metaId: "Tol (+) Column p2",
                                    metaX1: 468,
                                    metaY1: 392,
                                    metaWidth: 530,
                                    metaHeight: 415},

                                {metaId: "Tol (-) Column p2",
                                    metaX1: 531,
                                    metaY1: 392,
                                    metaWidth: 590,
                                    metaHeight: 415}

                            ]
                        }
                    ]}
            };
            ajaxPost(data);
        });
        /* Fore Testing Purpose */
        $('#test').click(function() {
            var data = {
                    mainCategory: $('#mainCategory').val(),
                    subCategory: $('#subCategory').val(),
                    templateName: "table",
                    status: "extract",
                    dataType: "table",
                    tableDataElements: [
                        {metaId: "first table",
                            totalX1: 14,totalY1: 390, totalWidth: 554,totalHeight: 692,pageNumber: 2,
                            columns:[{metaId: "Part Name",
                                metaX1: 20,
                                metaY1: 368,
                                metaWidth: 92,
                                metaHeight: 436},

                                {metaId: "Material",
                                    metaX1: 165,
                                    metaY1: 368,
                                    metaWidth: 238,
                                    metaHeight: 436},

                                {metaId: "Placement",
                                    metaX1: 455,
                                    metaY1: 368,
                                    metaWidth: 548,
                                    metaHeight: 436}
                            ]
                        }
                    ]
            };
            ajaxPost(data);

        });

        $('#demo9').click(function() {
            var data = {
                status: "insert",
                textDataParser : {
                    mainCategory: $('#mainCategory').val(),
                    subCategory: $('#subCategory').val(),
                    templateName: $('#templateName').val(),
                    status: "insert",
                    dataType: "text",
                    textDataElements: [
                        {metaId: "sub title pages 1 No Meta",
                            totalX1:63,totalY1: 75,totalWidth: 515,totalHeight: 105,
                            metaX1: -1, metaY1:  -1, metaWidth:  -1, metaHeight:  -1, pageNumber:1},
                        {metaId: "sub title pages 1 With Meta",
                            totalX1:63,totalY1: 75,totalWidth: 515,totalHeight: 105,
                            metaX1: 63, metaY1: 75, metaWidth: 168, metaHeight: 104, pageNumber:1},
                        {metaId: "sub title pages 2 Without Meta Tag Testing",
                            totalX1:63,totalY1: 75,totalWidth: 747,totalHeight: 120,
                            metaX1: -1, metaY1: -1, metaWidth: -1, metaHeight: -1, pageNumber:2},
                        {metaId: "sub title pages 2 No Meta",
                            totalX1:654,totalY1: 190,totalWidth: 1130,totalHeight: 215,
                            metaX1: -1, metaY1: -1, metaWidth: -1, metaHeight: -1, pageNumber:2}
                    ]},
                imageDataParser : {
                    mainCategory: $('#mainCategory').val(),
                    subCategory: $('#subCategory').val(),
                    templateName: $('#templateName').val(),
                    status: "insert",
                    dataType: "image",
                    imageDataElements: [
                        {metaId: "Panty Page 1",
                            totalX1:1366,totalY1: 32,totalWidth: 1574,totalHeight: 190,
                            metaX1: -1, metaY1: -1, metaWidth: -1, metaHeight: -1, pageNumber:1}
                    ]},
                tableDataParser : {
                    mainCategory: $('#mainCategory').val(),
                    subCategory: $('#subCategory').val(),
                    templateName: $('#templateName').val(),
                    status: "insert",
                    dataType: "table",
                    tableDataElements: [
                        {metaId: "first table",
                            totalX1: 14,totalY1: 390, totalWidth: 870,totalHeight: 715,pageNumber: 1,
                            columns:[{metaId: "Code Column",
                                metaX1: 15,
                                metaY1: 391,
                                metaWidth: 81,
                                metaHeight: 415},

                                {metaId: "Name Column",
                                    metaX1: 82,
                                    metaY1: 392,
                                    metaWidth: 467,
                                    metaHeight: 415},

                                {metaId: "Tol (+) Column",
                                    metaX1: 468,
                                    metaY1: 392,
                                    metaWidth: 530,
                                    metaHeight: 415},

                                {metaId: "Tol (-) Column",
                                    metaX1: 531,
                                    metaY1: 392,
                                    metaWidth: 590,
                                    metaHeight: 415}

                            ]
                        },
                        {metaId: "second table",
                            totalX1: 14,totalY1: 390, totalWidth: 870,totalHeight: 715,pageNumber: 2,
                            columns:[{metaId: "Code Column p2",
                                metaX1: 15,
                                metaY1: 391,
                                metaWidth: 81,
                                metaHeight: 415},

                                {metaId: "Name Column p2",
                                    metaX1: 82,
                                    metaY1: 392,
                                    metaWidth: 467,
                                    metaHeight: 415},

                                {metaId: "Tol (+) Column p2",
                                    metaX1: 468,
                                    metaY1: 392,
                                    metaWidth: 530,
                                    metaHeight: 415},

                                {metaId: "Tol (-) Column p2",
                                    metaX1: 531,
                                    metaY1: 392,
                                    metaWidth: 590,
                                    metaHeight: 415}

                            ]
                        }
                    ]}
            };
            ajaxPost(data);
        });
    });
    /* Function to POST the DATA to the servlet and receive the extracted data*/
    function ajaxPost(data){

        $.ajax({
            type: 'POST', url: 'MarkUpTemplateRegionController',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            data: JSON.stringify(data),
            success: function(data, textStatus, jqXHR) {
                var messages = JSON.parse(jqXHR.responseText);
                alert(messages.extractedData);
            },
            error: function(jqXHR, textStatus, errorThrown) {
                if(jqXHR.status == 400) {
                    var messages = JSON.parse(jqXHR.responseText);
                    $('#messages').empty();
                    $.each(messages, function(i, v) {
                        var item = $('<li>').append(v);
                        $('#messages').append(item);
                    });
                } else {
                    alert('Unexpected server error.');
                }
            }
        });
    }
</script>

<h1>File Upload Form</h1>

<label for="mainCategory">mainCategory</label>
<input id="mainCategory" name="mainCategory" type="text" value="Sales Order"/> <br/>

<label for="subCategory">subCategory</label>
<input id="subCategory" name="subCategory" type="text" value="Supplier 10"/> <br/>

<label for="templateName">temlateName</label>
<input id="templateName" name="templateName" type="text" value="template1"/> <br/>

<label for="metaId">metaID</label>
<input id="metaId" name="metaId" type="text" value="metaName1" /> <br/>

<input id="submit" type="button" value="Submit the form">



</body>
</html>
