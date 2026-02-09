<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>Org Level Mapping</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
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
           /* margin-bottom: 1rem; */
        }
        .action-buttons {
            display: flex;
            gap: 0px;
        }
         .action-buttons button {
        margin-left: 10px; /* Space between buttons */
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
        justify-content: space-between; /* Distribute space between search and buttons */
        padding: 8px; /* Adjust padding */
        background-color: #FFFFFF; /* White background */
        /* border-bottom: 1px solid #ccc; /* Subtle border for separation */ */
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
        .error-message {
    color: red;
    font-size: 14px;
    display: inline-block; /* Keeps it in a single row */
    white-space: nowrap; /* Prevents line breaks */
    overflow: hidden;
    text-overflow: ellipsis; /* Adds "..." if text is too long */
    max-width: 100%; /* Adjust width based on layout */
}
    </style> 
</head>
<body>
<div  class="page-header">
    <h3 style=" color: darkcyan;padding:4px;">Org Level Mapping</h3>
   <div  class="action-buttons">
              <button type="button" class="btn btn-default process-footer-button-cancel ng-binding" onclick="saveOrgLevelMapping()">Save</button>
              <button type="submit"  class="btn btn-default process-footer-button-cancel ng-binding" onclick="loadCommonList('/org-level-mapping/list', 'New')">Cancel</button>
    </div>
    </div>
     <!-- Form to enter Name and Description -->
     <div class="form-group">
        <label for="name" style=" color: darkcyan;padding:10px;">Name:</label>
        <input type="text" id="name" name="name" style="text-transform: capitalize;" required autocomplete="off">
        <span id="nameError" class="error-message"></span>
    </div>
    <div class="form-group">
        <label for="description" style=" color: darkcyan;padding:10px;">Description:</label>
        <input type="text" id="description" name="description" style="text-transform: capitalize;"  required autocomplete="off">
        <span id="descriptionError" class="error-message"></span>
    </div>
    <div id="orgMappingContainer">
    <div id="tabs">
        <ul style="padding:10px;">
            <c:forEach var="orgLevel" items="${orgLevels}">
                <li><a href="#tab-${orgLevel.orgLevelDefId}">${orgLevel.name}</a></li>
            </c:forEach>
        </ul>

        <c:forEach var="orgLevel" items="${orgLevels}">
            <div id="tab-${orgLevel.orgLevelDefId}" class="tab-content" style="display: none;">
                <h3>${orgLevel.name}</h3>

                <div class="multi-select-container">
                    <div class="multi-select-group">
                        <label for="available-${orgLevel.orgLevelDefId}" class="multi-select-label"style=" color: darkcyan;">Available Entries</label>
                        <select id="available-${orgLevel.orgLevelDefId}" class="multi-select-box" style="height:200px;width:250px;" multiple>
                            <c:forEach var="entry" items="${orgLevel.availableEntries}">
                                <option value="${entry.orgLevelEntryId}">${entry.name}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="button-group">
                        <button type="button" onclick="moveRight('${orgLevel.orgLevelDefId}')">&gt;</button>
                        <button type="button" onclick="moveLeft('${orgLevel.orgLevelDefId}')">&lt;</button>
                        
                    </div>

                    <div class="multi-select-group">
                        <label for="selected-${orgLevel.orgLevelDefId}" class="multi-select-label"style=" color: darkcyan;">Selected Entries</label>
                        <select id="selected-${orgLevel.orgLevelDefId}" class="multi-select-box" style="height:200px;width:250px;" multiple>
                            <!-- Dynamically populated when moving items -->
                        </select>
                    </div>
                   
                </div>
            </div>
        </c:forEach>
        
    </div>
</div>




           
</body>
</html>
