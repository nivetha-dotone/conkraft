<%-- <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit CMSPRINCIPALEMPLOYER</title>
</head>
<body>
    <h1>Edit CMSPRINCIPALEMPLOYER</h1>
    <form action="update" method="post">
        <input type="hidden" id="unitId" name="unitId" value="${cmSPRINCIPALEMPLOYER.UNITID}">
        <label for="name">Unit Name:</label>
        <input type="text" id="name" name="name" value="${cmSPRINCIPALEMPLOYER.NAME}" required><br>
        <label for="address">Address:</label>
        <input type="text" id="address" name="address" value="${cmSPRINCIPALEMPLOYER.ADDRESS}" required><br>
        <label for="managerName">Manager Name:</label>
        <input type="text" id="managerName" name="managerName" value="${cmSPRINCIPALEMPLOYER.MANAGERNAME}" required><br>
        <label for="managerAddress">Manager Address:</label>
        <input type="text" id="managerAddress" name="managerAddress" value="${cmSPRINCIPALEMPLOYER.MANAGERADDRS}" required><br>
        <label for="businessType">Type of Business:</label>
        <input type="text" id="businessType" name="businessType" value="${cmSPRINCIPALEMPLOYER.BUSINESSTYPE}" required><br>
        <label for="maxWorkmen">Maximum Number of Workmen:</label>
        <input type="text" id="maxWorkmen" name="maxWorkmen" value="${cmSPRINCIPALEMPLOYER.MAXWORKMEN}" required><br>
        <!-- Add more input fields for other fields if needed -->
        <button type="submit">Update</button>
    </form>
</body>
</html> --%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page isELIgnored="false" %>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
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
<div class="page-header">
<h3 style=" color: darkcyan;padding:4px;">Org Level Mapping</h3>
<div  class="action-buttons">
       <button type="submit"  class="btn btn-default process-footer-button-cancel ng-binding" onclick="loadCommonList('/org-level-mapping/list', 'New')">Cancel</button>
   </div>
</div>
<div class="form-group">
 <input type="hidden" name="orgAcctSetId" value="${basicInfo.orgAcctSetId}" />
 <label style=" color: darkcyan;padding:4px;">Short Name:</label>
 <input type="text" id="name" name="name" value="${basicInfo.shortName}" readonly required />
  </div> 
  

  <div class="form-group">
   <label style=" color: darkcyan;padding:4px;">Description:</label>
    <input type="text" id="description" name="description" readonly value="${basicInfo.longDescription}" required />
       
 </div>


<!-- Dynamic Tabs -->
<div id="orgMappingContainer">
    <div id="tabs">
        <ul style="padding:4px;">
            <c:forEach var="orgLevel" items="${orgLevels}">
                <li><a href="#tab-${orgLevel.orgLevelDefId}">${orgLevel.name}</a></li>
            </c:forEach>
        </ul>

        <c:forEach var="orgLevel" items="${orgLevels}">
            <div id="tab-${orgLevel.orgLevelDefId}" class="tab-content" style="display: none;">
                <h3>${orgLevel.name}</h3>

                <div class="multi-select-container">
                    <!-- Available Entries -->
                    <div class="multi-select-group">
                        <label for="available-${orgLevel.orgLevelDefId}" class="multi-select-label" style=" color: darkcyan;padding:4px;">Available Entries</label>
                        <select id="available-${orgLevel.orgLevelDefId}" class="multi-select-box" style="height:200px;width:250px;" multiple disabled>
                            <c:forEach var="entry" items="${orgLevel.availableEntries}">
                                <option value="${entry.orgLevelEntryId}">${entry.name}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <!-- Move Buttons -->
                    <div class="button-group">
                        <button type="button" onclick="moveRight('${orgLevel.orgLevelDefId}')"disabled>&gt;</button>
                        <button type="button" onclick="moveLeft('${orgLevel.orgLevelDefId}')"disabled>&lt;</button>
                    </div>

                    <!-- Selected Entries -->
                    <div class="multi-select-group">
                        <label for="selected-${orgLevel.orgLevelDefId}" class="multi-select-label" style=" color: darkcyan;padding:4px;">Selected Entries</label>
                        <select id="selected-${orgLevel.orgLevelDefId}" class="multi-select-box" style="height:200px;width:250px;" multiple disabled>
                            <c:forEach var="entry" items="${orgLevel.selectedEntries}">
                                <option value="${entry.orgLevelEntryId}" selected>${entry.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>




