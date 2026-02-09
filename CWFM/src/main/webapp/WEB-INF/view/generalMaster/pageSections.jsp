<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page isELIgnored="false" %>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <style>
        .success {
            color: green;
            font-weight: bold;
            padding: 10px;
            background-color: #e0ffe0;
            border: 1px solid green;
            margin-bottom: 1rem;
        }
        .error {
            color: red;
            font-weight: bold;
            padding: 10px;
            background-color: #ffe0e0;
            border: 1px solid red;
            margin-bottom: 1rem;
        }
        label {
            color: black;
            font-weight: bold;
            font-size: 12px; /* Reduced font size */
        }
        body {
            background-color: #FFFFFF;
            font-family: 'Volte Rounded', 'Noto Sans', sans-serif;
        }
        .action-bar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 1rem;
            background-color: #f8f8f8;
            margin-bottom: 1rem;
        }
        .action-buttons {
            display: flex;
            gap: 10px;
        }
        .action-buttons button {
            padding: 0.5rem 1rem;
            font-size: 1rem;
            cursor: pointer;
        }
        #searchForm {
            display: flex;
            align-items: center;
            flex-grow: 1;
            margin-right: 10px;
        }
        .search-box {
            width: 150px; /* Reduced width */
            padding: 0.25rem;
            font-size: 0.875rem;
            border: 1px solid #ccc;
            border-radius: 4px;
            outline: none;
            margin-right: 10px;
            box-sizing: border-box;
        }
        .table-container {
            overflow-x: auto;
            margin: 0;
            padding: 0;
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            padding: 5px; /* Reduced padding */
            text-align: left;
            border: 1px solid #ddd;
            font-size: 0.875rem;
        }
        th {
            background-color: #DDF3FF;
            color: #005151;
            font-family: 'Volte Rounded', 'Noto Sans', sans-serif;
            font-size: 0.75rem;
            padding: 4px; /* Reduced padding */
        }
        .page-header {
            display: flex;
            align-items: center;
            justify-content: flex-start;
            gap: 10px;
            padding: 8px;
            background-color: #FFFFFF;
            border-bottom: 1px solid #ccc;
        }
        select, input[type="text"] {
            width: 20%; /* Full width */
            padding: 6px; /* Reduced padding */
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 12px; /* Reduced font size */
            color: #000; /* Change text color to black */
            box-sizing: border-box;
        }
        select {
            background-color: #f9f9f9;
            color: #000;
        }
        input[type="text"] {
            background-color: #ffffff;
            color: #000;
        }
        label {
            color: black;
            font-weight: bold;
            font-size: 12px; /* Reduced font size */
        }
        select, textarea {
    font-family: inherit;
    font-size: inherit;
    line-height: inherit;
    </style>
</head>
<body>
<div class="page-header">
    
   <label for="sectionDropdown">Select Section:</label>
        <select id="sectionDropdown" onchange="onSectionChange()">
            <option value="">-- Select Section --</option>
            <c:forEach var="section" items="${sections}">
                <option value="${section.gmId}">${section.gmName}</option>
            </c:forEach>
        </select>
    
    

        <button type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="saveMapping()">Save</button>
            <%-- <input type="hidden" name="gmTypeId" id="gmTypeId" value="${param.gmTypeId}"> --%>
    </div>

    <h6>Existing General Masters</h6>
     <div style="display: flex; margin-top: 20px;">
        <!-- Available Pages -->
        <div>
            <h3>Available Pages</h3>
            <select id="availableBox" multiple style="width: 200px; height: 200px;"></select>
        </div>

        <div style="margin: 0 20px; display: flex; flex-direction: column; justify-content: center;">
            <button onclick="addPageToSelected()">Add </button><br>
            <button onclick="removePageFromSelected()"> Remove</button>
        </div>

        <div>
            <h3>Selected Pages</h3>
            <select id="selectedBox" multiple style="width: 200px; height: 200px;"></select>
        </div>
    </div>

  <!--   <div style="margin-top: 20px;">
        <button onclick="saveMapping()">Save</button>
    </div> -->
</body>
</html>
