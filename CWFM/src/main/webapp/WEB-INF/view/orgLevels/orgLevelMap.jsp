<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">

<head>
<title>Org Level Mapping</title>
   <!--  <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="resources/js/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="resources/css/styles.css"> 
    <script src="resources/js/cms/principalEmployer.js"></script>
    <script src="resources/js/commonjs.js"></script>  -->

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
    </style>
</head>

<body>
    
<!-- <h1>Organization Level Entry</h1> -->
<div class="page-header">
<label for="orgLevelDefId">Org Level Entry:</label>
<!-- <label for="orgLevelDefId">Select Organization Level:</label> -->
<select name="orgLevelDefId" id="orgLevelDefId" onchange="navigateToOrgLevel()">
    <option value="">-- Select --</option>
    <c:forEach var="orgLevel" items="${orgLevels}">
        <option value="${orgLevel.orgLevelDefId}" 
            <c:if test="${orgLevel.orgLevelDefId == orgLevelDefId}">selected</c:if>>
            ${orgLevel.name}
        </option>
    </c:forEach>
</select>
</div>

<!-- Form for Adding/Editing Entries -->
<h3>Add Entry</h3>
<div>
    <input type="hidden" name="orgLevelEntryId" id="orgLevelEntryId" value="0">
    <input type="hidden" name="orgLevelDefId" id="orgLevelDefId" value="${param.orgLevelDefId}">

    <label for="entryName">Entry Name:</label>
    <input type="text" name="entryName" id="entryName" style="text-transform: capitalize;" required autocomplete="off">

    <label for="description">Description:</label>
    <input type="text" name="description" id="description" style="text-transform: capitalize;" autocomplete="off">

    <button type="button" class="btn btn-default process-footer-button-cancel ng-binding" onclick="saveOrgEntries()">Save</button>
</div>
<hr/>

<!-- Table for displaying entries -->
<table border="1" id="table-body">
    <thead>
        <tr>
            <th>ID</th>
            <th>Entry Name</th>
            <th>Description</th>
            <th>Actions</th>
        </tr>
    </thead>
    <tbody>
        <c:choose>
            <c:when test="${not empty entries}">
                <c:forEach var="entry" items="${entries}">
                    <tr id="row-${entry.orgLevelEntryId}">
                        <td>${entry.orgLevelEntryId}</td>
                        <td>${entry.name}</td>
                        <td>${entry.description}</td>
                        <td>
                            <%-- <button onclick="editEntry(${entry.orgLevelEntryId}, '${entry.name}', '${entry.description}', ${param.orgLevelDefId})">Edit</button> --%>
                            <button type="button" class="btn btn-default process-footer-button-cancel ng-binding" onclick="deleteOrgLevelEntry(${entry.orgLevelEntryId}, ${orgLevelDefId})">Delete</button>
                        </td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr>
                    <td colspan="4">No entries available for the selected organization level.</td>
                </tr>
            </c:otherwise>
        </c:choose>
    </tbody>
</table>
</body>
</html>
