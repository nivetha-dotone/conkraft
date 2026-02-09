<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>CSV Preview</title>
    <script src="preview.js"></script>
</head>
<body onload="fetchDataAndDisplay()">
    <!-- <button onclick="previewData()">Preview</button> -->
    
    <table border="1">
        <thead>
            <tr>
                <th>Column 1</th>
                <th>Column 2</th>
                <th>Column 3</th>
                <!-- Add more columns dynamically if needed -->
            </tr>
        </thead>
        <tbody id="previewTableBody">
            <!-- Data will be populated here -->
        </tbody>
    </table>
</body>
</html>
