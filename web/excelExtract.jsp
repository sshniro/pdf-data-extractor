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
    <button type="submit" id="upload-button">Upload</button>
</form>

    <script>
        var form = document.getElementById('file-form');
        var fileSelect = document.getElementById('file-select');
        var uploadButton = document.getElementById('upload-button');
        var files = [];
        var xhr = new XMLHttpRequest();
        form.onsubmit = function(event) {
            event.preventDefault();
            var files = fileSelect.files;

            // Create a new FormData object.
            var formData = new FormData();
            var file = files[0];
            formData.append('excelFile', file);

            xhr.open('POST', 'http://localhost:36305/file/extract', false);
            xhr.send(formData);
        }

        xhr.onload = function () {
            if (xhr.status === 200) {
                // File(s) uploaded.
                uploadButton.innerHTML = 'Upload';
            } else {
                alert('An error occurred!');
            }
        };
    </script>

    </body>
</html>
